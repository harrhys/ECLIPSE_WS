/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.ejb.stateful.simple.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for the <code>CartBean</code>. Clients generally use home interface
 * to obtain references to the bean's remote interface, <code>Cart</code>.
 *
 * @see Cart
 * @see CartBean
 */
public interface CartHome extends EJBHome {
 
    /**
     * Gets a reference to the remote interface of the <code>CartBean</code>.
     * @param person name of the person.
     * @exception throws CreateException and RemoteException.     
     *
     */
    Cart create(String person) throws RemoteException, CreateException;

    /**
     * Gets a reference to the remote interface of the <code>CartBean</code>.
     * @param person name of the person.
     * @param id id of the person.
     * @exception throws CreateException and RemoteException.     
     *
     */
    Cart create(String person, String id) throws RemoteException, 
                                                 CreateException; 
}
