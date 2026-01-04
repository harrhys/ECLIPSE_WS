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
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import com.sun.ebank.util.AccountDetails;

public interface Account extends EJBObject {

    public AccountDetails getDetails() 
        throws RemoteException;

    public BigDecimal getBalance() 
        throws RemoteException;

    public String getType()
        throws RemoteException;

    public BigDecimal getCreditLine()
        throws RemoteException;

    public void setType(String type)
        throws RemoteException;

    public void setDescription(String description)
        throws RemoteException;

    public void setBalance(BigDecimal balance)
        throws RemoteException;

    public void setCreditLine(BigDecimal creditLine)
        throws RemoteException;

    public void setBeginBalance(BigDecimal beginBalance)
        throws RemoteException;

    public void setBeginBalanceTimeStamp(Date beginBalanceTimeStamp)
        throws RemoteException;

} // Account
