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
import com.sun.ebank.ejb.exception.MissingPrimaryKeyException;
import com.sun.ebank.util.Debug;
import com.sun.ebank.util.CodedNames;
import com.sun.ebank.util.DBHelper;
import com.sun.ebank.util.AccountDetails;

public class AccountBean implements EntityBean {

    private String accountId;
    private String type;
    private String description;
    private BigDecimal balance;
    private BigDecimal creditLine;
    private BigDecimal beginBalance;
    private java.util.Date beginBalanceTimeStamp;
    private ArrayList customerIds;

    private EntityContext context;
    private Connection con;

    // business methods

    public AccountDetails getDetails() {

        Debug.print("AccountBean getDetails");

        try {
            loadCustomerIds();
        } catch (Exception ex) {
             throw new EJBException("loadCustomerIds:  " +
                ex.getMessage());
        }

        return new AccountDetails(accountId, type, description, balance, 
            creditLine, beginBalance, beginBalanceTimeStamp,
            customerIds);
    }

    public BigDecimal getBalance() {
        Debug.print("AccountBean getBalance");
        return balance;
    }

    public String getType() {
        Debug.print("AccountBean getType");
        return type;
    }

    public BigDecimal getCreditLine() {
        Debug.print("AccountBean getCreditLine");
        return creditLine;
    }

    public void setType(String type) {
        Debug.print("AccountBean setType");
        this.type = type;
    }

    public void setDescription(String description) {
        Debug.print("AccountBean setDescription");
        this.description = description;
    }

    public void setBalance(BigDecimal balance) {
        Debug.print("AccountBean setBalance");
        this.balance = balance;
    }

    public void setCreditLine(BigDecimal creditLine) {
        Debug.print("AccountBean setCreditLine");
        this.creditLine = creditLine;
    }

    public void setBeginBalance(BigDecimal beginBalance) {
        Debug.print("AccountBean setBeginBalance");
        this.beginBalance = beginBalance;
    }

    public void setBeginBalanceTimeStamp(java.util.Date beginBalanceTimeStamp) {
        Debug.print("AccountBean setBeginBalanceTimeStamp");
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
    }


    // ejb methods 

    public String ejbCreate(String accountId, String type, String description,
        BigDecimal balance, BigDecimal creditLine, BigDecimal beginBalance,
        java.util.Date beginBalanceTimeStamp, ArrayList customerIds) 
        throws CreateException, MissingPrimaryKeyException {

        Debug.print("AccountBean ejbCreate");

        if ((accountId == null) || (accountId.trim().length() == 0)) {
            throw new MissingPrimaryKeyException
            ("ejbCreate: accountId arg is null or empty");
        }

        this.accountId = accountId;
        this.type = type;
        this.description = description;
        this.balance = balance;
        this.creditLine = creditLine;
        this.beginBalance = beginBalance;
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
        this.customerIds = customerIds;

        try {
            insertRow();
        } catch (Exception ex) {
             throw new EJBException("ejbCreate: " + 
                 ex.getMessage());
        }
  
        return accountId;
  }

    public String ejbFindByPrimaryKey(String primaryKey) 
        throws FinderException {
   
        Debug.print("AccountBean ejbFindByPrimaryKey");

        boolean result;

        try {
            result = selectByPrimaryKey(primaryKey);
        } catch (Exception ex) {
              throw new EJBException("ejbFindByPrimaryKey: " + 
                  ex.getMessage());
        }
   
        if (result) {
            return primaryKey;
        }
        else {
            throw new ObjectNotFoundException
                ("Row for id " + primaryKey + " not found.");
        }
    }

    public Collection ejbFindByCustomerId(String customerId)
        throws FinderException {
   
        Debug.print("AccountBean ejbFindByCustomerId");

        Collection result;
   
        try {
            result = selectByCustomerId(customerId);
        } catch (Exception ex) {
              throw new EJBException("ejbFindByCustomerId " + 
                  ex.getMessage());
        }
        return result;
    }

    public void ejbRemove() {
   
        Debug.print("AccountBean ejbRemove");

        try {
            deleteRow(accountId);
         } catch (Exception ex) {
              throw new EJBException("ejbRemove: " + 
                  ex.getMessage());
         }
    } 
   
    public void setEntityContext(EntityContext context) {
   
        Debug.print("AccountBean setEntityContext");
        this.context = context;
        customerIds = new ArrayList();
    }

    public void unsetEntityContext() {
   
        Debug.print("AccountBean unsetEntityContext");
    }
   
    public void ejbLoad() {
   
        Debug.print("AccountBean ejbLoad");

        try {
            loadAccount();
         } catch (Exception ex) {
              throw new EJBException("ejbLoad: " + 
                  ex.getMessage());
         }
    }
    
    public void ejbStore() {
   
        Debug.print("AccountBean ejbStore");

        try {
            storeAccount();
         } catch (Exception ex) {
              throw new EJBException("ejbStore: " + 
                  ex.getMessage());
         }
    }
   
    public void ejbActivate() {

        Debug.print("AccountBean ejbActivate");
        accountId = (String)context.getPrimaryKey();
    }

