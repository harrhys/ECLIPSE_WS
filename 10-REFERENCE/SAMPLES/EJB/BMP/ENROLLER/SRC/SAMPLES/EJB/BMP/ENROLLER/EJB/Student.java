package samples.ejb.bmp.enroller.ejb;
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
  * Remote interface for the <code>StudentBean</code>. The remote interface, </code>Student</code>
  * defines all possible business methods for the bean. These are methods, going to be invoked
  * remotely by clients, once they have a reference to the remote interface.
  *
  * Clients generally take the help of JNDI to lookup the bean's home interface and
  * then use the home interface to obtain references to the bean's remote interface.
  *
  * @see StudentHome
  * @see StudentBean
  */

public interface Student extends EJBObject {
 
  /**
   * Returns the CourseIds that a student is enrolled in. 
   * @param studentId primary key of the student object
   * @param courseId primary key of the course object
   * @exception RemoteException
   */
   public ArrayList getCourseIds()
      throws RemoteException;

  /**
   * Returns the Name of a student.
   * exception RemoteException
   */

   public String getName()
      throws RemoteException;


  /**
   * Sets the Name of a student.
   * exception RemoteException
   */

   public void setName(String name) 
      throws RemoteException;


}
