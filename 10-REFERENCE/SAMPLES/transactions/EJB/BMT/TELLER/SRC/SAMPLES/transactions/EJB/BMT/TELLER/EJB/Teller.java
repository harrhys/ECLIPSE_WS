package samples.transactions.ejb.bmt.teller.ejb;
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
 * Remote interface for the <code>TellerBean</code>. The remote interface, </code>Teller</code>
 * defines all possible business methods for the bean. These are methods, going to be invoked
 * remotely by clients, once they have a reference to the remote interface.
 *
 * Clients generally take the help of JNDI to lookup the bean's home interface and
 * then use the home interface to obtain references to the bean's remote interface.
 *
 * @see TellerHome
 * @see TellerBean
 */

public interface Teller extends EJBObject {
    
 /**
  * Withdraws the specified amount from the bank account
  * @param amount
  * @exception RemoteException
  */

   public void withdrawCash(double amount)
       throws RemoteException;
 /**
  * Returns the checking balance from the bank account
  * @exception RemoteException
  */

   public double getCheckingBalance()
       throws RemoteException;
}
