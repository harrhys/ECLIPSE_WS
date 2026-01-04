/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;

/**
 * Home interface for the ModuleLibraryEJB. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */
public interface ModuleLibraryHome extends EJBHome {

    /**
     * Gets a reference to the remote interface to the ModuleLibrary bean.
     * @exception throws CreateException and RemoteException.
     *
     */
	public ModuleLibrary create() throws CreateException, RemoteException;
}
