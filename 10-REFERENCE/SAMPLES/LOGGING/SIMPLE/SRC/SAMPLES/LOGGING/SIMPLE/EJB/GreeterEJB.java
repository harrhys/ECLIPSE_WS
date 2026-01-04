/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.logging.simple.ejb;

import java.util.*;
import java.io.*;
import java.util.logging.*;

/**
 * A simple stateless bean for the HelloWorld application. This bean implements one
 * business method as declared by the remote interface.
 */
public class GreeterEJB implements javax.ejb.SessionBean {

    public static final String EJB_LOGGER    = "samples.logging.simple.ejb";
    public static Logger logger              = Logger.getLogger(EJB_LOGGER);
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
        logger.info("ejbCreate() on obj " + this);
    }

    /**
     * Removes the bean. Required by EJB spec.
     */
    public void ejbRemove() {
        logger.info("ejbRemove() on obj " + this);
    }

    /**
     * Loads the state of the bean from secondary storage. Required by EJB spec.
     */
    public void ejbActivate() {
        logger.info("ejbActivate() on obj " + this);
    }

    /**
     * Serializes the state of the bean to secondary storage. Required by EJB spec.
     */
    public void ejbPassivate() {
        logger.info("ejbPassivate() on obj " + this);
    }

    /**
     * Required by EJB spec.
     */
    public void Greeter() {
    }


    /**
     * Returns a greeting, based on the time of the day.
     * @return returns a greeting as a string.
     * @exception throws a RemoteException.
     */
    public String getGreeting() throws java.rmi.RemoteException {
        logger.info("inside the getGreeting method of the bean...");
        String message = null;
        Calendar calendar = new GregorianCalendar();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if(currentHour < 12) message = "morning";
        else {
          if( (currentHour >= 12) &&
            (calendar.get(Calendar.HOUR_OF_DAY) < 18)) message = "afternoon";
          else message = "evening";
        }
        logger.info("returnig message to the caller...");
        return message;
    }
}
