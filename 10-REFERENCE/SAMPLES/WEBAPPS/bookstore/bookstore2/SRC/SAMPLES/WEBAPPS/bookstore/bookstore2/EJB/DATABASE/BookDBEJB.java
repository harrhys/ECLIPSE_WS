/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.webapps.bookstore.bookstore2.ejb.database;

import java.util.*;
import javax.ejb.*;
import java.rmi.RemoteException;
import samples.webapps.bookstore.bookstore2.ejb.exception.*;

public interface BookDBEJB extends EJBObject {
   public BookDetails getBookDetails(String bookId) throws RemoteException, BookNotFoundException;
   public int getNumberOfBooks() throws RemoteException, BooksNotFoundException;
   public Collection getBooks() throws RemoteException, BooksNotFoundException;
}
