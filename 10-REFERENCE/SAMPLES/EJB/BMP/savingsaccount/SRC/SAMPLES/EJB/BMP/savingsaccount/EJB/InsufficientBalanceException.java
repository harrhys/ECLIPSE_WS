/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.bmp.savingsaccount.ejb;

public class InsufficientBalanceException extends Exception {

   public InsufficientBalanceException() { }

   public InsufficientBalanceException(String msg) {
      super(msg);
   }
}
