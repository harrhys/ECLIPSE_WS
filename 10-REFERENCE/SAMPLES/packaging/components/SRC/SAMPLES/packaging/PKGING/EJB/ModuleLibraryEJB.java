/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.packaging.pkging.ejb;

import javax.ejb.*;
import java.util.*;
import javax.naming.*;

import samples.packaging.pkging.lib.*;

/**
 * A simple stateless bean, ModuleLibrary, provides Calculator and DateLibrary functions
 * to other applications and modules. The business methods are defined in it's remote interface.
 */
public class ModuleLibraryEJB implements javax.ejb.SessionBean {
    private  javax.ejb.SessionContext m_ctx = null;

    /**
     * Sets the session context. Required by EJB spec.
     * @param ctx A SessionContext object.
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) {
        m_ctx = ctx;
    }

    /**
     * Creates a bean. Required by EJB spec.
     * @exception throws CreateException.
     */
    public void ejbCreate() throws java.rmi.RemoteException, javax.ejb.CreateException {
    }

    /**
     * Removes the bean. Required by EJB spec.
     */
    public void ejbRemove() {
    }

    /**
     * Loads the state of the bean from secondary storage. Required by EJB spec.
     */
    public void ejbActivate() {
    }

    /**
     * Serializes the state of the bean to secondary storage. Required by EJB spec.
     */
    public void ejbPassivate() {
    }

    /**
     * Required by EJB spec.
     */
	public ModuleLibraryEJB() {
	}

    /**
     * Gets the number of days between two calendar dates.
     * @param date1 start date
     * @param date2 end date
     * @return long number of days between start and end dates.
     * @exception RemoteException
     */
	public long getDaysBetween(Calendar date1, Calendar date2) throws java.rmi.RemoteException {
	    DateLibrary dateLibrary = new DateLibrary();
	    return dateLibrary.getDaysBetween(date1,date2);
	}

    /**
     * Returns the result of a subtraction.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the subtraction.
     * @exception RemoteException
     */
	public double getDifference(double num1, double num2) throws java.rmi.RemoteException {
	    Calculator calculator = new Calculator();
	    return calculator.getDifference(num1,num2);
	}

    /**
     * Returns of value of the first argument raised to the power of the second argument.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the first argument raised to the power of the second argument.
     * @exception RemoteException
     */
	public double getPower(double num1, double num2) throws java.rmi.RemoteException {
	    Calculator calculator = new Calculator();
	    return calculator.getPower(num1,num2);
	}

    /**
     * Returns the result of a division.
     * @param num1 first input, numerator
     * @param num2 second input, denominator
     * @return double result of the division.
     * @exception RemoteException
     */
	public double getDivision(double num1, double num2) throws java.rmi.RemoteException	{
	    Calculator calculator = new Calculator();
	    double dividend = 0;
	    try {
	        dividend = calculator.getDivision(num1,num2);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return dividend;
	}

    /**
     * Returns the result of an addition.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the addition.
     * @exception RemoteException
     */
	public double getSum(double num1, double num2) throws java.rmi.RemoteException {
	    Calculator calculator = new Calculator();
	    return calculator.getSum(num1,num2);
	}

    /**
     * Returns the result by multiplying two doubles.
     * @param num1 first input
     * @param num2 second input
     * @return double result of the multiplication
     * @exception RemoteException
     */
	public double getProduct(double num1, double num2) throws java.rmi.RemoteException {
	    Calculator calculator = new Calculator();
	    return calculator.getProduct(num1,num2);
	}
}
