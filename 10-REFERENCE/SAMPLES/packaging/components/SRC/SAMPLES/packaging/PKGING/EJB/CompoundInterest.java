/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;


/**
 * Remote interface for the CompoundInterestEJB. The remote interface defines all possible
 * business methods for the bean. These are the methods going tobe invoked remotely
 * by clients, once they have a reference to the remote interface.
 *
 */
public interface CompoundInterest extends EJBObject {
    /**
     * Returns the simple interest for a given set of inputs.
     * @param principal principal amount in double
     * @param time time in months
     * @param rate rate in percentage
     * @return returns the simple interest
     * @exception throws a RemoteException.
     */
	public double getSimpleInterest(double principal, double time, double rate) throws RemoteException;

    /**
     * Returns the compound interest for a given set of inputs.
     * @param principal principal amount in double
     * @param time time in months
     * @param rate rate in percentage
     * @return returns the compound interest
     * @exception throws a RemoteException.
     */
	public double getCompoundInterest(double principal, double time, double rate) throws RemoteException;

}
