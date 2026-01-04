/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.account;

import java.util.*;
import java.math.*;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.sun.ebank.ejb.exception.MissingPrimaryKeyException;

public interface AccountHome extends EJBHome {

    public Account create (String accountId, String type, String description,
        BigDecimal balance, BigDecimal creditLine, BigDecimal beginBalance,
        Date beginBalanceTimeStamp, ArrayList customerIds) 
        throws RemoteException, CreateException, MissingPrimaryKeyException;

    public Account findByPrimaryKey(String accountId)
        throws FinderException, RemoteException;

    public Collection findByCustomerId(String customerId)
        throws FinderException, RemoteException;

} // AccountHome
