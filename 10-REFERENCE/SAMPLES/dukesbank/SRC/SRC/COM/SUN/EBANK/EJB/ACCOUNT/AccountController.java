/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.account;

import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import com.sun.ebank.util.AccountDetails;
import com.sun.ebank.ejb.exception.*;

public interface AccountController extends EJBObject {

    // account creation and removal methods

    public String createAccount(String customerId, String type, 
        String description, BigDecimal balance, BigDecimal creditLine, 
        BigDecimal beginBalance, Date beginBalanceTimeStamp) 
        throws RemoteException, IllegalAccountTypeException,
        CustomerNotFoundException, InvalidParameterException;


        // makes a new account and enters it into db,
        // customer for customerId must exist 1st,
        // returns accountId

    public void removeAccount(String accountId)
       throws RemoteException, AccountNotFoundException, 
       InvalidParameterException;

       // removes account from db

    // customer-account relationship methods

    public void addCustomerToAccount(String customerId, 
        String accountId) throws RemoteException,
        AccountNotFoundException, CustomerNotFoundException,
        CustomerInAccountException, InvalidParameterException;

        // adds another customer to the account
        // throws CustomerInAccountException
        //        if the customer is already in the account
        // throws CustomerNotFoundException 
        //        if the customer does not exist

    public void removeCustomerFromAccount(String customerId, 
        String accountId) throws RemoteException,
        AccountNotFoundException, CustomerRequiredException, 
        CustomerNotInAccountException, InvalidParameterException;

        // removes a customer from the account, but
        // the customer is not removed from the db
        // throws CustomerRequiredException
        //        if there is only one customer in the account
        //        (an account must have a least one customer)
        // throws CustomerNotInAccountException
        //        if the customer to be removed is not in the account

    // getters

    public ArrayList getAccountsOfCustomer(String customerId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

        // returns an ArrayList of AccountDetails objects
        // that correspond to the accounts for the specified
        // customer

    public AccountDetails getDetails(String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

        // returns the AccountDetails for the specified account

    // setters

    public void setType(String type, String accountId)
        throws RemoteException, AccountNotFoundException, 
        IllegalAccountTypeException, InvalidParameterException;

    public void setDescription(String description, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setBalance(BigDecimal balance, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setCreditLine(BigDecimal creditLine, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setBeginBalance(BigDecimal beginBalance, String accountId)
        throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

    public void setBeginBalanceTimeStamp(Date beginBalanceTimeStamp, 
        String accountId) throws RemoteException, AccountNotFoundException,
        InvalidParameterException;

} // AccountController
