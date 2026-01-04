package samples.ejb.bmp.salesrep.client;
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
import samples.ejb.bmp.salesrep.ejb.*;

/**
 * A simple java client will: 
 * <ul>
 * <li>Locates the home interface of the enterprise bean
 * <li>Gets a reference to the remote interface
 * <li>Invokes business methods
 * </ul>
 */
public class SalesRepClient {

/**
 * The main method of the client. This invokes the <code>SalesRepBean</code>
 * and the <code>CustomerBean</code> to use their services.
 * See <code>appclient</code> documentation in SunONE app server to run the client.
 *
 */
   public static void main(String[] args) {

      try {

         Context initial = new InitialContext();

         Object objref = initial.lookup("java:comp/env/ejb/SimpleSalesRep");
         SalesRepHome salesHome = 
             (SalesRepHome)PortableRemoteObject.narrow(objref, 
                                          SalesRepHome.class);

         objref = initial.lookup("java:comp/env/ejb/SimpleCustomer");
         CustomerHome customerHome =
             (CustomerHome)PortableRemoteObject.narrow(objref,
                                          CustomerHome.class);

         Customer buzz = customerHome.create("844", "543", "Buzz Murphy");

         Collection c = customerHome.findBySalesRep("543");
         Iterator i = c.iterator();

         while (i.hasNext()) {
            Customer customer = (Customer)i.next();
            String customerId = (String)customer.getPrimaryKey();
            System.out.println("customerId = " + customerId);
         }

         System.out.println();
         Customer mary = customerHome.findByPrimaryKey("987");
         mary.setSalesRepId("543");

         Customer x = customerHome.findByPrimaryKey("987");
         SalesRep janice = salesHome.findByPrimaryKey("543");
         ArrayList a = janice.getCustomerIds();

         i = a.iterator();

         while (i.hasNext()) {
            String customerId = (String)i.next();
            Customer customer = customerHome.findByPrimaryKey(customerId);
            String name = customer.getName();
            System.out.println(customerId + ": " + name);
         }

         System.exit(0);

      } catch (Exception ex) {
          System.err.println("Caught an exception." );
          ex.printStackTrace();
      }
   } 

} 
