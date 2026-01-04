/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.stateless.checker.ejb;

import java.util.*;
import javax.ejb.*;
import javax.naming.*;

/**
 * A simple stateless bean. This bean demonstrates the use of environment entries or
 * configurable parameters. It uses two environment entries as defined in the deployment
 * descriptor (ejb-jar.xml) of the bean. They are
 * <ul>
 * <li>Discount Level=1000.0
 * <li>Discount Percent=0.05
 * </ul>
 * Stored in an enterprise bean's deployment descriptor, an environment entry is a name-value
 * pair that allows you to customize the bean's business logic without changing its source code.
 * This bean calculates discounts, based on two environment entries named <code>Discount Percent</code>
 * and <code>Discount Level</code>. The business method <code>applyDiscount</code> looks up these
 * environment entries, makes some calculation and returns the discounted amount.
 *
 * @see Checker
 * @see CheckerHome
 */
public class CheckerBean implements SessionBean {

   String customerName;

    /**
     * Creates a bean.
     * @param person name of the customer.
     * @exception throws CreateException, RemoteException.
     */
	public void ejbCreate(String person) {
	  customerName = person;
	}

	/**
	 * The sole business method of this bean. This method looks up some environment entries,
	 * makes some calculation and returns the discounted amount for a given amount.
	 * Following code snippets demonstrate how you can get the environment entries:
	 * <blockquote><pre>
	 *	...
	 *	Context initial = new InitialContext();
	 *	Context environment = (Context)initial.lookup("java:comp/env");
	 *	Double discountLevel = (Double)environment.lookup("Discount Level");
	 *	Double discountPercent = (Double)environment.lookup("Discount Percent");
	 *	...
	 * </pre></blockquote>
	 *
	 * @param amount amount on which discount is to be applied.
	 */
	public double applyDiscount(double amount) {
		try {
			double discount;

			Context initial = new InitialContext();
			Context environment = (Context)initial.lookup("java:comp/env");

			Double discountLevel = (Double)environment.lookup("Discount Level");
			Double discountPercent = (Double)environment.lookup("Discount Percent");

			if (amount >= discountLevel.doubleValue()) {
				discount = discountPercent.doubleValue();
			} else {
				discount = 0.00;
			}

			return amount * (1.00 - discount);

		} catch (NamingException ex) {
		   throw new EJBException("NamingException: " + ex.getMessage());
		}
	}

    /**
     * Creates a bean. Required by EJB spec.
     */
	public CheckerBean() {}

    /**
     * Removes the bean. Required by EJB spec.
     */
	public void ejbRemove()  {}

    /**
     * Loads the state of the bean from secondary storage. Required by EJB spec.
     */
	public void ejbActivate() {}

    /**
     * Keeps the state of the bean to secondary storage. Required by EJB spec.
     */
	public void ejbPassivate() {}

    /**
     * Sets the session context. Required by EJB spec.
     * @param ctx A SessionContext object.
     */
	public void setSessionContext(SessionContext sc) {}

}
