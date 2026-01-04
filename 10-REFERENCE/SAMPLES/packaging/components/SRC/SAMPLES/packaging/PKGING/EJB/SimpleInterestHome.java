/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Home interface for the SimpleInterestEJB. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */
public interface SimpleInterestHome extends EJBHome {
    /**
     * Gets a reference to the remote interface to the SimpleInterest bean.
     * @exception throws CreateException and RemoteException.
     *
     */
	public SimpleInterest create() throws CreateException, RemoteException;
}
