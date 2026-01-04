/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.rmi_iiop.cpp.client;

import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import samples.rmi_iiop.cpp.ejb.CartHome;
import samples.rmi_iiop.cpp.ejb.Cart;
import samples.rmi_iiop.cpp.ejb.BookException;

/**
 * A Java client for simple shopping cppcart
 * @author Kumar Jayanti
 * @version 1.0
 */
public class CartClient {

   public static void main(String[] args) {
       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/CartEJB");

           CartHome home = 
               (CartHome)PortableRemoteObject.narrow(objref, 
                                            CartHome.class);

           Cart shoppingCart = home.create("Duke DeEarl","123");

           shoppingCart.addBook("The Martian Chronicles");
           shoppingCart.addBook("2001 A Space Odyssey");
           shoppingCart.addBook("The Left Hand of Darkness");
           
           //Vector bookList = new Vector();
           String[] bookList = null;
           bookList = shoppingCart.getContents();

           /*
           Enumeration enumer = bookList.elements();
           while (enumer.hasMoreElements()) {
              String title = (String) enumer.nextElement();
              System.out.println(title);
           }
           */
           for (int i=0; i< bookList.length; i++)
           {
              //String title = (String) bookList[i];
              System.out.println(bookList[i]);
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
