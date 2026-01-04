/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.ejb.stateful.simple.tools;

/**
 * Utility class, verifies the id of customers.
 */
public class IdVerifier {

	/**
	 * Default constructor.
	 */
    public IdVerifier() {
    }

	/**
	 * Validates a given id for a customer.
	 * @param id, id as a string.
	 * @return true if success, false otherwise.
	 */
    public boolean validate(String id) {
       boolean result = true;
       for (int i = 0; i < id.length(); i++) {
         if (Character.isDigit(id.charAt(i)) == false)
            result = false;
       }
       return result;
    }
}
