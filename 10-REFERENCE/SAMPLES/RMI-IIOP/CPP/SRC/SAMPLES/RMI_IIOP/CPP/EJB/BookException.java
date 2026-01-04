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
 *An exception thrown mainly to indicate an Invalid Book name
 *@author Kumar Jayanti
 *@version 1.0
 */
public class BookException extends Exception {

   /** 
    *Constructs a BookException, default constructor
    */
    public BookException() {
    }

   /** 
    *Constructs a BookException, default constructor
    *@param msg, the exception message
    */
    public BookException(String msg) {
        super(msg);
    }
}
