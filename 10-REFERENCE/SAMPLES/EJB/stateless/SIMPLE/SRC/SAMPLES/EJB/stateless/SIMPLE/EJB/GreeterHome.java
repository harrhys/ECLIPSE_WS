/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.stateless.simple.ejb;

/**
 * Home interface for the GreeterEJB. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */
public interface GreeterHome extends javax.ejb.EJBHome { 
    /**
     * Gets a reference to the remote interface to the Greeter bean.
     * @exception throws CreateException and RemoteException.
     *
     */
    public Greeter create() throws java.rmi.RemoteException, javax.ejb.CreateException; 
} 
