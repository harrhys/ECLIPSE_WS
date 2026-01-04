package samples.ejb.bmp.enroller.ejb;
/*
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
 * Remote interface for the <code>CheckerBean</code>. The remote interface, </code>Course</code>
 * defines all possible business methods for the bean. These are methods, going to be invoked
 * remotely by clients, once they have a reference to the remote interface.
 *
 * Clients generally take the help of JNDI to lookup the bean's home interface and
 * then use the home interface to obtain references to the bean's remote interface.
 *
 * @see CourseHome
 * @see CourseBean
 */

public interface Course extends EJBObject {
 
 /**
  * Returns an arraylist of StudentIds taking the course.
  * @exception RemoteException 
 */
   public ArrayList getStudentIds()
      throws RemoteException;
 /**
  * Returns the name of the course.
  * @exception RemoteException 
  * 
 */

   public String getName()
      throws RemoteException;
 /**
  * Sets the name of the course.
  * @exception RemoteException 
  * 
 */

   public void setName(String name) 
      throws RemoteException;

}
