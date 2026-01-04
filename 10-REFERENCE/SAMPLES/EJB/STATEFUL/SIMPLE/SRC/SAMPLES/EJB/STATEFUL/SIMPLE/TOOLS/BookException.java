/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.ejb.stateful.simple.tools;

/**
 * Exception class for the <code>cart (stateful-simple)</code> sample.
 * This exception is thrown by the removeBook method of the bean.
 */
public class BookException extends Exception {

	/**
	 * Default constructor.
	 */
    public BookException() {
    }

	/**
	 * Constructor with a <code>String<code> as a parameter.
	 * @param msg message, describing the exception.
	 */
    public BookException(String msg) {
        super(msg);
    }
}
