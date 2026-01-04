/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.tx;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import java.math.*;
import javax.ejb.*;
import javax.naming.*;
import com.sun.ebank.ejb.exception.MissingPrimaryKeyException;
import com.sun.ebank.util.Debug;
import com.sun.ebank.util.TxDetails;
import com.sun.ebank.util.CodedNames;
import com.sun.ebank.util.DBHelper;

public class TxBean implements EntityBean {

    private String txId;
    private String accountId;
    private java.util.Date timeStamp;
    private BigDecimal amount;
    private BigDecimal balance;
    private String description;

    private EntityContext context;
    private Connection con;

    // business methods

    public TxDetails getDetails() {

        Debug.print("TxBean  getDetails");

        return new TxDetails (txId, accountId, timeStamp, 
            amount, balance, description);
    }


    // ejb methods 

    public String ejbCreate (String txId, String accountId,
        java.util.Date timeStamp, BigDecimal amount, BigDecimal balance,
        String description) 
        throws CreateException, MissingPrimaryKeyException {

        Debug.print("TxBean ejbCreate");

        if ((txId == null) || (txId.trim().length() == 0)) {
            throw new MissingPrimaryKeyException
            ("ejbCreate: txId arg is null or empty");
        }

        this.txId = txId;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.balance = balance;
        this.description = description;

        try {
            insertRow();
        } catch (Exception ex) {
             throw new EJBException("ejbCreate: " + ex.getMessage());
        }
        return txId;
  }

    public String ejbFindByPrimaryKey(String primaryKey) 
        throws FinderException {
   
        Debug.print("TxBean ejbFindByPrimaryKey");

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

    public Collection ejbFindByAccountId(java.util.Date startDate, 
        java.util.Date endDate, String accountId) 
        throws FinderException {
   
        Debug.print("TxBean ejbFindByAccountId");

        Collection result;

        try {
            result = selectByAccountId(accountId, startDate, endDate);
        } catch (Exception ex) {
              throw new EJBException("ejbFindByAccountId " + 
                  ex.getMessage());
        }
        return result;
    }

    public void ejbRemove() {
   
        Debug.print("TxBean ejbRemove");

        try {
            deleteRow(txId);
         } catch (Exception ex) {
              throw new EJBException("ejbRemove: " + 
                  ex.getMessage());
         }
    } 
   
    public void setEntityContext(EntityContext context) {
   
        Debug.print("TxBean setEntityContext");
        this.context = context;
    }

    public void unsetEntityContext() {
   
        Debug.print("TxBean unsetEntityContext");
    }
   
    public void ejbLoad() {
   
        Debug.print("TxBean ejbLoad");

        try {
            loadTx();
         } catch (Exception ex) {
              throw new EJBException("ejbLoad: " + 
                  ex.getMessage());
         }
    }
    
    public void ejbStore() {
   
        Debug.print("TxBean ejbStore");

        try {
            storeTx();
         } catch (Exception ex) {
              throw new EJBException("ejbStore: " + 
                  ex.getMessage());
         }
    }
   
    public void ejbActivate() {

        Debug.print("TxBean ejbActivate");
        txId = (String)context.getPrimaryKey();
    }

    public void ejbPassivate() {

        Debug.print("TxBean ejbPassivate");
        txId = null;
    }

    public void ejbPostCreate(String txId, String accountId,
        java.util.Date timeStamp, BigDecimal amount, BigDecimal balance,
        String description) {}

/*********************** Database Routines *************************/

   
    private void makeConnection() {
   
        Debug.print("TxBean makeConnection");

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
   
        Debug.print("TxBean releaseConnection");

        try {
            con.close();
        } catch (SQLException ex) {
             throw new EJBException("releaseConnection: " + ex.getMessage());
        }

    } // releaseConnection


    private void insertRow () throws SQLException {
   
        Debug.print("TxBean insertRow");         

        makeConnection();
        String insertStatement =
            "insert into tx values ( ? , ? , ? , ? , ? , ? )" ;
        PreparedStatement prepStmt = 
            con.prepareStatement(insertStatement);
   
        prepStmt.setString(1, txId);
        prepStmt.setString(2, accountId);
        prepStmt.setDate(3, DBHelper.toSQLDate(timeStamp));
        prepStmt.setBigDecimal(4, amount);
        prepStmt.setBigDecimal(5, balance);
        prepStmt.setString(6, description);

        prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
    }
   
    private void deleteRow(String id) throws SQLException {
   
        Debug.print("TxBean deleteRow");

        makeConnection();
        String deleteStatement =
                "delete from tx where tx_id = ? ";
        PreparedStatement prepStmt =
                con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, id);
        prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
    }
   
    private boolean selectByPrimaryKey(String primaryKey) 
        throws SQLException {
   
        Debug.print("TxBean selectByPrimaryKey");

        makeConnection();
        String selectStatement =
                "select tx_id " +
                "from tx where tx_id = ? ";
        PreparedStatement prepStmt =
                con.prepareStatement(selectStatement);
        prepStmt.setString(1, primaryKey);
   
        ResultSet rs = prepStmt.executeQuery();
        boolean result = rs.next();
        prepStmt.close();
        releaseConnection();
        return result;
    }
   
    private Collection selectByAccountId(String accountId, 
        java.util.Date startDate, java.util.Date endDate) 
        throws SQLException {
   
        Debug.print("TxBean selectByAccountId");

        makeConnection();
        String selectStatement =
                "select tx_id " +
                "from tx " +
                "where (account_id = ?) " +
                "and ((time_stamp >= ?) and (time_stamp <= ?)) ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, accountId);
        prepStmt.setDate(2, DBHelper.toSQLDate(startDate));
        prepStmt.setDate(3, DBHelper.toSQLDate(endDate));
        ResultSet rs = prepStmt.executeQuery();
        ArrayList a = new ArrayList();
   
        while (rs.next()) {
            a.add(rs.getString(1));
        }
   
        prepStmt.close();
        releaseConnection();
        return a;
    }
   
    private void loadTx() throws SQLException {
   
        Debug.print("TxBean loadTx");

        makeConnection();
        String selectStatement =
                "select account_id, time_stamp, amount, balance, description " +
                "from tx where tx_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, txId);

        ResultSet rs = prepStmt.executeQuery();
   
        if (rs.next()) {
            accountId = rs.getString(1);
            timeStamp = rs.getDate(2);
            amount = rs.getBigDecimal(3);
            balance = rs.getBigDecimal(4);
            description = rs.getString(5);
            prepStmt.close();
            releaseConnection();
        }
        else {
            prepStmt.close();
            releaseConnection();
            throw new NoSuchEntityException("Row for id " + 
                txId + " not found in database.");
        }
    }
   

    private void storeTx() throws SQLException {
   
        Debug.print("TxBean storeTx");

        makeConnection();
        String updateStatement =
                "update tx " +
                "set account_id = ? , time_stamp = ? , " +
                "amount = ? , balance = ? , description = ?  " +
                "where tx_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(updateStatement);
   
        prepStmt.setString(1, accountId);
        prepStmt.setDate(2, DBHelper.toSQLDate(timeStamp));
        prepStmt.setBigDecimal(3, amount);
        prepStmt.setBigDecimal(4, balance);
        prepStmt.setString(5, description);
        prepStmt.setString(6, txId);

        int rowCount = prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
   
        if (rowCount == 0) {
            throw new EJBException("Storing row for id " + txId + " failed.");
        }
    }
   
} // TxBean 
