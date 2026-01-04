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

package com.sun.j2ee.blueprints.opc.customerrelations.ejb;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

import javax.xml.transform.dom.DOMSource;

import com.sun.j2ee.blueprints.xmldocuments.Invoice;
import com.sun.j2ee.blueprints.xmldocuments.LineItems;
import com.sun.j2ee.blueprints.xmldocuments.Mail;
import com.sun.j2ee.blueprints.xmldocuments.XMLDocumentException;
import com.sun.j2ee.blueprints.po.purchaseorder.ejb.PurchaseOrderHelper;
import com.sun.j2ee.blueprints.po.purchaseorder.ejb.OrderStatusNames;

/**
 * MailInvoiceMDB receives a JMS message containing an Invoice
 * for a user order. It makes a mail message that it then
 * sends to the customer by email
 */
public class MailInvoiceMDB implements MessageDrivenBean, MessageListener {
  private static final String INVOICE_STYLE_SHEET = "/xsl/Invoice.xsl";
  private static final String MAIL_SUBJECT = "Java Pet Store Order Shipped: ";

  private Context context;
  private MessageDrivenContext mdc = null;
  private boolean sendConfirmationMail = false;
  private Queue q;
  private QueueConnectionFactory qFactory;
  private MailHelper mailHelper;
  private MailContentFormatter mailContentFormatter;


  public MailInvoiceMDB() {
  }

  public void ejbCreate() {
    try {
      ServiceLocator serviceLocator   = new ServiceLocator();
      sendConfirmationMail = serviceLocator.getSendOrderStatusMail
        (JNDINames.SEND_CONFIRMATION_MAIL);
      qFactory = serviceLocator.getQueueConnectionFactory
        (JNDINames.QUEUE_CONNECTION_FACTORY );
      q = serviceLocator.getQueue(JNDINames.MAIL_SENDER_QUEUE);

      mailHelper = new MailHelper(qFactory, q);
      mailContentFormatter = new MailContentFormatter(INVOICE_STYLE_SHEET);
    } catch (NamingException ne) {
      throw new EJBException("OPC.custRelations.MailInvoiceMDB.ejbCreate Naming exception");
    } catch (MailContentFormatter.FormatterException exception) {
      throw new EJBException("OPC.custRelations.MailInvoiceMDB.ejbCreate mailContentFormatter exception");
    }
  }

  /**
   * Receive a JMS Message containing the Invoice xml to
   * generate a Mail xml message for the customer.
   * The Mail xml mesages contain html presentation
   */
  public void onMessage(Message recvMsg) {
    TextMessage recdTM = null;
    String recdText = null;
    try {
      recdTM = (TextMessage)recvMsg;
      recdText = recdTM.getText();
      if (sendConfirmationMail) {
            String xmlMail = doWork(recdText);
          doTransition(xmlMail);
      }
    } catch(XMLDocumentException xde) {
      throw new EJBException("OPC.customerrelations.MailInvoiceMDB.onMessage" + xde);
    } catch  (JMSException je) {
      throw new EJBException("OPC.customerrelations.MailInvoiceMDB.onMessage" + je);
    } catch (MailContentFormatter.FormatterException mfe) {
      throw new EJBException("OPC.custRelations.MailInvoiceMDB.onMessage MailFormatter exception" + mfe);
    }
  }

  public void setMessageDrivenContext(MessageDrivenContext mdc) {
    this.mdc = mdc;
  }

  public void ejbRemove() {
  }

  /**
   * @param xmlMessage is the invoice for the customer order that has shipped
   */
  private String doWork(String xmlMessage) throws JMSException,
                                          XMLDocumentException,
                                           MailContentFormatter.FormatterException {

      PurchaseOrderHelper poHelper = new PurchaseOrderHelper();
      Invoice invoice = null;
      invoice = invoice.fromXML(xmlMessage);
      //build message with html formatting
      String emailAddress = poHelper.getPOEmailId(invoice.getOrderId());
      Locale locale = poHelper.getPOLocale(invoice.getOrderId());
      String subject = MAIL_SUBJECT + invoice.getOrderId();
      String message = mailContentFormatter.format(new DOMSource(invoice.toDOM()), locale);
     //build  mail message as xml
      Mail mailMsg = new Mail(emailAddress, subject, message);
      String xmlMail = mailMsg.toXML();
      return xmlMail;
  }

  /**
   * send a Mail message to mailer service, so customer gets an email
   */
  private void doTransition(String xmlMail) throws JMSException  {
     if (xmlMail != null) {
         mailHelper.sendEmail(xmlMail);
     }
  }
}

