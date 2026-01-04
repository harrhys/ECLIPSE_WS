/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.connectors.cci.client;

import java.util.*;
import samples.connectors.cci.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

public class CoffeeClient {

   public static void main(String[] args) {
       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/SimpleCoffee");

           CoffeeHome home = 
               (CoffeeHome)PortableRemoteObject.narrow(objref, 
                                                       CoffeeHome.class);

           Coffee coffee = home.create();

           int count = coffee.getCoffeeCount();
           System.err.println("Coffee count = " + count);

           System.err.println("Inserting 3 coffee entries...");
           coffee.insertCoffee("Mocha", 10);
           coffee.insertCoffee("Espresso", 20);
           coffee.insertCoffee("Kona", 30);

           count = coffee.getCoffeeCount();
           System.err.println("Coffee count = " + count);
       } catch (Exception ex) {
           System.err.println("Caught an unexpected exception!");
           ex.printStackTrace();
       }
   } 
} 
