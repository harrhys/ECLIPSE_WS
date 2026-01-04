package samples.ejb.bmp.salesrep.ejb;
/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;

/**
 * Remote interface for the <code>CustomerBean</code>. The remote interface, </code>Customer</code>
 * defines all possible business methods for the bean. These are methods, going to be invoked
 * remotely by clients, once they have a reference to the remote interface.
 *
 * Clients generally take the help of JNDI to lookup the bean's home interface and
 * then use the home interface to obtain references to the bean's remote interface.
 *
 * @see SalesRep
 * @see SalesRepBean
 */

public interface SalesRepHome extends EJBHome {

/**
 * Gets a reference to the remote interface to the SalesRep bean.
 * @param salesRepId
 * @param name
 * @exception CreateException 
 * @exception RemoteException
 */

    public SalesRep create(String salesRepId, String name)
        throws RemoteException, CreateException;
    
/**
 * Gets a reference to the remote interface to the SalesRep bean by Primary Key.
 * @param salesRepId
 * @exception FinderException 
 * @exception RemoteException
 */
    public SalesRep findByPrimaryKey(String salesRepId) 
        throws FinderException, RemoteException;
}
