/*
 * Copyright 1999-2002 Sun Microsystems, Inc. ALL RIGHTS RESERVED
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package st;

import java.io.*;

import shared.MessageConstants;

/**
 * Exception class that maps codes to messages.
 *
 * Permits many types of errors to be reported without creating extra 
 * exception classes.
 */
public class ApplicationException extends Exception {

    /** The error code to look up */
    private int errCode;

    /** The error message corresponding to an error code */
    private String errMsg;
    
    /**
     * The constructor method for the exception given an error code.
     *
     * @param code the error code
     */
    public ApplicationException(int code) {
        if (code < MessageConstants.NUM_MSG) {
	    errCode = code;
	    errMsg = SmartTicket.getMsg(errCode);
	}
    }

    public ApplicationException() {
	errCode = 0;
	errMsg = null;
    }

    /**
     * Get the error message.
     *
     * @return the error message
     */
    public String getMessage() {
        return errMsg;
    }
    
    /**
     * Get the error code.
     *
     * @return the error code
     */
    public int getCode() {
        return errCode;
    }
}
