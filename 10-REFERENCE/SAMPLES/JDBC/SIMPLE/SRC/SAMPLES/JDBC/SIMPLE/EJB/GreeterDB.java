/**
  Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
  */

package samples.jdbc.simple.ejb; 

/**
 * Remote interface for the GreeterDBEJB. The remote interface defines all possible 
 * business methods for the bean. These are the methods going to be invoked remotely 
 * by the servlets, once they have a reference to the remote interface.  
 * 
 * Servlets generally take the help of JNDI to lookup the bean's home interface and 
 * then use the home interface to obtain references to the bean's remote interface.  
 */

public interface GreeterDB extends javax.ejb.EJBObject { 

  /** 
    * Returns the greeting String such as "Good morning, John"
	* @return the greeting String
	*/

  public String getGreeting() throws java.rmi.RemoteException; 
} 
