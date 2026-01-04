/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.stateless.checker.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for the <code>CheckerBean</code>. Clients generally use home interface
 * to obtain references to the bean's remote interface, <code>Checker</code>.
 *
 * @see Checker
 * @see CheckerBean
 */
public interface CheckerHome extends EJBHome { 
    /**
     * Gets a reference to the remote interface of the <code>CheckerBean</code>.
     * @param person name of the person.
     * @exception throws CreateException and RemoteException.     
     *
     */
    Checker create(String person) throws RemoteException, CreateException;
}
