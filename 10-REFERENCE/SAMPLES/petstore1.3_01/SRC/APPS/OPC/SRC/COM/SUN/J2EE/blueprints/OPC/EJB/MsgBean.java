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

import java.util.Locale;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.jms.*;

import com.sun.j2ee.blueprints.xmldocuments.PO;
import com.sun.j2ee.blueprints.xmldocuments.ChangedOrder;
import com.sun.j2ee.blueprints.xmldocuments.OrderApproval;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;
import com.sun.j2ee.blueprints.po.purchaseorder.ejb.PurchaseOrderHelper;
import com.sun.j2ee.blueprints.po.purchaseorder.ejb.OrderStatusNames;

/**
 * Message Driven Bean receives a JMS message containing a purchase order
 * from a customer placing an order at Java Pet Store. It creates the
 * POEJB to begin the process of fulfilling the order.
 */
public class MsgBean implements MessageDrivenBean, MessageListener {

    private Context context;
    private MessageDrivenContext mdc = null;
    private QueueConnection qConnect;
    private Queue q;
    private QueueSession session;
    private QueueConnectionFactory qFactory;
    private QueueSender qSender;
    private QueueHelper queueHelper;

    public MsgBean() {
    }

    public void ejbCreate() {
        try {
          JMSLocator jmsLocator = new JMSLocator();
            qFactory = jmsLocator.getQueueConnectionFactory();
            q = jmsLocator.getQueue(JNDINames.ORDER_APPROVAL_MDB_QUEUE);
          queueHelper = new QueueHelper(qFactory, q);
        } catch (NamingException ne) {
            throw new EJBException("OPC.MsgBean.ejbCreate naming exception" + ne);
        }
    }

    /**
     * Process a a purchase order that was placed at the Java Pet Store
     * It creates the PuchaseOrder EJB for the order, and then for small
     * orders directly approves the order and sends the approval to the
     * OrderApproval MDB queue, but for larger orders, it jst puts them
     * in the PurchaseOrder EJB, and the workflow process just waits for
     * the Administrator to appove or deny the orders.
     *
     * @param  a JMS message containing an PurchaseOrder
     */
    public void onMessage(Message recvMsg) {
            TextMessage recdTM = null;
            String recdText = null;
            String approval = null;
          try {
              recdTM = (TextMessage)recvMsg;
              recdText = recdTM.getText();
              approval = doWork(recdText);

              //if order approve/deny list !empty, send it to customer relations
                if(approval != null) {
                      doTransition(approval);
                } //else wait for admin to approve/deny orders
          } catch(XMLDocumentException de) {
              throw new EJBException("OPC.MsgBean.onMessage", de);
          } catch(JMSException je) {
              throw new EJBException("OPC.MsgBean.onMessage", je);
          }
    }

    public void setMessageDrivenContext(MessageDrivenContext mdc) {
        this.mdc = mdc;
    }

    public void ejbRemove() {
    }

    /**
     * Process a purchase order from the petstore. This is the
     * beginning of the order fulfillment process.
     *
     * @param xmlMessage is a Purchase Order from the petstore
     */
    private String doWork(String xmlMessage) throws XMLDocumentException {
        String ret = null;
      PO po = null;
      po = PO.fromXML(xmlMessage);
        PurchaseOrderHelper poHelper = new PurchaseOrderHelper();
      poHelper.persistPO(po);
        if(canIApprove(po)) {
          ChangedOrder co = new ChangedOrder(po.getOrderId(), OrderStatusNames.APPROVED);
          OrderApproval oa = new OrderApproval();
          oa.addOrder(co);
          ret = oa.toXML();
        }
        return ret;
    }

    /**
     * Send an order approval to the OrderApproval Queue, since the work
     * for this step in the order fullfillment process is done.
     */
    private void doTransition(String xmlOrderApproval) throws JMSException {
       if (xmlOrderApproval != null) {
          queueHelper.sendMessage(xmlOrderApproval);
       }
    }

    /**
     * Just a stub for converting currency. This metod is used for
     * demonstrating the petstore, so that smaller orders can flow through
     * the system without having to fire up the admin Gui to approve orders
     */
    private boolean canIApprove(PO po) {
        Locale locale = po.getLocale();
        if (locale.equals(Locale.US)) {
            if(po.getTotalPrice() < 500) return true;
        } else if (locale.equals(Locale.JAPAN))  {
            if(po.getTotalPrice() < 50000) return true;
        }
        return false;
    }

}

