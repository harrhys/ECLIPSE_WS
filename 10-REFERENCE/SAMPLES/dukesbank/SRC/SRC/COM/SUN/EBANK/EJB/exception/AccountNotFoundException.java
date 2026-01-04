/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.exception;

/** This application exception indicates that an Account 
 *  entity has not been found.
*/

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException () { }

    public AccountNotFoundException (String msg) {
        super(msg);
    } 
}

