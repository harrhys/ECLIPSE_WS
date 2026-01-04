/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.comet.ejb;

import javax.resource.cci.ConnectionSpec;

public interface WelcomeHome extends javax.ejb.EJBHome
{
	public Welcome create()
		throws	javax.ejb.CreateException,
				java.rmi.RemoteException;

}
