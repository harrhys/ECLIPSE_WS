package samples.transactions.ejb.bmt.teller.ejb;
/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.rmi.RemoteException;
import javax.ejb.*;

/**
 * Home interface for the OrderBean. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */


public interface TellerHome extends EJBHome {

/**
 * Gets a reference to the remote interface to the Order bean.
 * @exception CreateException 
 * @exception RemoteException
 *
 */

    public Teller create(String id)
        throws RemoteException, CreateException;
}
