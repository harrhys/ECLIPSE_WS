/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.tx;

import java.util.*;
import java.math.*;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.sun.ebank.ejb.exception.*;

public interface TxHome extends EJBHome {

    public Tx create (String txId, String accountId,
        Date timeStamp, BigDecimal amount, BigDecimal balance,
        String description) 
        throws RemoteException, CreateException, MissingPrimaryKeyException;

    public Tx findByPrimaryKey(String txId)
        throws FinderException, RemoteException;

    public Collection findByAccountId(Date startDate, Date endDate,
        String accountId)
        throws FinderException, RemoteException;

} // TxHome
