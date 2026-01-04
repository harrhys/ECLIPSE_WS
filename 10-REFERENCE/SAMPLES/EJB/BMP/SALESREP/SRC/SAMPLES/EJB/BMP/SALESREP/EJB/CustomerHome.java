package samples.ejb.bmp.salesrep.ejb;
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

/**
  * Remote interface for the <code>CustomerBean</code>. The remote interface, </code>Customer</code>
  * defines all possible business methods for the bean. These are methods, going to be invoked
  * remotely by clients, once they have a reference to the remote interface.
  *
  * Clients generally take the help of JNDI to lookup the bean's home interface and
  * then use the home interface to obtain references to the bean's remote interface.
  *
  * @see CustomerHome
  * @see CustomerBean
  */

public interface CustomerHome extends EJBHome {

  /** 
   * Returns a reference to the CustomerBean object given the customerId and salesRepId.
   * @param customerId
   * @param salesRepId
   * @exception RemoteException 
   * @exception CreateException 
   */

    public Customer create(String customerId, String salesRepId,
        String name)
        throws RemoteException, CreateException;
    
  /** 
   * Returns a reference to the CustomerBean object given the customerId.
   * @param customerId
   * @exception RemoteException 
   * @exception CreateException 
   */
    public Customer findByPrimaryKey(String customerId) 
        throws FinderException, RemoteException;

  /** 
   * Returns a Collection of CustomerBean objects given the salesRepId.
   * @param salesRepId
   * @exception RemoteException 
   * @exception FinderException 
   */
    public Collection findBySalesRep(String salesRepId)
        throws FinderException, RemoteException;

}
