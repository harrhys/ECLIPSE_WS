/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.exception;

/** This application exception indicates
 *  that at least one customer is required
 *  for an account.
*/

public class CustomerRequiredException extends Exception {

    public CustomerRequiredException () { }

    public CustomerRequiredException (String msg) {
        super(msg);
    } 
}

