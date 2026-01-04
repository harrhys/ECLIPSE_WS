package samples.ejb.bmp.salesrep.ejb;
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

 
 /**
  * Remote interface for the <code>CustomerBean</code>. The remote interface, </code>Customer</code>
  * defines all possible business methods for the bean. These are methods, going to be invoked
  * remotely by clients, once they have a reference to the remote interface.
  *
  * Clients generally take the help of JNDI to lookup the bean's home interface and
  * then use the home interface to obtain references to the bean's remote interface.
  * @see CustomerBean
  * @see CustomerHome
  */

public interface Customer extends EJBObject {
 
 /**
  * Returns a SalesRepId for this Customer
  * @exception RemoteException
  */
   public String getSalesRepId()
      throws RemoteException;

 /**
  * Returns a Name for this Customer
  * @exception RemoteException
  */
   public String getName()
      throws RemoteException;

 /**
  * Sets a SalesRepId for this Customer
  * @param salesRepId
  * @exception RemoteException
  */
   public void setSalesRepId(String salesRepId)
      throws RemoteException;

 /**
  * Sets the name for this Customer
  * @param name
  * @exception RemoteException
  */

   public void setName(String name)
      throws RemoteException;
}
