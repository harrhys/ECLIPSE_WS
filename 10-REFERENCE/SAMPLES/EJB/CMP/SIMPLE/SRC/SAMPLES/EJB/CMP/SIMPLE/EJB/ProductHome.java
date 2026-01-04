/*
 *
 * Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.cmp.simple.ejb;

import java.util.Collection;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface ProductHome extends EJBHome {

    public Product create(String productId, String description, 
        double balance) throws RemoteException, CreateException;
    
    public Product findByPrimaryKey(String productId) 
        throws FinderException, RemoteException;
    
    public Collection findByDescription(String description)
        throws FinderException, RemoteException;

    public Collection findInRange(double low, double high)
        throws FinderException, RemoteException;
}
