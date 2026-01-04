/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.rmi_iiop.cpp.ejb;
/** 
 *Class to validate the UserName and Id of a person
 *Used internally by the CartBean Implementation
 *@author Kumar Jayanti
 *@version 1.0
 */
public class IdVerifier {

   /**                
    *Sole Constructor
    */
    public IdVerifier() {
    }

    /**
     *Validate the argument ID
     *@param id, the ID of the person 
     *@return <code>true</code> if the ID is valid
     */
    public boolean validate(String id) {

       boolean result = true;
       for (int i = 0; i < id.length(); i++) {
         if (Character.isDigit(id.charAt(i)) == false)
            result = false;
       }
       return result;
    }
}
