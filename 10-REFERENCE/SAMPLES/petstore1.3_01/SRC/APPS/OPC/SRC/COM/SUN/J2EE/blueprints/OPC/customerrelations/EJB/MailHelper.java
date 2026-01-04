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

import java.util.Locale;

import javax.naming.NamingException;
import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.jms.*;


/**
 * A helper class which takes care of interfacing with the Mailer Service
 * component.
 */
public class MailHelper implements java.io.Serializable {

  private QueueConnection qConnect;
  private Queue q;
  private QueueSession session;
  private QueueConnectionFactory qFactory;
  private QueueSender qSender;

  public MailHelper(QueueConnectionFactory qFactory, Queue q) {
    this.qFactory = qFactory;
    this.q = q;
  }

  /**
   * Sends an e-mail with the given message and subject to the given
   * e-mail address.
   *
   * @param xmlMail is the Mail message to send. It contains the email
   *        address, the subject, and the content that has been
   *        personalized with the style sheets
   */
  public void sendEmail(String xmlMail) throws JMSException {

    try {
      qConnect = qFactory.createQueueConnection();
      session = qConnect.createQueueSession(true, 0);
      qConnect.start();
      qSender = session.createSender(q);
      TextMessage jmsMsg = session.createTextMessage();
      jmsMsg.setText(xmlMail);
      qSender.send(jmsMsg);
    } finally {
      try {
        if( qConnect != null ) {
          qConnect.close();
        }
      } catch(Exception e) {
      }
    }
  }

}

