/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.bmp.simple.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface StorageBin extends EJBObject {
    
   public String getWidgetId()
      throws RemoteException;

   public int getQuantity()
      throws RemoteException;
}
