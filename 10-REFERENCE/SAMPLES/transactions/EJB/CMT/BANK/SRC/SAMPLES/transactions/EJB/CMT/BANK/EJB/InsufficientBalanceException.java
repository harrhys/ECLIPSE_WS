package samples.transactions.ejb.cmt.bank.ejb;

/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */


/**
 * Exception class for Bank application.
 */
public class InsufficientBalanceException extends Exception {

/**
 * Default Constructor.
 */
   public InsufficientBalanceException() { }

/**
 * Constructor with string message.
 * @param msg
 */
   public InsufficientBalanceException(String msg) {
      super(msg);
   }
}
