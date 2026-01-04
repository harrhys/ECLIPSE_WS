/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.rmi_iiop.cpp.ejb;

import java.util.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/** 
 *Cart interface represents a simplified shopping Cart 
 *Where the cppcart items are books only.
 *@author Kumar Jayanti
 *@version 1.0
 */
public interface Cart extends EJBObject {
 
   /** 
    *method to add a Book to Cart
    *@param title, the title of the book to be added to cppcart
    *@exception RemoteException
    */
   public void addBook(String title) throws RemoteException;
   /**  
    *method to removes a Book from Cart
    *@param title, the title of the book to be removed from cppcart
    *@exception BookException, RemoteException
    */
   public void removeBook(String title) throws BookException, RemoteException;
   /** 
    *method to list the contents of the cppcart
    *@return <code>String[]</code>  an array of book names added to the cppcart
    *@exception RemoteException
    */
   public String[] getContents() throws RemoteException;

   /**
    */
   public void setComplexObject(ComplexObject object) throws RemoteException;
   /**
    */
   public ComplexObject getComplexObject() throws RemoteException;

}
