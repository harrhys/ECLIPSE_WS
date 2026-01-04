/*
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.rmi.simple.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.math.*;

/**
 * Remote interface for the ConverterEJB. The remote interface 
 * defines all possible business methods for the bean. These are 
 * the methods going tobe invoked remotely by clients, once they 
 * have a reference to the remote interface.
 *
 * Clients generally take the help of JNDI to lookup the bean's 
 * home interface and then use the home interface to obtain 
 * references to the bean's remote interface.
 */

public interface Converter extends EJBObject {

    /**
     * Returns the yen value for given dollar amount.
     * @param dollars Dollar amount tobe converted to yen.
     */
    public BigDecimal dollarToYen(BigDecimal dollars) throws RemoteException;

    /**
     * Returns the euro value for given yen amount.
     * @param yen Yen amount tobe converted to euro.
     */
    public BigDecimal yenToEuro(BigDecimal yen) throws RemoteException;
}
