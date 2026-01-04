/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.account;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.math.*;
import javax.ejb.*;
import javax.naming.*;
import java.rmi.RemoteException;
import com.sun.ebank.ejb.customer.CustomerHome;
import com.sun.ebank.ejb.customer.Customer;
import com.sun.ebank.ejb.exception.MissingPrimaryKeyException;
import com.sun.ebank.ejb.exception.AccountNotFoundException;
import com.sun.ebank.ejb.exception.CustomerInAccountException;
import com.sun.ebank.ejb.exception.IllegalAccountTypeException;
import com.sun.ebank.ejb.exception.CustomerRequiredException;
import com.sun.ebank.ejb.exception.CustomerNotInAccountException;
import com.sun.ebank.ejb.exception.CustomerNotFoundException;
import com.sun.ebank.ejb.exception.InvalidParameterException;
import com.sun.ebank.util.Debug;
import com.sun.ebank.util.DomainUtil;
import com.sun.ebank.util.DBHelper;
import com.sun.ebank.util.AccountDetails;
import com.sun.ebank.util.EJBGetter;
import com.sun.ebank.util.CodedNames;

public class AccountControllerBean implements SessionBean {

    private String accountId;
    private AccountHome accountHome;
    private Account account;
    private Connection con;
    
    // account creation and removal methods

    public String createAccount(String customerId, String type,
        String description, BigDecimal balance, BigDecimal creditLine, 
        BigDecimal beginBalance, java.util.Date beginBalanceTimeStamp)   
        throws IllegalAccountTypeException, CustomerNotFoundException,
        InvalidParameterException {


        // makes a new account and enters it into db,
        // customer for customerId must exist 1st,
        // returns accountId

        Debug.print("AccountControllerBean createAccount");

        if (customerId == null) 
            throw new InvalidParameterException("null customerId" );

        if (type == null)
            throw new InvalidParameterException("null type");

        if (description == null)
            throw new InvalidParameterException("null description");

        if (beginBalanceTimeStamp == null)
            throw new InvalidParameterException("null beginBalanceTimeStamp");

        DomainUtil.checkAccountType(type);

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        ArrayList customerIds = new ArrayList();
        customerIds.add(customerId);

        try {
            makeConnection();
            accountId = DBHelper.getNextAccountId(con);
            account = accountHome.create(accountId, type,
                description, balance, creditLine, beginBalance,
                beginBalanceTimeStamp, customerIds);   
            insertXref(customerId, accountId);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("createAccount: " + ex.getMessage());
        }

        return accountId;
    } // createAccount

