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
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import com.sun.ebank.util.TxDetails;

public interface Tx extends EJBObject {

    public TxDetails getDetails() 
        throws RemoteException;

} // Tx
