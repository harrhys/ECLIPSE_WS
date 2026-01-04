/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.webapps.bookstore.bookstore2.ejb.database;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;

public interface BookDBEJBHome extends EJBHome {
   BookDBEJB create() throws RemoteException, CreateException;
}
