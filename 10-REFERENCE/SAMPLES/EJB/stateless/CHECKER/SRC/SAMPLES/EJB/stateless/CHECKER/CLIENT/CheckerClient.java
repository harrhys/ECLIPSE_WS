/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.stateless.checker.client;

import java.util.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import samples.ejb.stateless.checker.ejb.*;

/**
 * A simple java client. The client calls the <code>applyDiscount</code> method on a remote
 * object for an amount of 6000.0. The method in turn, applies a discount on the given amount
 * based on some configurable parameters, called environment entries. These environment entries
 * are defined in <code>ejb-jar.xml</code> file.
 * <p>In this process, client does the following, in order
 * <ul>
 * <li>Locates the home interface of the enterprise bean
 * <li>Gets a reference to the remote interface
 * <li>Invokes business methods
 * </ul>
 * <br>
 * <b>Locating the home interface:</b>
 * <blockquote><pre>
 *  Context initial = new InitialContext();
 *  Object objref = initial.lookup("java:comp/env/ejb/SimpleChecker");
 *  CheckerHome home = (CheckerHome)PortableRemoteObject.narrow(objref, CheckerHome.class);
 * </pre></blockquote>
 * <br>
 * <b>Creating the remote interface:</b>
 * <blockquote><pre>
 *	Checker checker = home.create("Duke");
 * </pre></blockquote>
 * <br>
 * <b>Invoking business methods:</b>
 * <blockquote><pre>
 *  double discount = checker.applyDiscount(6000.00);
 * </pre></blockquote>
 * <br>
 * <b>Output:</b>
 * <pre>
 * discount = 5700.0
 * </pre>
 *
 *
 */
public class CheckerClient {

   /**
    * The main method of the client. This invokes the <code>CheckerBean</code> to use
    * its services. It then asks the bean to apply a discount on 6000.0 units and then prints 
    * the result.
    * See <code>appclient</code> documentation in SunONE app server to run the clinet.
    *
    */
   public static void main(String[] args) {
       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/SimpleChecker");

           CheckerHome home = 
               (CheckerHome)PortableRemoteObject.narrow(objref, 
                                            CheckerHome.class);

           Checker checker = home.create("Duke");

           double discount = checker.applyDiscount(6000.00);
           System.out.println("discount = " + String.valueOf(discount));

           System.exit(0);

       } catch (Exception ex) {
           System.err.println("Caught an unexpected exception!");
           ex.printStackTrace();
       }
   } 
} 
