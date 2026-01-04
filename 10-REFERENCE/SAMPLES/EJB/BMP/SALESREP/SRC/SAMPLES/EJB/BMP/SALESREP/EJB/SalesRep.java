package samples.ejb.bmp.salesrep.ejb;
/**
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.*;

/**
  * Remote interface for the <code>SalesRepBean</code>. The remote interface, </code>SalesRep</code>
  * defines all possible business methods for the bean. These are methods, going to be invoked
  * remotely by clients, once they have a reference to the remote interface.
  *
  * Clients generally take the help of JNDI to lookup the bean's home interface and
  * then use the home interface to obtain references to the bean's remote interface.
  *
  * @see SalesRepHome
  * @see SalesRepBean
  */

public interface SalesRep extends EJBObject {
  
   /**
    * Returns an ArrayList of CustomersIds associated with this SalesRep
    * @exception RemoteException
    *
    */
 
   public ArrayList getCustomerIds()
      throws RemoteException;

   /**
    * Returns the name of this SalesRep
    * @exception RemoteException
    *
    */
   public String getName()
      throws RemoteException;

   /**
    * Sets the name of this SalesRep
    * @param name
    * @exception RemoteException
    *
    */
   public void setName(String name) 
      throws RemoteException;

}
