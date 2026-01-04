/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.customer;

import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import com.sun.ebank.util.CustomerDetails;
import com.sun.ebank.ejb.exception.*;

public interface CustomerController extends EJBObject {


    // customer creation and removal methods

    public String createCustomer (String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email)
        throws InvalidParameterException, RemoteException;

        // makes a new customer and enters it into db,
        // returns customerId

    public void removeCustomer(String customerId)
       throws RemoteException, CustomerNotFoundException,
       InvalidParameterException;

       // removes customer from db

    // getters

    public ArrayList getCustomersOfAccount(String accountId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;;

        // returns an ArrayList of CustomerDetails objects
        // that correspond to the customers for the specified
        // account

    public CustomerDetails getDetails(String customerId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;

        // returns the CustomerDetails for the specified customer


    public ArrayList getCustomersOfLastName(String lastName)
        throws InvalidParameterException, RemoteException;

        // returns an ArrayList of CustomerDetails objects
        // that correspond to the customers for the specified
        // last name; if now customers are found the ArrayList
        // is empty

    // setters

    public void setName(String lastName, String firstName,
        String middleInitial, String customerId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;

    public void setAddress(String street, String city, 
        String state, String zip, String phone, String email,
        String customerId)
        throws RemoteException, CustomerNotFoundException,
        InvalidParameterException;

} // CustomerController
