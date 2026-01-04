package samples.transactions.ejb.bmt.teller.client;
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
import samples.transactions.ejb.bmt.teller.ejb.*;

/**
 *  A simple java client will: 
 * <ul>
 * <li>Locates the home interface of the enterprise bean
 * <li>Gets a reference to the remote interface
 * <li>Invokes business methods
 * </ul>
 */

public class TellerClient {

 /**
  * The main method of the client. This invokes the <code>TellerBean</code> to use
  * its services.  The code will create a Teller EJB instance and withdraw
  * 60.00 from the checking account.  It displays the balance of this 
  * checking account before and after withdrawal.
  * See <code>appclient</code> documentation in SunONE app server to run the client.
  *
  */

   public static void main(String[] args) {

       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/transactions-tellerEjb");

           TellerHome home = 
               (TellerHome)PortableRemoteObject.narrow(objref, 
                                            TellerHome.class);

           Teller duke = home.create("123");
           System.out.println("checking = " + duke.getCheckingBalance());
           duke.withdrawCash(60.00);
           System.out.println("checking = " + duke.getCheckingBalance());
           duke.remove();

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an exception." );
           ex.printStackTrace();
       }
   } 
} 
