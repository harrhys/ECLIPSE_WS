/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.ejb.stateful.simple.ejb;

import java.util.*;
import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import samples.ejb.stateful.simple.tools.BookException;

/**
 * Remote interface for the <code>CartBean</code>. The remote interface, </code>Cart</code>
 * defines all possible business methods for the bean. These are methods, going to be invoked
 * remotely by clients, once they have a reference to the remote interface.
 *
 * Clients generally take the help of JNDI to lookup the bean's home interface and
 * then use the home interface to obtain references to the bean's remote interface.
 *
 * @see CartHome
 * @see CartBean
 */
public interface Cart extends EJBObject {
 
    /**
     * Adds a book to the cart.
     * @param title title of the book.
     * @exception RemoteException
     */
   public void addBook(String title) throws RemoteException;

    /**
     * Removes a book from the cart.
     * @param title title of the book.
     * @exception RemoteException, BookException.
     */
   public void removeBook(String title) throws BookException, RemoteException;

    /**
     * Gets the content of the cart at that instance of time.
     * @return Vector containing books.
     * @exception RemoteException
     */
   public Vector getContents() throws RemoteException;
}
