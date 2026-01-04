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

public interface Widget extends EJBObject {
    
   public double getPrice()
      throws RemoteException;

   public String getDescription()
      throws RemoteException;
}
