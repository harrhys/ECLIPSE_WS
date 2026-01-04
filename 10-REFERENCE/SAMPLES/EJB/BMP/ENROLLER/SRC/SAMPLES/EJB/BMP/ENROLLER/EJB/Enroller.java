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
  * Remote interface for the <code>EnrollerBean</code>. The remote interface, </code>Enroller</code>
  * defines all possible business methods for the bean. These are methods, going to be invoked
  * remotely by clients, once they have a reference to the remote interface.
  *
  * Clients generally take the help of JNDI to lookup the bean's home interface and
  * then use the home interface to obtain references to the bean's remote interface.
  *
  * @see EnrollerHome
  * @see EnrollerBean
  */

public interface Enroller extends EJBObject {
 
 /**
  * Enrolls a Student in a course
  * @param studentId primary key of the student object
  * @param courseId primary key of the course object
  * @exception RemoteException
  */
   public void enroll(String studentId, String courseId)
      throws RemoteException;
 /**
  * Un-Enrolls a Student in a course
  * @param studentId primary key of the student object
  * @param courseId primary key of the course object
  * @exception RemoteException
  */

   public void unEnroll(String studentId, String courseId)
      throws RemoteException;
 /**
  * Deletes a Student 
  * @param studentId primary key of the student object
  * @exception RemoteException
  */

   public void deleteStudent(String studentId)
      throws RemoteException;

 /**
  * Deletes a Course 
  * @param courseId primary key of the course object
  * @exception RemoteException
  */
   public void deleteCourse(String courseId)
      throws RemoteException;
 /**
  * Returns an Arraylist of StudentsIds enrolled in a course
  * @param courseId primary key of the course object
  * @exception RemoteException
  */

   public ArrayList getStudentIds(String courseId)
      throws RemoteException;
 /**
  * Return an ArrayList of CourseIds that student is enroller in
  * @param studentId primary key of the student object
  * @exception RemoteException
  */

   public ArrayList getCourseIds(String studentId)
      throws RemoteException;
}
