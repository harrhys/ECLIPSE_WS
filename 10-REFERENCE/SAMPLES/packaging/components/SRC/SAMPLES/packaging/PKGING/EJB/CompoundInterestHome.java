/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Home interface for the CompoundInterestEJB. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */
public interface CompoundInterestHome extends EJBHome {
    /**
     * Gets a reference to the remote interface to the CompoundInterest bean.
     * @exception throws CreateException and RemoteException.
     *
     */
	public CompoundInterest create() throws CreateException, RemoteException;
}
