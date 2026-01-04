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

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.sun.j2ee.blueprints.po.purchaseorder.ejb.PurchaseOrderHelper;
import com.sun.j2ee.blueprints.po.purchaseorder.ejb.OrderStatusNames;
import com.sun.j2ee.blueprints.xmldocuments.Invoice;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;

/**
 * InvoiceMDB receives a JMS message containing an Invoice
 * for a user order. It updates the Purchase Order EJB based
 * on the invoice information.
 */
public class InvoiceMDB implements MessageDrivenBean, MessageListener {

    private Context context;
    private MessageDrivenContext mdc = null;

    public InvoiceMDB() {
    }

    public void ejbCreate() {
    }

    /**
     * InvoiceMDB receives a JMS message containing an Invoice
     * for a user order. If all the order items have been shipped
     * for the order, it updates the purchase order status to
     * COMPLETED to end the purchase order fulfillment process.
     * If the order is not yet all shipped, it updates the order
     * status based on the invoice.
     *
     * @param recvMsg is the JMS message contaning the xml content for the invoice
     */
    public void onMessage(Message recvMsg) {
        TextMessage recdTM = null;
        String recdText = null;
        try {
            recdTM = (TextMessage)recvMsg;
              recdText = recdTM.getText();
              doWork(recdText);
            //there is no transition since this is the end of the purchase
            //order workflow process.
      } catch(XMLDocumentException xde) {
           throw new EJBException("OPC.InvoiceMDB.onMessage::" + xde);
        } catch  (JMSException je) {
           throw new EJBException("OPC.InvoiceMDB.onMessage::" + je);
        }
    }

    public void setMessageDrivenContext(MessageDrivenContext mdc) {
          this.mdc = mdc;
    }

    public void ejbRemove() {
    }

    /**
     * update PO EJB to completed status for the order's invoice
     */
    private void doWork(String xmlInvoice) throws XMLDocumentException {
        PurchaseOrderHelper poHelper = new PurchaseOrderHelper();
          Invoice invoice = Invoice.fromXML(xmlInvoice);
        poHelper.processInvoice(invoice);
    }
}

