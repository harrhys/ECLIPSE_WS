package samples.transactions.ejb.cmt.bank.client;
/**
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */


import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import samples.transactions.ejb.cmt.bank.ejb.*;
/**
 * A simple java client will: 
 * <ul>
 * <li>Locates the home interface of the enterprise bean
 * <li>Gets a reference to the remote interface
 * <li>Invokes business methods
 * </ul>
 */

public class BankClient {


/**
 * The main method of the client. This invokes the <code>BankBean</code> to use
 * its services.  It will create a bank EJB instance and transfer 40.00
 * to the savings account.
 * See <code>appclient</code> documentation in SunONE app server to run the client.
 *
 */
   public static void main(String[] args) {

       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/transactions-bankEjb");

           BankHome home = 
               (BankHome)PortableRemoteObject.narrow(objref, 
                                            BankHome.class);

           Bank duke = home.create("123");
           duke.transferToSaving(40.00);
           System.out.println("checking: " + duke.getCheckingBalance());
           System.out.println("saving: " + duke.getSavingBalance());

           duke.remove();

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an exception." );
           ex.printStackTrace();
       }
   } 
} 
