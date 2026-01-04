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

public interface WidgetHome extends EJBHome {

    public Widget create(String widgetId, String description, 
        double price)
        throws RemoteException, CreateException;
    
    public Widget findByPrimaryKey(String widgetId) 
        throws FinderException, RemoteException;
    
}
