/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package samples.javamail.simple.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import samples.javamail.simple.ejb.*;

public class ConfirmerClient {

   public static void main(String[] args) {

       if (args.length != 1) {
           System.out.println("Please enter the destination email address as a command-line argument");
           System.exit(1);
       }

       String recipient = args[0];

       try {

           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/SimpleConfirmer");

           ConfirmerHome home = 
               (ConfirmerHome)PortableRemoteObject.narrow(objref, 
                                            ConfirmerHome.class);

           Confirmer confirmer = home.create();
           confirmer.sendNotice(recipient);

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an unexpected exception!");
           ex.printStackTrace();
       }
   } 
} 
