/*
 * $Id: ReceivingServlet.java,v 1.1.2.1 2002/08/05 21:32:52 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 21:32:52 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package samples.webservices.jaxm.soaprp.receiver;

import javax.xml.messaging.*;
import javax.xml.soap.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.xml.transform.*;

import java.io.*;
import java.util.Enumeration;

import org.apache.commons.logging.*;
import com.sun.xml.messaging.jaxm.soaprp.*;

/**
 * Sample servlet that receives messages.
 *
 * @author Rajiv Mordani (rajiv.mordani@sun.com)
 */
public class ReceivingServlet extends JAXMServlet implements OnewayListener {
    private static Log logger = LogFactory.getLog("Samples/Remote");
    
    private ProviderConnectionFactory pcf;
    private ProviderConnection pc;
    private static final String providerURI = 
    			"http://java.sun.com/xml/jaxm/provider";

    //private MessageFactory messageFactory;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
	try {
	    pcf = ProviderConnectionFactory.newInstance();
	    pc = pcf.createConnection();
	    setMessageFactory(new SOAPRPMessageFactoryImpl());
	}catch (Exception e) {
	    e.printStackTrace();
	    throw new ServletException(
	    	"Couldn't initialize Receiving servlet " + e.getMessage());
	}
    }

    public void onMessage(SOAPMessage message) {
        System.out.println("On message called in receiving servlet");
        try {
            System.out.println("Here's the message: ");
	    message.saveChanges();
            message.writeTo(System.out);
        } catch(Exception e) {
            logger.error("Error in processing or replying to a message", e);
        }
    }
}
