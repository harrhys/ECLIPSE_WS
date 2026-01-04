/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.ejb.exception;

/** This class application exception indicates that
 *  a Tx entity has not been found.
*/

public class TxNotFoundException extends Exception {

    public TxNotFoundException () { }

    public TxNotFoundException (String msg) {
        super(msg);
    } 
}

