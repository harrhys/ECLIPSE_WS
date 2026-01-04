/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.customer;

import java.util.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import com.sun.ebank.util.CustomerDetails;

public interface Customer extends EJBObject {

    public CustomerDetails getDetails() 
        throws RemoteException;

    public void setLastName(String lastName)
        throws RemoteException;

    public void setFirstName(String firstName)
        throws RemoteException;

    public void setMiddleInitial(String middleInitial)
        throws RemoteException;

    public void setStreet(String street)
        throws RemoteException;

    public void setCity(String city)
        throws RemoteException;

    public void setState(String state)
        throws RemoteException;

    public void setZip(String zip)
        throws RemoteException;

    public void setPhone(String phone)
        throws RemoteException;

    public void setEmail(String email)
        throws RemoteException;

} // Customer
