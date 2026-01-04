/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.customer;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import com.sun.ebank.ejb.exception.MissingPrimaryKeyException;
import com.sun.ebank.util.Debug;
import com.sun.ebank.util.CustomerDetails;
import com.sun.ebank.util.CodedNames;

public class CustomerBean implements EntityBean {


    private String customerId;
    private String lastName;
    private String firstName;
    private String middleInitial;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;

    private EntityContext context;
    private Connection con;

    // business methods

    public CustomerDetails getDetails() {

        Debug.print("CustomerBean getDetails");

        return new CustomerDetails (customerId, lastName, 
            firstName, middleInitial, street, city, state, 
            zip, phone, email);
    }


    public void setLastName(String lastName) {

        Debug.print("CustomerBean setLastName");
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {

        Debug.print("CustomerBean setFirstName");
        this.firstName = firstName;
    }

    public void setMiddleInitial(String middleInitial) {

        Debug.print("CustomerBean setMiddleInitial");
        this.middleInitial = middleInitial;
    }

    public void setStreet(String street) {

        Debug.print("CustomerBean setStreet");

        this.street = street;
    }

    public void setCity(String city) {

        Debug.print("CustomerBean setCity");
        this.city = city;
    }

    public void setState(String state) {

        Debug.print("CustomerBean setState");
        this.state = state;
    }

    public void setZip(String zip) {

        Debug.print("CustomerBean setZip");
        this.zip = zip;
    }

    public void setPhone(String phone) {

        Debug.print("CustomerBean setPhone");
        this.phone = phone;
    }

    public void setEmail(String email) {

        Debug.print("CustomerBean setEmail");
        this.email = email;
    }

    // ejb methods 

    public String ejbCreate (String customerId, String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email)
        throws CreateException, MissingPrimaryKeyException {

        Debug.print("CustomerBean ejbCreate");

        if ((customerId == null) || (customerId.trim().length() == 0)) {
            throw new MissingPrimaryKeyException
            ("ejbCreate: customerId arg is null or empty");
        }

        this.customerId = customerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;

        try {
            insertRow();
        } catch (Exception ex) {
             throw new EJBException("ejbCreate: " + 
                 ex.getMessage());
        }
  
        return customerId;
  }

    public String ejbFindByPrimaryKey(String primaryKey) 
        throws FinderException {
   
        Debug.print("CustomerBean ejbFindByPrimaryKey");

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

    public Collection ejbFindByAccountId(String accountId)
        throws FinderException {
   
        Debug.print("CustomerBean ejbFindByAccountId");

        Collection result;

        try {
            result = selectByAccountId(accountId);
        } catch (Exception ex) {
              throw new EJBException("ejbFindByAccountId " + 
                  ex.getMessage());
        }
        return result;
    }

    public Collection ejbFindByLastName(String lastName)
        throws FinderException {
   
        Debug.print("CustomerBean ejbFindByLastName");

        Collection result;

        try {
            result = selectByLastName(lastName);
        } catch (Exception ex) {
              throw new EJBException("ejbFindByLastName " + 
                  ex.getMessage());
        }
        return result;
    }

    public void ejbRemove() {
   
        Debug.print("CustomerBean ejbRemove");

        try {
            deleteRow(customerId);
         } catch (Exception ex) {
              throw new EJBException("ejbRemove: " + 
                  ex.getMessage());
         }
    } 
   
    public void setEntityContext(EntityContext context) {
   
        Debug.print("CustomerBean setEntityContext");
        this.context = context;
    }

    public void unsetEntityContext() {
   
        Debug.print("CustomerBean unsetEntityContext");
    }
   
    public void ejbLoad() {
   
        Debug.print("CustomerBean ejbLoad");

        try {
            loadCustomer();
         } catch (Exception ex) {
              throw new EJBException("ejbLoad: " + 
                  ex.getMessage());
         }
    }
    
    public void ejbStore() {
   
        Debug.print("CustomerBean ejbStore");

        try {
            storeCustomer();
         } catch (Exception ex) {
              throw new EJBException("ejbStore: " + 
                  ex.getMessage());
         }
    }
   
    public void ejbActivate() {

        Debug.print("CustomerBean ejbActivate");

        customerId = (String)context.getPrimaryKey();
    }

    public void ejbPassivate() {

        Debug.print("CustomerBean ejbPassivate");

        customerId = null;
    }


    public void ejbPostCreate(String customerId, String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email) {}

/*********************** Database Routines *************************/

   
    private void makeConnection() {
   
        Debug.print("CustomerBean makeConnection");

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
   
        Debug.print("CustomerBean releaseConnection");

        try {
            con.close();
        } catch (SQLException ex) {
             throw new EJBException("releaseConnection: " + ex.getMessage());
        }

    } // releaseConnection


    private void insertRow () throws SQLException {
   
        Debug.print("CustomerBean insertRow");         

        makeConnection();
        String insertStatement =
            "insert into customer values ( ? , ? , ? , ? , ? , " +
            " ? , ? , ? , ? , ? )";
        PreparedStatement prepStmt = 
            con.prepareStatement(insertStatement);
   
        prepStmt.setString(1, customerId);
        prepStmt.setString(2, lastName);
        prepStmt.setString(3, firstName);
        prepStmt.setString(4, middleInitial);
        prepStmt.setString(5, street);
        prepStmt.setString(6, city);
        prepStmt.setString(7, state);
        prepStmt.setString(8, zip);
        prepStmt.setString(9, phone);
        prepStmt.setString(10, email);

        prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
    }
   
    private void deleteRow(String id) throws SQLException {
   
        Debug.print("CustomerBean deleteRow");

        makeConnection();
        String deleteStatement =
                "delete from customer where customer_id = ? ";
        PreparedStatement prepStmt =
                con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, id);
        prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
    }
   
    private boolean selectByPrimaryKey(String primaryKey) 
        throws SQLException {
   
        Debug.print("CustomerBean selectByPrimaryKey");

        makeConnection();
        String selectStatement =
                "select customer_id " +
                "from customer where customer_id = ? ";
        PreparedStatement prepStmt =
                con.prepareStatement(selectStatement);
        prepStmt.setString(1, primaryKey);
   
        ResultSet rs = prepStmt.executeQuery();
        boolean result = rs.next();
        prepStmt.close();
        releaseConnection();
        return result;
    }
   
    private Collection selectByAccountId(String accountId) 
        throws SQLException {
   
        Debug.print("CustomerBean selectByAccountId");

        makeConnection();
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
        releaseConnection();
        return a;
    }
   
    private Collection selectByLastName(String lastName) 
        throws SQLException {
   
        Debug.print("CustomerBean selectByLastName");

        makeConnection();
        String selectStatement =
                "select customer_id " +
                "from customer " +
                "where last_name = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, lastName);
        ResultSet rs = prepStmt.executeQuery();
        ArrayList a = new ArrayList();
   
        while (rs.next()) {
            a.add(rs.getString(1));
        }
   
        prepStmt.close();
        releaseConnection();
        return a;
    }
   
    private void loadCustomer() throws SQLException {
   
        Debug.print("CustomerBean loadCustomer");

        makeConnection();
        String selectStatement =
                "select last_name, first_name, middle_initial, " +
                "street, city, state, zip, phone, email " +
                "from customer where customer_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(selectStatement);
   
        prepStmt.setString(1, customerId);
   
        ResultSet rs = prepStmt.executeQuery();
   
        if (rs.next()) {
            lastName = rs.getString(1);
            firstName = rs.getString(2);
            middleInitial = rs.getString(3);
            street = rs.getString(4);
            city = rs.getString(5);
            state = rs.getString(6);
            zip = rs.getString(7);
            phone = rs.getString(8);
            email = rs.getString(9);
            prepStmt.close();
            releaseConnection();
        }
        else {
            prepStmt.close();
            releaseConnection();
            throw new NoSuchEntityException("Row for id " + 
                customerId + " not found in database.");
        }
    }
   

    private void storeCustomer() throws SQLException {
   
        Debug.print("CustomerBean storeCustomer");

        makeConnection();
        String updateStatement =
                "update customer " +
                "set last_name = ? , first_name = ? , " +
                "middle_initial = ? , street = ? , city = ? , " +
                "state = ? , zip = ? , phone = ? , email = ? " +
                "where customer_id = ? ";
        PreparedStatement prepStmt = 
                con.prepareStatement(updateStatement);
   
        prepStmt.setString(1, lastName);
        prepStmt.setString(2, firstName);
        prepStmt.setString(3, middleInitial);
        prepStmt.setString(4, street);
        prepStmt.setString(5, city);
        prepStmt.setString(6, state);
        prepStmt.setString(7, zip);
        prepStmt.setString(8, phone);
        prepStmt.setString(9, email);
        prepStmt.setString(10, customerId);

        int rowCount = prepStmt.executeUpdate();
        prepStmt.close();
        releaseConnection();
   
        if (rowCount == 0) {
            throw new EJBException("Storing row for id " + customerId + " failed.");
        }
    }
   
} // CustomerBean 
