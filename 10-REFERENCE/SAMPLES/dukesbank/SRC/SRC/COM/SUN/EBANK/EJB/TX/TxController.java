/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.tx;

import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import com.sun.ebank.util.TxDetails;
import com.sun.ebank.ejb.exception.*;

public interface TxController extends EJBObject {

    // getters

    public ArrayList getTxsOfAccount(Date startDate, Date endDate,
        String accountId) throws RemoteException, InvalidParameterException;

        // returns an ArrayList of TxDetails objects
        // that correspond to the txs for the specified
        // account

    public TxDetails getDetails(String txId)
        throws RemoteException, TxNotFoundException, InvalidParameterException;

        // returns the TxDetails for the specified tx

    // business transaction methods

    public void withdraw(BigDecimal amount, String description,
        String accountId)
        throws RemoteException, InvalidParameterException,
        AccountNotFoundException, IllegalAccountTypeException,
        InsufficientFundsException;

        // withdraws funds from a non-credit account

    public void deposit(BigDecimal amount, String description,
        String accountId)
        throws RemoteException, InvalidParameterException,
        AccountNotFoundException, IllegalAccountTypeException;
 
        // deposits funds to a non-credit account

    public void transferFunds(BigDecimal amount, String description,
        String fromAccountId, String toAccountId)
        throws RemoteException, InvalidParameterException,
        AccountNotFoundException, InsufficientFundsException,
        InsufficientCreditException;

        // transfers funds from one account to another

    public void makeCharge(BigDecimal amount, String description,
        String accountId) 
        throws  InvalidParameterException, AccountNotFoundException, 
        IllegalAccountTypeException, InsufficientCreditException, 
        RemoteException ;

        // makes a charge to a credit account

    public void makePayment(BigDecimal amount, String description,
        String accountId) 
        throws InvalidParameterException, AccountNotFoundException, 
        IllegalAccountTypeException, RemoteException;

        // makes a payment to a credit account

} // TxController
