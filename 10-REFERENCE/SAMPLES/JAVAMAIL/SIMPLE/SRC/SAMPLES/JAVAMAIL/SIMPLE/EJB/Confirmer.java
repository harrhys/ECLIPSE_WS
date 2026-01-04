/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.javamail.simple.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface Confirmer extends EJBObject {
 
   public void sendNotice(String recipient) throws RemoteException;
}
