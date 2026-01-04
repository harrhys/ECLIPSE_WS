/*
 *
 * Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.ejb.bmp.robean.ejb;

import java.rmi.RemoteException;
import javax.ejb.FinderException;

public interface AddressHome extends javax.ejb.EJBHome {
    public Address findByPrimaryKey(String SSN) throws FinderException, RemoteException; 
}

