/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.stateless.checker.ejb;

import java.util.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface for the <code>CheckerBean</code>. The remote interface, </code>Checker</code>
 * defines all possible business methods for the bean. These are methods, going to be invoked
 * remotely by clients, once they have a reference to the remote interface.
 *
 * Clients generally take the help of JNDI to lookup the bean's home interface and
 * then use the home interface to obtain references to the bean's remote interface.
 *
 * @see CheckerHome
 * @see CheckerBean
 */
public interface Checker extends EJBObject {
 
    /**
     * Returns the amount after applying discount on a given amount.
     * @param amount amount, on which discount is to be applied.
     */
   public double applyDiscount(double amount) throws RemoteException;
}
