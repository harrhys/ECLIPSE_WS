/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.ejb.stateful.simple.client;

import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import samples.ejb.stateful.simple.tools.*;
import samples.ejb.stateful.simple.ejb.*;

/**
 * A simple java client. This demonstrates the use of stateful session bean. In this process
 * the client first creates a cart object, adds few books to that cart, lists the content of
 * the cart, removes few books from the cart. Overall, it demonstrates how the session bean
 * retains state information across method calls and within the same session.
 * <p>Client does the following, in order
 * <ul>
 * <li>Locates the home interface of the enterprise bean
 * <li>Gets a reference to the remote interface
 * <li>Invokes business methods
 * </ul>
 * <br>
 * <b>Locating the home interface:</b>
 * <blockquote><pre>
 *	Context initial = new InitialContext();
 *	Object objref = initial.lookup("java:comp/env/ejb/SimpleCart");
 *	CartHome home = (CartHome)PortableRemoteObject.narrow(objref, CartHome.class);
 * </pre></blockquote>
 * <br>
 * <b>Creating the remote interface:</b>
 * <blockquote><pre>
 *	Cart shoppingCart = home.create("Duke DeEarl","123");
 * </pre></blockquote>
 * <br>
 * <b>Invoking business methods:</b>
 * <blockquote><pre>
 *	...
 *	shoppingCart.addBook("The Martian Chronicles");
 *	...
 *	shoppingCart.removeBook("Alice in Wonderland");
 *	...
 * </pre></blockquote>
 * <br>
 *
 * <b>Output:</b>
 * <pre>
 *	The Martian Chronicles
 *	2001 A Space Odyssey
 *	The Left Hand of Darkness
 *	Caught a BookException: Alice in Wonderland not in cart.
 * </pre>
 *
 *
 */
public class CartClient {

   /**
    * The main method of the client. This invokes the <code>CartBean</code> to use
    * its services. It then asks the stateful bean to add few books, list the contents
    * of the cart and remove few books from the cart.
    * See <code>appclient</code> documentation in SunONE app server to run the clinet.
    *
    */
   public static void main(String[] args) {
       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/SimpleCart");

           CartHome home =
               (CartHome)PortableRemoteObject.narrow(objref,
                                            CartHome.class);

           Cart shoppingCart = home.create("Duke DeEarl","123");

           shoppingCart.addBook("The Martian Chronicles");
           shoppingCart.addBook("2001 A Space Odyssey");
           shoppingCart.addBook("The Left Hand of Darkness");

           Vector bookList = new Vector();
           bookList = shoppingCart.getContents();

           Enumeration enumer = bookList.elements();
           while (enumer.hasMoreElements()) {
              String title = (String) enumer.nextElement();
              System.out.println(title);
           }

           shoppingCart.removeBook("Alice in Wonderland");
           shoppingCart.remove();

           System.exit(0);

       } catch (BookException ex) {
           System.err.println("Caught a BookException: " + ex.getMessage());
           System.exit(0);
       } catch (Exception ex) {
           System.err.println("Caught an unexpected exception!");
           ex.printStackTrace();
           System.exit(1);
       }
   }
}
