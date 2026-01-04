package samples.ejb.bmp.enroller.ejb;
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
 * Home interface for the CourseBean. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */

public interface CourseHome extends EJBHome {

 /**
  * Gets a reference to the remote interface to the CourseBean bean.
  * @exception throws CreateException and RemoteException.
  *
 */
    public Course create(String courseId, String name)
        throws RemoteException, CreateException;
    
 /**
  * Gets a reference to the remote interface to the CourseBean object by Primary Key.
  * @exception throws FinderException and RemoteException.
  *
 */
    public Course findByPrimaryKey(String courseId) 
        throws FinderException, RemoteException;
}
