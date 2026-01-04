/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.ejb.stateful.simple.ejb;


import java.util.*;
import javax.ejb.*;
import samples.ejb.stateful.simple.tools.BookException;
import samples.ejb.stateful.simple.tools.IdVerifier;

/**
 * A simple stateful bean. This bean demonstrates how a bean retains state information
 * across method calls and within the same session. Typical use of a stateful session
 * bean is a shopping cart and this sample tries to simulate the same. The cart keeps the
 * name of the customer, id of the customer and a list of books. Initially the list remains
 * empty.
 * <p>
 * Clients using this bean can add and remove books to the list. The state of the bean can
 * be verified by listing down the books in the bean.
 *
 * @see Cart
 * @see CartHome
 */
public class CartBean implements SessionBean {

	String customerName;
	String customerId;
	Vector contents;

    /**
     * Creates a bean.
     * @param person name of the customer.
     * @exception throws CreateException, RemoteException.
     */
	public void ejbCreate(String person) throws CreateException {
		if (person == null) {
			throw new CreateException("Null person not allowed.");
		} else {
			customerName = person;
		}
		
		customerId = "0";
		contents = new Vector();
	}

    /**
     * Creates a bean.
     * @param person name of the customer.
     * @param id unique id of the customer.
     * @exception throws CreateException, RemoteException.
     */
	public void ejbCreate(String person, String id) throws CreateException {	
		if (person == null) {
			throw new CreateException("Null person not allowed.");
		} else {
			customerName = person;
		}
		
		IdVerifier idChecker = new IdVerifier();
		if (idChecker.validate(id)) {
			customerId = id;
		} else {
			throw new CreateException("Invalid id: " + id);
		}
		
		contents = new Vector();
	}

    /**
     * Adds a book to the cart.
     * @param title title of the book.
     * @exception RemoteException
     */
	public void addBook(String title) {	
		contents.addElement(title);
	}

    /**
     * Removes a book from the cart.
     * @param title title of the book.
     * @exception RemoteException, BookException
     */
	public void removeBook(String title) throws BookException {
		boolean result = contents.removeElement(title);
		if (result == false) {
			throw new BookException(title + " not in cart.");
		}
	}

    /**
     * Gets the content.
     * @return Vector containing books.
     * @exception RemoteException
     */
	public Vector getContents() {
		return contents;
	}

    /**
     * Creates a bean. Required by EJB spec.
     */
	public CartBean() {}

    /**
     * Removes the bean. Required by EJB spec.
     */
	public void ejbRemove() {}

    /**
     * Loads the state of the bean from secondary storage. Required by EJB spec.
     */
	public void ejbActivate() {}

    /**
     * Keeps the state of the bean to secondary storage. Required by EJB spec.
     */
	public void ejbPassivate() {}

    /**
     * Sets the session context. Required by EJB spec.
     * @param ctx A SessionContext object.
     */
	public void setSessionContext(SessionContext sc) {}

} 
