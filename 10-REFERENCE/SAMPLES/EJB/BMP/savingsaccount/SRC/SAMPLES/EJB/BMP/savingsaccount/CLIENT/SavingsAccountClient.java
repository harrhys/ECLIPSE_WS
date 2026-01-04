/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.bmp.savingsaccount.client;

import java.util.*;
import java.math.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import samples.ejb.bmp.savingsaccount.ejb.*;

public class SavingsAccountClient {

   public static void main(String[] args) {

       try {
           Context initial = new InitialContext();
           Object objref = initial.lookup("java:comp/env/ejb/SimpleSavingsAccount");

           SavingsAccountHome home = 
               (SavingsAccountHome)PortableRemoteObject.narrow(objref, 
                                            SavingsAccountHome.class);

           BigDecimal zeroAmount = new BigDecimal("0.00");
           SavingsAccount duke = home.create("123", "Duke", "Earl", zeroAmount);
           duke.credit(new BigDecimal("88.50"));
           duke.debit(new BigDecimal("20.25"));
           BigDecimal balance = duke.getBalance();
           System.out.println("balance = " + balance);
           duke.remove();

           SavingsAccount joe = home.create("836", "Joe", "Jones", zeroAmount);
           joe.credit(new BigDecimal("34.55"));
           SavingsAccount jones = home.findByPrimaryKey("836");
           jones.debit(new BigDecimal("2.00"));
           balance = jones.getBalance();
           System.out.println("balance = " + balance);

           SavingsAccount pat = home.create("456", "Pat", "Smith", zeroAmount);
           pat.credit(new BigDecimal("44.77"));
           SavingsAccount john = home.create("730", "John", "Smith", zeroAmount);
           john.credit(new BigDecimal("19.54"));
           SavingsAccount mary = home.create("268", "Mary", "Smith", zeroAmount);
           mary.credit(new BigDecimal("100.07"));

           Collection c = home.findByLastName("Smith");
           Iterator i=c.iterator();

           while (i.hasNext()) {
              SavingsAccount account = (SavingsAccount)i.next();
              String id = (String)account.getPrimaryKey();
              BigDecimal amount = account.getBalance(); 
              System.out.println(id + ": " + amount);
           }

           c = home.findInRange(new BigDecimal("20.00"), 
               new BigDecimal("99.00"));
           i=c.iterator();

           while (i.hasNext()) {
              SavingsAccount account = (SavingsAccount)i.next();
              String id = (String)account.getPrimaryKey();
              BigDecimal amount = account.getBalance(); 
              System.out.println(id + ": " + amount);
           }

           SavingsAccount pete = home.create("904", "Pete", "Carlson", 
              new BigDecimal("5.00"));
           SavingsAccount sally = home.create("905", "Sally", "Fortney", 
              new BigDecimal("8.00"));

           home.chargeForLowBalance(new BigDecimal("10.00"), 
              new BigDecimal("1.00"));

           BigDecimal reducedAmount = pete.getBalance();
           System.out.println(reducedAmount);
           reducedAmount = sally.getBalance();
           System.out.println(reducedAmount);

           System.exit(0);

       } catch (InsufficientBalanceException ex) {
           System.err.println("Caught an InsufficientBalanceException: " 
                               + ex.getMessage());

       } catch (Exception ex) {
           System.err.println("Caught an exception." );
           ex.printStackTrace();
       }
   } 
} 
