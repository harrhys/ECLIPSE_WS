package samples.ejb.bmp.enroller.ejb;
/**
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Home interface for the EnrollerBean. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */

public interface EnrollerHome extends EJBHome {

 /**
   * Gets a reference to the remote interface to the EnrollerBean bean.
   * @exception throws CreateException and RemoteException.
   *
  */
    Enroller create() throws RemoteException, CreateException;
}
