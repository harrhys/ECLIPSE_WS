/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.util;

import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.sun.ebank.ejb.account.AccountHome;
import com.sun.ebank.ejb.account.AccountControllerHome;
import com.sun.ebank.ejb.customer.CustomerHome;
import com.sun.ebank.ejb.customer.CustomerControllerHome;
import com.sun.ebank.ejb.tx.TxHome;
import com.sun.ebank.ejb.tx.TxControllerHome;
import com.sun.ebank.ejb.exception.*;

/**
 * This helper class fetches EJB home references.
 */

public final class EJBGetter {

    public static AccountHome getAccountHome() throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.ACCOUNT_EJBHOME);
        return (AccountHome)
            PortableRemoteObject.narrow(objref, AccountHome.class);
    } 

    public static AccountControllerHome getAccountControllerHome() 
        throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.ACCOUNT_CONTROLLER_EJBHOME);
        return (AccountControllerHome)
            PortableRemoteObject.narrow(objref, AccountControllerHome.class);
    } 

    public static CustomerHome getCustomerHome() throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.CUSTOMER_EJBHOME);
        return (CustomerHome)
            PortableRemoteObject.narrow(objref, CustomerHome.class);
    }

    public static CustomerControllerHome getCustomerControllerHome() 
        throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.CUSTOMER_CONTROLLER_EJBHOME);
        return (CustomerControllerHome)
            PortableRemoteObject.narrow(objref, CustomerControllerHome.class);
    }

    public static TxHome getTxHome() throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.TX_EJBHOME);
        return (TxHome)
            PortableRemoteObject.narrow(objref, TxHome.class);
    }

    public static TxControllerHome getTxControllerHome() 
        throws NamingException {

        InitialContext initial = new InitialContext();
        Object objref = initial.lookup(CodedNames.TX_CONTROLLER_EJBHOME);
        return (TxControllerHome)
            PortableRemoteObject.narrow(objref, TxControllerHome.class);
    }

} // EJBGetter
