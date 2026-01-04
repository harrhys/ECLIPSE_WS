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
import javax.ejb.*;

import samples.rmi_iiop.cpp.ejb.IdVerifier;

/** 
 *A simplified shopping cppcart bean implemented for
 *demonstration purposes. 
 *@author Kumar Jayanti
 *@version 1.0
 */
public class CartEJB implements SessionBean {

   /**
    * customerName, the name of the customer
    */ 
   String customerName;
   /**
    * customerId, the id of the customer
    */  
   String customerId;
   /**     
    * contents, the contents of the Cart stored as a vector
    */  
   Vector contents;

   /**
    */
    ComplexObject obj=null;

   /**             
    *The ejbCreate container callback method
    *@param person, the name of the person
    *@exception CreateException
    */    
   public void ejbCreate(String person) throws CreateException {

      if (person == null) {
        throw new CreateException("Null person not allowed.");
      }
      else {
         customerName = person;
      }

      customerId = "0";
      contents = new Vector();
   }

   /** 
    *The ejbCreate container callback method
    *@param person, the name of the person
    *@param id, the ID of the person
    *@exception CreateException
    */
   public void ejbCreate(String person, String id) throws CreateException {

      if (person == null) {
        throw new CreateException("Null person not allowed.");
      }
      else {
         customerName = person;
      }

      IdVerifier idChecker = new IdVerifier();
      if (idChecker.validate(id)) {
         customerId = id;
      }
      else {
         throw new CreateException("Invalid id: " + id);
      }

      contents = new Vector();
   }

   /** 
    *The method to add a book to the book shopping cppcart
    *@param title, the title of the book
    */
   public void addBook(String title) {

      contents.addElement(title);
   }

   /** 
    *The method to remove a book from the book shopping cppcart
    *@param title, the title of the book
    *@exception BookException, when the book title is not present in the cppcart
    */
   public void removeBook(String title) throws BookException {

      boolean result = contents.removeElement(title);
      if (result == false) {
         throw new BookException(title + " not in cppcart.");
      }
   }

   /** 
    *The method to list the contents of the cppcart
    *@return an array of book names added to the cppcart.
    */
   public String[] getContents() {
      Object[] arr= contents.toArray();
      String[] ret = new String[arr.length];
      for (int i=0; i< arr.length; i++)
      {
         ret[i] = (String)arr[i];
      }
      return ret;
   }

   /**
    */
   public ComplexObject getComplexObject()
   {
     return obj;
   }

   /**
    */
   public void setComplexObject(ComplexObject complexObject)
   {
     obj = complexObject;
   }

   /** 
    * Sole Constructor (For use by the Container)
    */
   public CartEJB() {}
   /** 
    *The ejbRemove container callback method
    */
   public void ejbRemove() {}
   /** 
    *The ejbActivate container callback method
    */
   public void ejbActivate() {}
   /** 
    *The ejbPassivate container callback method
    */
   public void ejbPassivate() {}
   /** 
    *The setSessionContext container callback method
    * @param sc, the session context passed by container
    */
   public void setSessionContext(SessionContext sc) {}

} 
