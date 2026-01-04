/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package com.sun.j2ee.blueprints.opc.ejb;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.jms.*;

import com.sun.j2ee.blueprints.po.purchaseorder.ejb.PurchaseOrderHelper;
import com.sun.j2ee.blueprints.po.purchaseorder.ejb.OrderStatusNames;
import com.sun.j2ee.blueprints.xmldocuments.OrderApproval;
import com.sun.j2ee.blueprints.xmldocuments.ChangedOrder;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;

/**
 * OrderApprovalMDB gets a JMS message containing a list of
 * orders that has been updated. It updates the POEJB state
 * based on the status of the orders. For all approved orders
 * a supplier purchase order is built and sent to a supplier,
 * and a batch of order approval status notices to the customer
 * relations department to handle sending an email can to customers.
 */
public class OrderApprovalMDB implements MessageDrivenBean, MessageListener {

    private Context context;
    private MessageDrivenContext mdc = null;
    private QueueConnectionFactory qFactory;
    private Queue mailQueue;
    private Queue supplierPoQueue;
    private QueueHelper supplierQueueHelper;
    private QueueHelper mailQueueHelper = new QueueHelper(qFactory, mailQueue);

    public OrderApprovalMDB() {
    }

    public void ejbCreate() {
        try {
            JMSLocator jmsLocator   = new JMSLocator();
            qFactory = jmsLocator.getQueueConnectionFactory();
            mailQueue = jmsLocator.getQueue(JNDINames.CR_MAIL_ORDER_APPROVAL_MDB_QUEUE);
            supplierPoQueue = jmsLocator.getQueue(JNDINames.SUPPLIER_PURCHASE_ORDER_QUEUE);
          mailQueueHelper     = new QueueHelper(qFactory, mailQueue);
          supplierQueueHelper = new QueueHelper(qFactory, supplierPoQueue);
        } catch (NamingException ne) {
            throw new EJBException("OPC.OrderApprovalMDB.ejbCreate naming exception" + ne);
        }
    }

    /**
     * Process a list of order status updates for customer orders
     *
     * @param  a JMS message containing an OrderApproval that
     *          contains a list of orders and the status updates,
     *          such as APPROVED or DENIED.
     */
    public void onMessage(Message recvMsg) {
        TextMessage recdTM = null;
        String recdText = null;
        String xmlMailOrderApprovals = null;
        try {
            recdTM = (TextMessage)recvMsg;
              recdText = recdTM.getText();
            xmlMailOrderApprovals  = doWork(recdText);
                 //send the list of valid order approval/deny to customer relations
            sendMail(xmlMailOrderApprovals);
        } catch(XMLDocumentException xde) {
              throw new EJBException("OPC.OrderApproval.onMessage" + xde);
          }catch  (JMSException je) {
              throw new EJBException("OPC.OrderApproval.onMessage" + je);
        }
    }

    public void setMessageDrivenContext(MessageDrivenContext mdc) {
        this.mdc = mdc;
    }

    public void ejbRemove() {
    }


    /**
     * Process the list of order approvals and update database. Send a
     * PurchaseOrder to a supplier for each approved order. Also generate
     * a list of approved or denied orders.
     *
     * @return a list of valid order approvals/denys to be sent
     *         to customer relations. Or return null, if list empty
     *         or no valid orders
     */
    private String doWork(String xmlMessage) throws JMSException,
                                                    XMLDocumentException {
        PurchaseOrderHelper poHelper = new PurchaseOrderHelper();
        OrderApproval approval = null;
        approval = OrderApproval.fromXML(xmlMessage);
              //generate list of valid orders to return
        OrderApproval oaMailList = new OrderApproval();
        String xmlMailOrderApprovals = null;
        Collection coll = approval.getOrdersList();
        Iterator it = coll.iterator();

        while(it!= null && it.hasNext()) {
            ChangedOrder co = (ChangedOrder) it.next();
            // only PENDING orders can be updated
            //if order alreay APPROVED or DENIED or COMPLETED, then order
          //already processed so dont process again
            String curStatus = poHelper.getPOStatus(co.getOrderId());
            if(!curStatus.equals(OrderStatusNames.PENDING)) {
              continue;
          }

           //generate list of valid orders to return
           //list contains orders to notify the customer of
           //order status changes. List is sent to customer
           //relations for emailing.
           oaMailList.addOrder(co);

           poHelper.setPOStatus(co.getOrderId(), co.getOrderStatus());

           //for all approved orders, send a PO to supplier
             if(co.getOrderStatus().equals(OrderStatusNames.APPROVED)) {
             String xmlPO = poHelper.getXmlPO(co.getOrderId());
                 //send a PO to supplier for this order
                 doTransition(xmlPO);
             }
         }//end while

           xmlMailOrderApprovals = oaMailList.toXML();
         return xmlMailOrderApprovals ;
    }

    /**
     * Send a PO to supplier to fulfill a customer order
     * @param xmlPO is the Purchase Order to send to the supplier
     */
    private void doTransition(String xmlPO) throws JMSException {
        if(xmlPO != null) {
            supplierQueueHelper.sendMessage(xmlPO);
        }
    }

    /**
     * This transition sends the list of order status for each order to
     * the customer relations to send email notices to each customer.
     * This is done as a batch by sending one message to the customer
     * relations, containing multiple customer order status notices
     *
     * @param xmlOrderApproval which is a list of order status for each order to
     * the customer relations to send email notices to each customer.
     */
    private void sendMail(String xmlOrderApproval) throws JMSException {
        if (xmlOrderApproval != null) {
            mailQueueHelper.sendMessage(xmlOrderApproval);
        }
    }
}

