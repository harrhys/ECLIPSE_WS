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
import java.rmi.RemoteException;
import com.sun.ebank.ejb.exception.CustomerNotFoundException;
import com.sun.ebank.ejb.exception.InvalidParameterException;
import com.sun.ebank.util.Debug;
import com.sun.ebank.util.DBHelper;
import com.sun.ebank.util.CustomerDetails;
import com.sun.ebank.util.EJBGetter;
import com.sun.ebank.util.CodedNames;

public class CustomerControllerBean implements SessionBean {

    private String customerId;
    private CustomerHome customerHome;
    private Customer customer;
    private Connection con;
    
    // customer creation and removal methods

    public String createCustomer (String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email) throws InvalidParameterException {

        // makes a new customer and enters it into db,
        // returns customerId

        Debug.print("CustomerControllerBean createCustomer");

        if (lastName == null) 
            throw new InvalidParameterException("null lastName");
 
        if (firstName == null) 
            throw new InvalidParameterException("null firstName");

        try {
            makeConnection();
            customerId = DBHelper.getNextCustomerId(con);
            customer = customerHome.create(customerId,
                lastName, firstName, middleInitial, street,
                city, state, zip, phone, email);
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("createCustomer: " + ex.getMessage());
        }

        return customerId;
    } // createCustomer

    public void removeCustomer(String customerId) 
        throws CustomerNotFoundException, InvalidParameterException {

       // removes customer from db

        Debug.print("CustomerControllerBean removeCustomer");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        try {
            makeConnection();
            deleteAllCustomerInXref(customerId);
            customer.remove();
            releaseConnection();
        } catch (Exception ex) {
             releaseConnection();
             throw new EJBException
             ("removeCustomer: " + ex.getMessage());
        }

    } // removeCustomer

    // getters

    public ArrayList getCustomersOfAccount(String accountId) 
        throws CustomerNotFoundException, InvalidParameterException {

        // returns an ArrayList of CustomerDetails 
        // that correspond to the accountId specified

        Debug.print("CustomerControllerBean getCustomersOfAccount");

        Collection customerIds;

        if (accountId == null) 
            throw new InvalidParameterException("null accountId");

        try {
            customerIds = customerHome.findByAccountId(accountId);
            if (customerIds.isEmpty())
                throw new CustomerNotFoundException();
        } catch (Exception ex) {
             throw new CustomerNotFoundException();
        }

        ArrayList customerList = new ArrayList();

        try {
            Iterator i = customerIds.iterator();
            while (i.hasNext()) {
                Customer customer = (Customer)i.next();
                CustomerDetails customerDetail = customer.getDetails();
                customerList.add(customerDetail);
            }
        } catch (RemoteException ex) {
             throw new EJBException(": " + ex.getMessage());
        } 

        return customerList;

    } //  getCustomersOfAccount

    public CustomerDetails getDetails(String customerId) 
        throws CustomerNotFoundException, InvalidParameterException {

        // returns the CustomerDetails for the specified customer

        Debug.print("CustomerControllerBean getDetails");

        CustomerDetails result;

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        try {
            result = customer.getDetails();
        } catch (RemoteException ex) {
             throw new EJBException("getDetails: " + ex.getMessage());
        } 

        return result;

    } // getDetails
     

    public ArrayList getCustomersOfLastName(String lastName) 
        throws InvalidParameterException {

        // returns an ArrayList of CustomerDetails 
        // that correspond to the the lastName specified
        // returns null if no customers are found

        Debug.print("CustomerControllerBean getCustomersOfCustomer");

        Collection customerIds;
        ArrayList customerList = new ArrayList();

        if (lastName == null) 
            throw new InvalidParameterException("null lastName");

        try {
            customerIds = customerHome.findByLastName(lastName);
        } catch (Exception ex) {
             return customerList;
        }

        try {
            Iterator i = customerIds.iterator();
            while (i.hasNext()) {
                Customer customer = (Customer)i.next();
                CustomerDetails customerDetail = customer.getDetails();
                customerList.add(customerDetail);
            }
        } catch (RemoteException ex) {
             throw new EJBException("getCustomersOfLastName: " 
                 + ex.getMessage());
        } 

        return customerList;

    } //  getCustomersOfLastName

    // setters

    public void setName(String lastName, String firstName,
        String middleInitial, String customerId)
        throws CustomerNotFoundException, InvalidParameterException {

        Debug.print("CustomerControllerBean setName");

        if (lastName == null) 
            throw new InvalidParameterException("null lastName");
 
        if (firstName == null) 
            throw new InvalidParameterException("null firstName");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        try {
            customer.setLastName(lastName);
            customer.setFirstName(firstName);
            customer.setMiddleInitial(middleInitial);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }

    } // setName

    public void setAddress(String street, String city,
        String state, String zip, String phone, String email,
        String customerId)
        throws CustomerNotFoundException, InvalidParameterException {

        Debug.print("CustomerControllerBean setAddress");

        if (street == null) 
            throw new InvalidParameterException("null street");
 
        if (city == null) 
            throw new InvalidParameterException("null city");

        if (state == null) 
            throw new InvalidParameterException("null state");

        if (customerId == null)
            throw new InvalidParameterException("null customerId" );

        if (customerExists(customerId) == false)
            throw new CustomerNotFoundException(customerId);

        try {
            customer.setStreet(street);
            customer.setCity(city);
            customer.setState(state);
            customer.setZip(zip);
            customer.setPhone(phone);
            customer.setEmail(email);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    } // setAddress
        

    // ejb methods

    public void ejbCreate() {

        Debug.print("CustomerControllerBean ejbCreate");

        try {
            customerHome = EJBGetter.getCustomerHome();
        } catch (Exception ex) {
             Debug.print("CustomerControllerBean catch");
             throw new EJBException("ejbCreate: " +
                 ex.getMessage());
        }

        customer = null;
        customerId = null;
        Debug.print("CustomerControllerBean leaving");
    } // ejbCreate

    public CustomerControllerBean() {}
    public void ejbRemove() {}
    public void ejbActivate() {}
    public void ejbPassivate() {}
    public void setSessionContext(SessionContext sc) {}

    // private methods

    private boolean customerExists(String customerId) {

        // If a business method has been invoked with
        // a different customerId, then update the
        // customerId and customer variables.
        // Return null if the customer is not found.

        Debug.print("CustomerControllerBean customerExists");

        if (customerId.equals(this.customerId) == false) {
            try {
                customer = customerHome.findByPrimaryKey(customerId);
                this.customerId = customerId;
            } catch (Exception ex) {
                return false;
            }
        } // if
        return true;

    } // customerExists

/*********************** Database Routines *************************/

    private void makeConnection() {
   
        Debug.print("CustomerControllerBean makeConnection");

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
   
        Debug.print("CustomerControllerBean releaseConnection");

        try {
            con.close();
        } catch (SQLException ex) {
             throw new EJBException("releaseConnection: " + ex.getMessage());
        }

    } // releaseConnection


    private void deleteAllCustomerInXref (String customerId)
        throws SQLException {
   
        Debug.print("CustomerControllerBean deleteAllCustomerInXref");         

        String deleteStatement =
            "delete from customer_account_xref " +
            "where customer_id  = ? ";
        PreparedStatement prepStmt = 
            con.prepareStatement(deleteStatement);
   
        prepStmt.setString(1, customerId);
        prepStmt.executeUpdate();
        prepStmt.close();
    }

} // CustomerControllerBean
