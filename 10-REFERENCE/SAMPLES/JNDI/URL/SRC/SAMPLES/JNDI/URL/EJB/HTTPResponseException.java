/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package samples.jndi.url.ejb;

import java.net.*;
import java.io.*;

public class HTTPResponseException extends Exception {

   public HTTPResponseException() { }

   public HTTPResponseException(String msg) {
      super(msg);
   }

}
