/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.exception;

/** This application exception indicates that a 
 *  customer-account relationship already
 *  exists.  In other words, the customer
 *  has already been assigned to the account.
*/

public class CustomerInAccountException extends Exception {

    public CustomerInAccountException () { }

    public CustomerInAccountException (String msg) {
        super(msg);
    } 
}

