/*
 *
 * Copyright (c) 2002 Sun Microsystems, Inc. All rights reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.cmp.simple.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Product extends EJBObject {
 
   public void setPrice(double price) throws RemoteException;

   public double getPrice() throws RemoteException;  

   public String getDescription() throws RemoteException;
}