    public void ejbPassivate() {

        Debug.print("AccountBean ejbPassivate");
        accountId = null;
    }

    public void ejbPostCreate(String accountId, String type, String description,
        BigDecimal balance, BigDecimal creditLine, BigDecimal beginBalance,
        java.util.Date beginBalanceTimeStamp, ArrayList customerIds) {}


/*********************** Database Routines *************************/

   
    private void makeConnection() {
   
        Debug.print("AccountBean makeConnection");

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
   
        Debug.print("AccountBean releaseConnection");

        try {
            con.close();
        } catch (SQLException ex) {
             throw new EJBException("releaseConnection: " + ex.getMessage());
        }

    } // releaseConnection


    private void insertRow () throws SQLException {
   
        Debug.print("AccountBean insertRow");         

        makeConnection();
        String insertStatement =
            "insert into account values ( ? , ? , ? , ? , ? , ? , ? )";
        PreparedStatement prepStmt = 
            con.prepareStatement(insertStatement);
   
        prepStmt.setString(1, accountId);
        prepStmt.setString(2, type);
        prepStmt.setString(3, description);
        prepStmt.setBigDecimal(4, balance);
        prepStmt.setBigDecimal(5, creditLine);
        prepStmt.setBigDecimal(6, beginBalance);
        prepStmt.setDate(7, DBHelper.toSQLDate(beginBalanceTimeStamp));
   
        prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
    }
   
    private void deleteRow(String id) throws SQLException {
   
        Debug.print("AccountBean deleteRow");

        makeConnection();
        String deleteStatement =
                "delete from account where account_id = ? ";
        PreparedStatement prepStmt =
                con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, id);
        prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
    }
   
    private boolean selectByPrimaryKey(String primaryKey) 
        throws SQLException {
   
        Debug.print("AccountBean selectByPrimaryKey");

        makeConnection();
        String selectStatement =
                "select account_id " +
                "from account where account_id = ? ";
        PreparedStatement prepStmt =
                con.prepareStatement(selectStatement);
        prepStmt.setString(1, primaryKey);
   
        ResultSet rs = prepStmt.executeQuery();
        boolean result = rs.next();
        prepStmt.close();
        releaseConnection();
        return result;
    }
   
    private Collection selectByCustomerId(String customerId) 
        throws SQLException {
   
        Debug.print("AccountBean selectByCustomerId");

        makeConnection();
        String selectStatement =
                "select account_id " +
                "from customer_account_xref " +
                "where customer_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, customerId);
        ResultSet rs = prepStmt.executeQuery();
        ArrayList a = new ArrayList();
   
        while (rs.next()) {
            a.add(rs.getString(1));
        }
   
        prepStmt.close();
        releaseConnection();
        return a;
    }
   
   
    private void loadAccount() throws SQLException {
   
        Debug.print("AccountBean loadAccount");

        makeConnection();
        String selectStatement =
                "select type, description, balance, credit_line, " + 
                "begin_balance, begin_balance_time_stamp " +
                "from account where account_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, accountId);
   
        ResultSet rs = prepStmt.executeQuery();
   
        if (rs.next()) {
            type = rs.getString(1);
            description = rs.getString(2);
            balance = rs.getBigDecimal(3);
            creditLine = rs.getBigDecimal(4);
            beginBalance = rs.getBigDecimal(5);
            beginBalanceTimeStamp = rs.getDate(6);
            prepStmt.close();
            releaseConnection();
        }
        else {
            prepStmt.close();
            releaseConnection();
            throw new NoSuchEntityException("Row for id " + 
                accountId + " not found in database.");
        }
    }
   

    private void loadCustomerIds() throws SQLException {
   
        Debug.print("AccountBean loadCustomerIds");

        makeConnection();
        String selectStatement =
                "select customer_id " + 
                "from customer_account_xref where account_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, accountId);
        ResultSet rs = prepStmt.executeQuery();
        customerIds.clear();

        while (rs.next()) {
            customerIds.add(rs.getString(1));
        }
        prepStmt.close();
        releaseConnection();
    }
   
    private void storeAccount() throws SQLException {
   
        Debug.print("AccountBean storeAccount");

        makeConnection();
        String updateStatement =
                "update account set type =  ? , description = ? , " +
                "balance = ? , credit_line = ? , " +
                "begin_balance = ? , begin_balance_time_stamp = ? " +
                "where account_id = ?";
        PreparedStatement prepStmt = 
                con.prepareStatement(updateStatement);
   
        prepStmt.setString(1, type);
        prepStmt.setString(2, description);
        prepStmt.setBigDecimal(3, balance);
        prepStmt.setBigDecimal(4, creditLine);
        prepStmt.setBigDecimal(5, beginBalance);
        prepStmt.setDate(6, DBHelper.toSQLDate(beginBalanceTimeStamp));
        prepStmt.setString(7, accountId);

        int rowCount = prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
   
        if (rowCount == 0) {
            throw new EJBException("Storing row for id " + accountId + " failed.");
        }
    }
   
} // AccountBean 
