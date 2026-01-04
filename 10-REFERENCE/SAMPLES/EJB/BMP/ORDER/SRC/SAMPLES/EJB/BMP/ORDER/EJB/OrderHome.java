package samples.ejb.bmp.order.ejb;
/**
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.util.*;
import java.rmi.RemoteException;
import javax.ejb.*;
import samples.ejb.bmp.order.util.*;

/**
 * Home interface for the OrderBean. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */

public interface OrderHome extends EJBHome {

    /**
     * Gets a reference to the remote interface to the Order bean.
     * @exception CreateException 
     * @exception RemoteException
     *
     */
    public Order create(String orderId, String customerId,
        String status, double totalPrice, ArrayList lineItems)
        throws RemoteException, CreateException;

    /**
      * Gets a reference to the remote interface to the Order object by Primary Key.
      * @exception FinderException 
      * @exception RemoteException
      *
      */

    public Order findByPrimaryKey(String orderId) 
        throws FinderException, RemoteException;

    /**
      * Returns a Collection of references to the remote interface to the OrderBean by productId.
      * @exception FinderException
      * @exception RemoteException
      *
      */

    public Collection findByProductId(String productId)
        throws FinderException, RemoteException;

}
