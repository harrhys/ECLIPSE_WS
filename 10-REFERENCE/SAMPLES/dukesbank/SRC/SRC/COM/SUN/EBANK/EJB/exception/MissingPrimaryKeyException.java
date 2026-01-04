/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.exception;

/** This application exception 
 *  indicates that a primary key is missing.
*/

public class MissingPrimaryKeyException extends Exception {

    public MissingPrimaryKeyException () { }

    public MissingPrimaryKeyException (String msg) {
        super(msg);
    } 
}

