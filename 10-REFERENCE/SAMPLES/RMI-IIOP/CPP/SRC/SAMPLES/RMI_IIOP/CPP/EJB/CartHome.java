/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.rmi_iiop.cpp.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/** 
 *The CartHome interface repesents a shopping cppcart Home interface
 *@author Kumar Jayanti
 *@version 1.0
 */
public interface CartHome extends EJBHome {
 
    /** 
     *Create a Cart given the person name 
     *@param person, the name of the person
     *@return a newly created <code>Cart</code> 
     *@exception RemoteException, CreateException
     */
    Cart create(String person) throws RemoteException, CreateException;
    /** 
     *Create a Cart given the person name and an ID
     *@param person, the name of the person
     *@param id, the ID of the person
     *@return a newly created <code>Cart</code> 
     *@exception RemoteException, CreateException
     */   
    Cart create(String person, String id) throws RemoteException, 
                                                 CreateException; 
}
