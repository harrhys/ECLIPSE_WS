/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */


package samples.jdbc.transactions.ejb;
import java.rmi.RemoteException;
import javax.ejb.*;

/**
 * Home interface for the Warehouse EJB. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */


public interface WarehouseHome extends EJBHome {

    /** 
	  * Gets a reference to the remote interface to the Warehouse bean.  
	  * @exception throws CreateException and RemoteException.  
	  * 
	  */

    public Warehouse create()
        throws RemoteException, CreateException;
}
