/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.comet.ejb;

import javax.resource.cci.*;

public interface Welcome extends javax.ejb.EJBObject
{

	public boolean execute()
		throws  javax.resource.ResourceException,java.rmi.RemoteException;

        public void setName(String name)
                throws  javax.resource.ResourceException,java.rmi.RemoteException;

        public String getMessage()
                throws  javax.resource.ResourceException,java.rmi.RemoteException;

}
