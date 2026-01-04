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
import javax.ejb.*;
import java.rmi.RemoteException;
import com.sun.ebank.ejb.exception.*;

public interface CustomerHome extends EJBHome {

    public Customer create (String customerId, String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email)
        throws RemoteException, CreateException, MissingPrimaryKeyException;

    public Customer findByPrimaryKey(String customerId)
        throws FinderException, RemoteException;

    public Collection findByAccountId(String accountId)
        throws FinderException, RemoteException;

    public Collection findByLastName(String lastName)
        throws FinderException, RemoteException;

} // CustomerHome
