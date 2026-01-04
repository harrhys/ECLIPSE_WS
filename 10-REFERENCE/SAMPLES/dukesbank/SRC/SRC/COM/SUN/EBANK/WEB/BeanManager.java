/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
 
 
package com.sun.ebank.web;
 
import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import com.sun.ebank.util.*;
import com.sun.ebank.ejb.account.*;
import com.sun.ebank.ejb.customer.*;
import com.sun.ebank.ejb.tx.*;

public class BeanManager {

    private CustomerController custctl;
    private AccountController acctctl;
    private TxController txctl; 

    public BeanManager() {
        	
      if (custctl == null) {
         try {
            CustomerControllerHome home = EJBGetter.getCustomerControllerHome();
            custctl = home.create();
         } catch (RemoteException ex) {
            System.out.println("Couldn't create customer bean."  + ex.getMessage());
         } catch (CreateException ex) {
            System.out.println("Couldn't create customer bean."  + ex.getMessage());
         } catch (NamingException ex) {
            System.out.println("Unable to lookup home: " + CodedNames.CUSTOMER_CONTROLLER_EJBHOME  + ex.getMessage());
         }
      }
      if (acctctl == null) {
         try {
             AccountControllerHome home = EJBGetter.getAccountControllerHome();
             acctctl = home.create();
         } catch (RemoteException ex) {
            System.out.println("Couldn't create account bean."  + ex.getMessage());
         } catch (CreateException ex) {
            System.out.println("Couldn't create account bean."  + ex.getMessage());
         } catch (NamingException ex) {
            System.out.println("Unable to lookup home: " + CodedNames.ACCOUNT_CONTROLLER_EJBHOME  + ex.getMessage());
         }
      }
       if (txctl == null) {
         try {
            TxControllerHome home = EJBGetter.getTxControllerHome();
            txctl = home.create();
         } catch (RemoteException ex) {
            System.out.println("Couldn't create transaction bean."  + ex.getMessage());
         } catch (CreateException ex) {
            System.out.println("Couldn't create transaction bean."  + ex.getMessage());
         } catch (NamingException ex) {
            System.out.println("Unable to lookup home: " + CodedNames.TX_CONTROLLER_EJBHOME  + ex.getMessage());
         }
      }
    }

    public CustomerController getCustomerController() {
      return custctl;
    }

    public AccountController getAccountController() { 
      return acctctl;
    }

    public TxController getTxController() {
      return txctl; 
    }
 

    public void destroy() {
         custctl = null;
         acctctl = null;
         txctl = null;
   }
}

