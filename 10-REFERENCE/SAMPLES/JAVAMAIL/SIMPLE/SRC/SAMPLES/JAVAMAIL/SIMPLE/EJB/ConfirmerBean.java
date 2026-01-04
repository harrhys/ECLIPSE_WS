/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.javamail.simple.ejb;

import java.rmi.RemoteException; 
import java.io.Serializable;
import java.io.IOException;
import java.util.*;
import java.text.*;
import javax.ejb.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import javax.naming.*;


public class ConfirmerBean implements SessionBean {
 
   SessionContext sc;
   private static final String mailer = "JavaMailer";

   public void sendNotice(String recipient) {

      try {
          Context initial = new InitialContext();
          Session session = 
            (Session) initial.lookup("java:comp/env/TheMailSession");
          
          System.out.println("Found mail session " + session);
          
          Message msg = new MimeMessage(session);
          msg.setFrom();

          msg.setRecipients(Message.RecipientType.TO,
      			InternetAddress.parse(recipient, false));

          msg.setSubject("Test Message from ConfirmerBean");
  
          DateFormat dateFormatter = DateFormat.getDateTimeInstance(
             DateFormat.LONG, DateFormat.SHORT);
 
          Date timeStamp = new Date();
         
          String messageText = "Thank you for your order." + '\n' +
             "We received your order on " + 
             dateFormatter.format(timeStamp) + ".";

          msg.setText(messageText);

          msg.setHeader("X-Mailer", mailer);
          msg.setSentDate(timeStamp);

          Transport.send(msg);

          System.out.println("Mail sent");
      } catch(Exception e) {
          throw new EJBException(e.getMessage());
      }
   }

   public ConfirmerBean() {}
   public void ejbCreate() {}
   public void ejbRemove() {}
   public void ejbActivate() {}
   public void ejbPassivate() {}
   public void setSessionContext(SessionContext sc) {}

} // ConfirmerBean
