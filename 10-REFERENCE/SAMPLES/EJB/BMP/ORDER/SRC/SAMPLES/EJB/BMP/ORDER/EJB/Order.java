package samples.ejb.bmp.order.ejb;
/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;
import samples.ejb.bmp.order.util.*;

/**
  * Remote interface for the <code>OrderBean</code>. The remote interface, </code>Order</code>
  * defines all possible business methods for the bean. These are methods, going to be invoked
  * remotely by clients, once they have a reference to the remote interface.
  *
  * Clients generally take the help of JNDI to lookup the bean's home interface and
  * then use the home interface to obtain references to the bean's remote interface.
  *
  * @see OrderHome
  * @see OrderBean
  */

public interface Order extends EJBObject {

  /**
   * Returns the LineItems that make up this particular Order.
   * @exception RemoteException
   */
 
   public ArrayList getLineItems()
      throws RemoteException;
  /**
   * Returns the CustomerId for this particular Order.
   * @exception RemoteException
   */

   public String getCustomerId()
      throws RemoteException;
   
  /**
   * Returns the Total Price for this particular Order.
   * @exception RemoteException
   */

   public double getTotalPrice()
      throws RemoteException;

  /**
   * Returns the Status for this particular Order.
   * @exception RemoteException
   */

   public String getStatus()
      throws RemoteException;

}
