package samples.transactions.ejb.cmt.bank.ejb;

/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 *
 * Remote interface for the <code>BankBean</code>. The remote interface, </code>Bank</code>
 * defines all possible business methods for the bean. These are methods, going to be invoked
 * remotely by clients, once they have a reference to the remote interface.
 *
 * Clients generally take the help of JNDI to lookup the bean's home interface and
 * then use the home interface to obtain references to the bean's remote interface.
 *
 *
 * @see BankBean
 * @see BankHome
 */

public interface Bank extends EJBObject {
    
/** 
 **
 * Transfers the given amount to the Savings Account.
 * @param amount
 * @exception RemoteException
 * @exception InsufficientBalanceException
 */

    public void transferToSaving(double amount)
        throws RemoteException, InsufficientBalanceException;

/** 
 * Returns the balance of the checking Account.
 * @exception RemoteException
 */
    public double getCheckingBalance()
        throws RemoteException;

/** 
 **
 * Returns the balance of the savings Account.
 * @exception RemoteException
 *
 */
    public double getSavingBalance()
        throws RemoteException;
}
