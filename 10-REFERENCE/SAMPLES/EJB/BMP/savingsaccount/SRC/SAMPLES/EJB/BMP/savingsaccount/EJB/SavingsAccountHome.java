/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.ejb.bmp.savingsaccount.ejb;

import java.util.Collection;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import javax.ejb.*;

public interface SavingsAccountHome extends EJBHome {

    public SavingsAccount create(String id, String firstName, 
        String lastName, BigDecimal balance)
        throws RemoteException, CreateException;
    
    public SavingsAccount findByPrimaryKey(String id) 
        throws FinderException, RemoteException;
    
    public Collection findByLastName(String lastName)
        throws FinderException, RemoteException;

    public Collection findInRange(BigDecimal low, BigDecimal high)
        throws FinderException, RemoteException;

    public void chargeForLowBalance(BigDecimal minimumBalance, 
       BigDecimal charge)
       throws InsufficientBalanceException, RemoteException;

}
