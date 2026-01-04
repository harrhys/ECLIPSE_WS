/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.exception;

/** This application exception indicates that
 *  the credit line of an account is not large
 *  enough to perform an operation.
*/

public class InsufficientCreditException extends Exception {

    public InsufficientCreditException () { }

    public InsufficientCreditException (String msg) {
        super(msg);
    } 
}

