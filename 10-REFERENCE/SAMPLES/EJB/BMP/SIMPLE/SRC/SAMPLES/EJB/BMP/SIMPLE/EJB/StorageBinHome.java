/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.bmp.simple.ejb;

import java.rmi.RemoteException;
import javax.ejb.*;

public interface StorageBinHome extends EJBHome {

    public StorageBin create(String storageBinId, String widgetId, 
        int quantity)
        throws RemoteException, CreateException;
    
    public StorageBin findByPrimaryKey(String storageBinId) 
        throws FinderException, RemoteException;

    public StorageBin findByWidgetId(String widgetId)
        throws FinderException, RemoteException;
}
