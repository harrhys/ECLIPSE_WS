//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
package samples.transactions.ejb.bmt.simple.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/*
 * Remote interface for the <code>GlobalBankServiceBean</code>.  The remote
 * interface, <code>GlobalBankService</code> defines all possible
 * business methods for the bean.  These are the methods which are going
 * to be invoked remotely by the clients, once they have a reference to the
 * remote interface.
 * Clients generally take the help of JNDI to lookup the bean's home interface 
 * and then use the home interface to obtain reference to the bean's remote interface.
 * @see GlobalBankServiceHome
 * @see GlobalBankServiceEJB
 */

public interface GlobalBankService extends EJBObject {

/* Transfer the funds from one bank account to another.
 * @param fromBank String for the Source account
 * @param FromAccountID String for the Source account ID
 * @param toBank String for the Destination account
 * @param toAccountID String for the Destination Account ID
 * @param transferAmount double amount to be transferred.
 * @exception RemoteException
 */

    public void transferFunds( String fromBank,
                               String fromAccountId,
                               String toBank,
                               String toAccountId,
                               double transferAmount )
    throws RemoteException;


   /**
   * Returns the old balance from the source account
   * @exception RemoteException
   */

    public double getFromAccountOldBalance( )
    throws RemoteException;

   /**
   * Returns the new balance from the source account.
   * @exception RemoteException
   */

    public double getFromAccountNewBalance( )
    throws RemoteException;
 
    /**
    * Returns the old balance from the destination account
    * @exception RemoteException
    */

    public double getToAccountOldBalance( )
    throws RemoteException;
    /**
    * Returns the new balance from the destination account
    * @exception RemoteException
    */

    public double getToAccountNewBalance( )
    throws RemoteException;
}
