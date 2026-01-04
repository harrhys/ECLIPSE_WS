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

public interface Customer extends javax.ejb.EJBObject {
    public double getBalance() throws RemoteException;

    public void doCredit(double amount) throws RemoteException;

    public void doDebit(double amount) throws RemoteException;
}
