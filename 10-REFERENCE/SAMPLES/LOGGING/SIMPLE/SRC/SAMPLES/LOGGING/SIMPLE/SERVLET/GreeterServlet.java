/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.logging.simple.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.naming.*;
import javax.servlet.http.*;
import javax.rmi.PortableRemoteObject;
import javax.ejb.*;
import java.util.logging.*;

import samples.logging.simple.ejb.*;

/**
 * This servlet invokes the GreeterEJB and calls the getGreeting method. The returned message is displayed
 * in the output JSP. However, the objective of this servlet is to demonstrate the logging mechanism. This
 * servlet logs different log messages either in server.log or the sample's log file as specified in log.properties
 * file, based on user's input. User will have four options to choose from:
 * <ul>
 * <li> Print log messages with <b>System.err.println</b>
 * <li> Print <b>warning log</b> using <b>java.util.logging.Logger
 * <li> Bypass log-level and log using a lower log-level
 * <li> Log to a file (Can be set in <b>log.properties</b>. Default is helloworld.log)
 * </ul>
 */
public class GreeterServlet extends HttpServlet {

    public static final String LOGTYPE_NULL      = "NULL";
    public static final String LOGTYPE_INFO      = "INFO";
    public static final String LOGTYPE_FINE      = "FINE";
    public static final String LOGTYPE_HANDLER   = "HANDLER";
    public static final String SERVLET_LOGGER    = "samples.logging.simple.servlet";


    /**
     * Logger object for this package, since it has been created with the name "samples.logging.simple.servlet".
     */
    public static Logger logger                  = Logger.getLogger(SERVLET_LOGGER);

    /**
     * The doGet method of the servlet. Handles all http GET request.
     * Required by the servlet specification.
     * @exception throws ServletException and IOException.
     */
    public void doGet (HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException {
        String theMessage = null;

        String log_type = request.getParameter("log_type");
        initLog(log_type);

        try {
            log(log_type, "GreeterServlet is executing ...");
            String JNDIName = "java:comp/env/ejb/logger";
            log(log_type, "Looking up greeter bean home interface using JNDI name: " + JNDIName);
            InitialContext initContext = new javax.naming.InitialContext();
            Object objref = initContext.lookup(JNDIName);

            log(log_type, "Bean found!!!");
            GreeterHome myGreeterHome = (GreeterHome)PortableRemoteObject.narrow(objref, GreeterHome.class);

            log(log_type, "Creating a remote bean object...");
            Greeter myGreeterRemote = myGreeterHome.create();

            log(log_type, "Calling bean's getGreeting method...");
            theMessage = myGreeterRemote.getGreeting();

            log(log_type, "Got message from greeter bean: " + theMessage);
        } catch(Exception e) {
            log(log_type, "Greeter bean home not found - " + "Is bean registered with JNDI?: " + e.toString());
            return;
        }

        log(log_type, "Storing the message in request object");
        request.setAttribute("message", theMessage);

        log(log_type, "Dispatching JSP for output");
        response.setContentType("text/html");
        RequestDispatcher dispatcher;
        dispatcher = getServletContext().getRequestDispatcher
         ("/GreeterView.jsp");
        dispatcher.include(request, response);

        return;
    }

    /**
     * The doPost method of the servlet. Handles all http POST request.
     * Required by the servlet specification.
     * @exception throws ServletException and IOException.
     */
    public void doPost (HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request,response);
    }

    /**
     * Returns the servlet info as a String.
     * @return returns the servlet info as a String.
     */
    public String getServletInfo() {
        return "Call a session bean from a servlet and deliver result via a JSP.";
    }

    /**
     * Initializes the logger. Removes all previous handlers, if any and sets the log level to WARNING.
     * Then based, on the user's selection, either sets the level or adds a new handler.
     */
    private void initLog(String log_type) {

        //Remove all handlers
        Handler[] h = logger.getHandlers();
        for (int i = 0; i < h.length; i++) {
            logger.removeHandler(h[i]);
        }
        //Set it to its parent handler so that it gets printed on the server log
        logger.setUseParentHandlers(true);
        //Set the level to INFO
        logger.setLevel(Level.INFO);

        if(log_type.equals(LOGTYPE_FINE)) {
            setLevel();
        }

        if(log_type.equals(LOGTYPE_HANDLER)) {
            addHandler();
        }
    }

    /**
     * This method logs, various log messages with various log levels, based on user's selection.
     * Selection can be one of the following:
     * <ul>
     * <li> Print log messages with <b>System.err.println</b>
     * <li> Print <b>warning log</b> using <b>java.util.logging.Logger
     * <li> Bypass log-level and log using a lower log-level
     * <li> Log to a file (Can be set in <b>log.properties</b>. Default is helloworld.log)
     * </ul>
     * @param log_type one of the above selection
     * @param message log message
     */
    private void log(String log_type, String message) {
        if(log_type.equals(LOGTYPE_NULL)) {
            System.err.println(message);
        }

        if(log_type.equals(LOGTYPE_INFO)) {
            logger.info(message);
            logger.fine("This message will NOT get logged.");
        }

        if(log_type.equals(LOGTYPE_FINE)) {
            logger.fine(message);
        }

        if(log_type.equals(LOGTYPE_HANDLER)) {
            logger.info(message);
        }
    }

    /**
     * Sets the log level to a level lower than the default level, i.e. 'FINE'. Observe here that the first log
     * statement here will not be logged, since the level is not yet set.
     */
    private void setLevel() {
        logger.fine("This message will NOT get logged.");
        logger.warning("Trying to set the log level to " + Level.FINE);
        try {
            logger.setLevel(Level.FINE);
            logger.fine("Changed the log level to FINE.");
        } catch(SecurityException ex) {
            logger.severe("Security violation. Cannot set the log level.");
            return;
        }
    }

    /**
     * Reads the log.properties file and selects the log file and log level frm there.
     * Adds a file handler to the logger, file being the one specified in log.properties.
     * Sets the level for the logger
     */
    private void addHandler() {
        try {
            String propertiesFile = getServletContext().getResource("/WEB-INF/log.properties").toString();
            Properties logProperties = new Properties();
            java.net.URL url = new java.net.URL(propertiesFile);
            logProperties.load(url.openStream());
 		    String log_file  = logProperties.getProperty("log_file");
 		    String log_level = logProperties.getProperty("log_level");
			Handler fh = new FileHandler(log_file);
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
            logger.setLevel(Level.parse(log_level));
		} catch(Exception ex) {
	        logger.severe("Cannot set handler for logger.");
		}
    }
}
