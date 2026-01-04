package samples.transactions.ejb.cmt.bank.ejb;
/**
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.rmi.RemoteException;
import javax.ejb.*;

/**
 * Home interface for the BankBean. Clients generally use home interface
 * to obtain references to the bean's remote interface.
 *
 */

public interface BankHome extends EJBHome {

/**
 * Gets a reference to the remote interface to the Bank bean.
 * @exception CreateException 
 * @exception RemoteException
 *
 */

    public Bank create(String id)
        throws RemoteException, CreateException;
}
