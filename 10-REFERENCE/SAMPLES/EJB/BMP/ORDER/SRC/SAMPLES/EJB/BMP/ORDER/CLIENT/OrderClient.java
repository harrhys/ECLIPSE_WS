package samples.ejb.bmp.order.client;
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
import samples.ejb.bmp.order.util.*;
import samples.ejb.bmp.order.ejb.*;

/**
  * A simple java client will: 
  * <ul>
  * <li>Locates the home interface of the enterprise bean
  * <li>Gets a reference to the remote interface
  * <li>Invokes business methods
  * </ul>
  */


public class OrderClient {

 /**
  * The main method of the client. This invokes the <code>OrderBean</code> to use
  * its services.
  * See <code>appclient</code> documentation in SunONE app server to run the client.
  *
  */


   public static void main(String[] args) {

      try {

         ArrayList lineItems = new ArrayList();
         lineItems.add(new LineItem("p23", 13, 12.00, 1, "123"));
         lineItems.add(new LineItem("p67", 47, 89.00, 2, "123"));
         lineItems.add(new LineItem("p11", 28, 41.00, 3, "123"));

         Context initial = new InitialContext();
         Object objref = initial.lookup("java:comp/env/ejb/SimpleOrder");

         OrderHome home = 
             (OrderHome)PortableRemoteObject.narrow(objref, 
                                          OrderHome.class);

         Order duke = home.create("123", "c44", "open", 
            totalItems(lineItems), lineItems);
         displayItems(duke.getLineItems());
         System.out.println();


         Collection c = home.findByProductId("p67");
         Iterator i=c.iterator();

         while (i.hasNext()) {
            Order order = (Order)i.next();
            String id = (String)order.getPrimaryKey();
            System.out.println(id);
         }

         System.exit(0);

      } catch (Exception ex) {
          System.err.println("Caught an exception." );
          ex.printStackTrace();
      }
   } 

   static double totalItems(ArrayList lineItems) {

      double total = 0.00;
      ListIterator iterator = lineItems.listIterator(0);
      while (iterator.hasNext()) {
         LineItem item = (LineItem)iterator.next();
         total += item.getUnitPrice();
      }
      return total;
   }

   static void displayItems(ArrayList lineItems) {

      ListIterator iterator = lineItems.listIterator(0);
      while (iterator.hasNext()) {
         LineItem item = (LineItem)iterator.next();
         System.out.println(item.getOrderId() + " " +
                            item.getItemNo() + " " +
                            item.getProductId() + " " +
                            item.getUnitPrice());
      }
   }
} 
