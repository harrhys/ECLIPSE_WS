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
 *  a customer who was expected to be in an account
 *  was not found there.
*/

public class CustomerNotInAccountException extends Exception {

    public CustomerNotInAccountException () { }

    public CustomerNotInAccountException (String msg) {
        super(msg);
    } 
}

