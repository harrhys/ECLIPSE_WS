//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
package samples.transactions.ejb.cmt.simple.ejb;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Home interface for the <code>GlobalBankServiceEJB</code>. 
 * Clients generally use home interface to obtain references to the bean's remote interface, <code>GlobalBankService</code>.
 *
 * @see GlobalBankService
 * @see GlobalBankServiceEJB
 */

public interface GlobalBankServiceHome extends EJBHome {

/**
     * Gets a reference to the remote interface of the <code>GlobalBankService</code>.
     * @exception throws CreateException and RemoteException.
     *
     */
    public GlobalBankService create()
    throws CreateException, RemoteException;
}