    public void removeAccount(String accountId) 
        throws AccountNotFoundException, InvalidParameterException {

       // removes account from db

        Debug.print("AccountControllerBean removeAccount");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            makeConnection();
            deleteAllAccountInXref(accountId);
            releaseConnection();
            account.remove();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeAccount: " + ex.getMessage());
        }

    } // removeAccount


    // customer-account relationship methods

    public void addCustomerToAccount(String customerId, 
        String accountId) throws AccountNotFoundException, 
        CustomerNotFoundException, CustomerInAccountException,
        InvalidParameterException {

        // adds another customer to the account

        Debug.print("AccountControllerBean addCustomerToAccount");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        ArrayList customerIds;
        CustomerHome customerHome;

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            makeConnection();
            customerIds = getCustomerIds(accountId);
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("addCustomerToAccount: " + ex.getMessage());
        }

        if (customerIds.contains(customerId)) {
            releaseConnection();
            throw new CustomerInAccountException
            ("customer " + customerId + 
             " already assigned to account " + accountId);
        } 

        try {
            insertXref(customerId, accountId);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("addCustomerToAccount: " + ex.getMessage());
        }
    } // addCustomerToAccount

    public void removeCustomerFromAccount(String customerId, 
        String accountId) throws AccountNotFoundException,
        CustomerRequiredException, CustomerNotInAccountException,
        InvalidParameterException {

        // removes a customer from this account, but
        // the customer is not removed from the db

        Debug.print("AccountControllerBean removeCustomerFromAccount");

        ArrayList customerIds;

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            makeConnection();
            customerIds = getCustomerIds(accountId);
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeCustomerFromAccount: " + ex.getMessage());
        }

        if (customerIds.size() == 1) {
            releaseConnection();
            throw new CustomerRequiredException();
        } 

        if (customerIds.contains(customerId) == false) {
            releaseConnection();
            throw new CustomerNotInAccountException
            ("customer " + customerId + 
             " not assigned to account " + accountId);
        } 
        try {
            deleteOneCustomerInXref(customerId, accountId);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeCustomerFromAccount: " + ex.getMessage());
        }
    } // removeCustomerFromAccount


    // getters

    public ArrayList getAccountsOfCustomer(String customerId)
        throws AccountNotFoundException, InvalidParameterException {

        // returns an ArrayList of AccountDetails 
        // that correspond to the accounts for the specified
        // customer

        Debug.print("AccountControllerBean getAccountsOfCustomer");

        Collection accountIds;

        if (customerId == null) 
            throw new InvalidParameterException("null customerId");

        try {
            accountIds = accountHome.findByCustomerId(customerId);
            if (accountIds.isEmpty())
                throw new AccountNotFoundException();
        } catch (Exception ex) {
             throw new AccountNotFoundException();
        }

        ArrayList accountList = new ArrayList();

        try {
            Iterator i = accountIds.iterator();
            while (i.hasNext()) {
                Account account = (Account)i.next();
                AccountDetails accountDetails = account.getDetails();
                accountList.add(accountDetails);
            }
        } catch (RemoteException ex) {
             throw new EJBException("getAccountsOfCustomer: " 
                 + ex.getMessage());
        } 

        return accountList;

    } //  getAccountsOfCustomer

    public AccountDetails getDetails(String accountId) 
        throws AccountNotFoundException, InvalidParameterException {

        // returns the AccountDetails for the specified account

        Debug.print("AccountControllerBean getDetails");

        AccountDetails result;

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            result = account.getDetails();
        } catch (RemoteException ex) {
             throw new EJBException("getDetails: " + ex.getMessage());
        } 

        return result;

    } // getDetails
     

    // setters

    public void setType(String type, String accountId) 
        throws AccountNotFoundException, IllegalAccountTypeException,
        InvalidParameterException {

        Debug.print("AccountControllerBean setType");

        if (type == null)
            throw new InvalidParameterException("null type");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        DomainUtil.checkAccountType(type);
        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setType(type);
        } catch (RemoteException ex) {
             throw new EJBException("setType: " + ex.getMessage());
        } 

    } // setType

    public void setDescription(String description, String accountId)
        throws AccountNotFoundException,InvalidParameterException {

        Debug.print("AccountControllerBean setDescription");

        if (description == null)
            throw new InvalidParameterException("null description");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

    } // setDescription

    public void setBalance(BigDecimal balance, String accountId)
        throws AccountNotFoundException, InvalidParameterException {

        Debug.print("AccountControllerBean setBalance");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setBalance(balance);
        } catch (RemoteException ex) {
             throw new EJBException("setBalance: " + ex.getMessage());
        } 

    } // setBalance

    public void setCreditLine(BigDecimal creditLine, String accountId)
        throws EJBException, AccountNotFoundException,
        InvalidParameterException {

        Debug.print("AccountControllerBean setCreditLine");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setCreditLine(creditLine);
        } catch (RemoteException ex) {
             throw new EJBException("setCreditLine: " + ex.getMessage());
        } 

    } // setCreditLine

    public void setBeginBalance(BigDecimal beginBalance, String accountId)
        throws AccountNotFoundException, InvalidParameterException {

        Debug.print("AccountControllerBean setBeginBalance");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setBeginBalance(beginBalance);
        } catch (RemoteException ex) {
             throw new EJBException("setBeginBalance: " + ex.getMessage());
        } 

    } // setBeginBalance

    public void setBeginBalanceTimeStamp(java.util.Date beginBalanceTimeStamp, 
        String accountId)
        throws AccountNotFoundException, InvalidParameterException {

        Debug.print("AccountControllerBean setBeginBalanceTimeStamp");

        if (beginBalanceTimeStamp == null)
            throw new InvalidParameterException("null beginBalanceTimeStamp");

        if (accountId == null)
            throw new InvalidParameterException("null accountId" );

        if (accountExists(accountId) == false)
            throw new AccountNotFoundException(accountId);

        try {
            account.setBeginBalanceTimeStamp(beginBalanceTimeStamp);
        } catch (RemoteException ex) {
             throw new EJBException("setBeginBalanceTimeStamp: " 
                 + ex.getMessage());
        } 

    } // setBeginBalanceTimeStamp

    // ejb methods

    public void ejbCreate() {

        Debug.print("AccountControllerBean ejbCreate");

        try {
            accountHome = EJBGetter.getAccountHome();
        } catch (Exception ex) {
             throw new EJBException("ejbCreate: " +
                 ex.getMessage());
        }

        account = null;
        accountId = null;
    } // ejbCreate

    public AccountControllerBean() {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void setSessionContext(SessionContext sc) {}

    // private methods

    private boolean accountExists(String accountId) {

        // If a business method has been invoked with
        // a different accountId, then find the new
        // accountId and update the accountId and account 
        // variables.  Return false if the account
        // cannot be found.

        Debug.print("AccountControllerBean accountExists");

        if (accountId.equals(this.accountId) == false) {
            try {
                account = accountHome.findByPrimaryKey(accountId);
                this.accountId = accountId;
            } catch (Exception ex) {
                return false;
            }
        } // if
        return true;

    } // accountExists

    private boolean customerExists(String customerId) {

        Debug.print("AccountControllerBean customerExists");

        CustomerHome customerHome;

        try {
            customerHome = EJBGetter.getCustomerHome();
        } catch (Exception ex) {
             throw new EJBException("customerExists: " +
                 ex.getMessage());
        }

        try {
            customerHome.findByPrimaryKey(customerId);
            return true;
        } catch (Exception ex) {
            return false;
        }

    } // customerExists

/*********************** Database Routines *************************/

    private void makeConnection() {
   
        Debug.print("AccountControllerBean makeConnection");

        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup(CodedNames.BANK_DATABASE);
            con =  ds.getConnection();
        } catch (Exception ex) {
             throw new EJBException("Unable to connect to database. " +
                 ex.getMessage());
        }
    } // makeConnection
 
  
    private void releaseConnection() {
   
        Debug.print("AccountControllerBean releaseConnection");

        try {
            con.close();
        } catch (SQLException ex) {
             throw new EJBException("releaseConnection: " + ex.getMessage());
        }

    } // releaseConnection

    private void insertXref (String customerId, String accountId) 
        throws SQLException {
   
        Debug.print("AccountControllerBean insertXref");         

        String insertStatement =
            "insert into customer_account_xref " +
            "values ( ? , ? )";
        PreparedStatement prepStmt = 
            con.prepareStatement(insertStatement);
   
        prepStmt.setString(1, customerId);
        prepStmt.setString(2, accountId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }

    private void deleteAllAccountInXref (String accountId)
        throws SQLException {
   
        Debug.print("AccountControllerBean deleteAllAccountInXref");         

        String deleteStatement =
            "delete from customer_account_xref " +
            "where account_id  = ? ";
        PreparedStatement prepStmt = 
            con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, accountId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }


    private ArrayList getCustomerIds(String accountId) 
        throws SQLException {
   
        Debug.print("AccountControllerBean getCustomerIds");

        String selectStatement =
                "select customer_id " +
                "from customer_account_xref " +
                "where account_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, accountId);
        ResultSet rs = prepStmt.executeQuery();
        ArrayList a = new ArrayList();
   
        while (rs.next()) {
            a.add(rs.getString(1));
        }
   
        prepStmt.close();
        return a;
    } // getCustomerIds


    private void deleteOneCustomerInXref (String customerId, String accountId)
        throws SQLException {
   
        Debug.print("AccountControllerBean deleteOneCustomerInXref");         

        String deleteStatement =
            "delete from customer_account_xref " +
            "where account_id  = ? and customer_id = ? ";
        PreparedStatement prepStmt = 
            con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, accountId);
        prepStmt.setString(2, customerId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }


} // AccountControllerBean
