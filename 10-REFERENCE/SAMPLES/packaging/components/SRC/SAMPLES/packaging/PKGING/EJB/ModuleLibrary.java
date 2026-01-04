/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;

/**
 * Remote interface for the ModuleLibraryEJB. The remote interface defines all possible
 * business methods for the bean. These are the methods going tobe invoked remotely
 * by clients, once they have a reference to the remote interface.
 *
 */
public interface ModuleLibrary extends EJBObject {
    /**
     * Gets the number of days between two calendar dates.
     * @param date1 start date
     * @param date2 end date
     * @return long number of days between start and end dates.
     * @exception RemoteException
     */
	public long getDaysBetween(Calendar date1, Calendar date2) throws RemoteException;

    /**
     * Returns the result of a subtraction.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the subtraction.
     * @exception RemoteException
     */
	public double getDifference(double num1, double num2) throws RemoteException;

    /**
     * Returns of value of the first argument raised to the power of the second argument.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the first argument raised to the power of the second argument.
     * @exception RemoteException
     */
	public double getPower(double num1, double num2) throws RemoteException;

    /**
     * Returns the result of a division.
     * @param num1 first input, numerator
     * @param num2 second input, denominator
     * @return double result of the division.
     * @exception RemoteException
     */
	public double getDivision(double num1, double num2) throws RemoteException;

    /**
     * Returns the result of an addition.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the addition.
     * @exception RemoteException
     */
	public double getSum(double num1, double num2) throws RemoteException;

    /**
     * Returns the result by multiplying two doubles.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the multiplication
     * @exception RemoteException
     */
	public double getProduct(double num1, double num2)	throws RemoteException;
}
