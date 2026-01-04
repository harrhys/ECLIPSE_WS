/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package samples.jdbc.transactions.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * Remote interface for the WarehouseEJB. The remote interface defines all possible
  * business methods for the bean. These are the methods going to be invoked remotely 
  * by clients, once they have a reference to the remote interface.  
  * 
  * Clients generally take the help of JNDI to lookup the bean's home interface and 
  * then use the home interface to obtain references to the bean's remote interface.  
  */

public interface Warehouse extends EJBObject {
   
   /** 
     * Updates the received for particular product inside the particular order. 
	 * sets the status to shipped
	 * @param productID. 
	 * @param orderID 
	 * @param quantity the received quantity
	 */

    public void ship(String productId, String orderId, int quantity)
        throws RemoteException;

   /** 
     * Sets the get the status for the particular product inside the particular order.
	 * @param productID. 
	 * @param orderID 
	 * @return the status
	 */
    public String getStatus(String productId, String orderId)
        throws RemoteException;
}
