/*
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.rmi.simple.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for the ConverterEJB. Clients generally use home
 * interface to obtain references to the bean's remote interface.
 */
public interface ConverterHome extends EJBHome {
    /**
     * Gets a reference to the remote interface to the Converter bean.
     * @exception throws CreateException and RemoteException.
     *
     */
    Converter create() throws RemoteException, CreateException;
}
