package samples.ejb.bmp.enroller.ejb;
/*
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
 * Home interface for the StudentBean. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */

public interface StudentHome extends EJBHome {

/**
 * Gets a reference to the remote interface to the StudentBean bean.
 * @exception throws CreateException and RemoteException.
 *
 */

public Student create(String studentId, String name)
    throws RemoteException, CreateException;

/**
  * Gets a reference to the remote interface to the StudentBean object by Primary Key.
  * @exception throws FinderException and RemoteException.
  *
  */
    
    public Student findByPrimaryKey(String studentId) 
        throws FinderException, RemoteException;
}
