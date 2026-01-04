/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.exception;

/** This class application exception indicates that a
 *  Customer entity has not been found.
*/

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException () { }

    public CustomerNotFoundException (String msg) {
        super(msg);
    } 
}

