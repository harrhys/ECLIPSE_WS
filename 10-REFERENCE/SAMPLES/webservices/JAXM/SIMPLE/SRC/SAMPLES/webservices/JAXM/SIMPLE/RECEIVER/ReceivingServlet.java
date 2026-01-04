/*
 * $Id: ReceivingServlet.java,v 1.1.2.1 2002/07/22 18:09:14 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/07/22 18:09:14 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package samples.webservices.jaxm.simple.receiver;

import javax.xml.soap.*;
import javax.xml.messaging.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.xml.transform.*;

import javax.naming.*;

import org.apache.commons.logging.*;

/**
 * Sample servlet that receives messages.
 *
 * @author Rajiv Mordani (mode@eng.sun.com)
 * @author Anil Vijendran (anil@sun.com)
 */
public class ReceivingServlet 
    extends JAXMServlet 
    implements ReqRespListener
{
    static MessageFactory fac = null;
    
    static {
        try {
            fac = MessageFactory.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    };
        
    
    static Log 
        logger = LogFactory.getFactory().getInstance("Samples/Simple");

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        // Not much there to do here.
    }
    
    // This is the application code for handling the message.. Once the
    // message is received the application can retrieve the soap part, the
    // attachment part if there are any, or any other information from the
    // message.

    public SOAPMessage onMessage(SOAPMessage message) {
        System.out.println("On message called in receiving servlet");
        try {
            System.out.println("Here's the message: ");
            message.writeTo(System.out);

            SOAPMessage msg = fac.createMessage();
            
            SOAPEnvelope env = msg.getSOAPPart().getEnvelope();
            
            env.getBody()
                .addChildElement(env.createName("Response"))
                .addTextNode("This is a response");

            return msg;
        } catch(Exception e) {
            logger.error("Error in processing or replying to a message", e);
            return null;
        }
    }
}
