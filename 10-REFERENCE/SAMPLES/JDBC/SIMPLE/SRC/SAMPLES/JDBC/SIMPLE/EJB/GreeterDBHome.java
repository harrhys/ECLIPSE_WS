/**
  Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
  */
package samples.jdbc.simple.ejb;  

/**
 * Home interface for the GreeterDB EJB. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 */


public interface GreeterDBHome extends javax.ejb.EJBHome { 
  /** 
    * Gets a reference to the remote interface to the GreeterDBBean.  
	* @exception throws CreateException and RemoteException.  
	* 
	*/

  public GreeterDB create() throws java.rmi.RemoteException, javax.ejb.CreateException; 
} 
