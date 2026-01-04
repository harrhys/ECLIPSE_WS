/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package samples.webservices.jaxm.saaj.receiver;

import javax.xml.soap.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.xml.transform.*;

import java.util.*;
import java.io.*;
import javax.naming.*;

import org.apache.commons.logging.*;

/**
 * Sample servlet that receives messages.
 *
 * @author Krishna Meduri (krishna.meduri@sun.com)
 */

public class ReceivingServlet extends HttpServlet
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

    MessageFactory msgFactory;

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        try {
            // Initialize it to the default.
            msgFactory = MessageFactory.newInstance();
        } catch (SOAPException ex) {
            throw new ServletException("Unable to create message factory"
                + ex.getMessage());
        }
    }
    
    public void doPost( HttpServletRequest req, 
			HttpServletResponse resp)
	throws ServletException, IOException {

	try {
	    // Get all the headers from the HTTP request.
	    MimeHeaders headers = getHeaders(req);

	    // Get the body of the HTTP request.
	    InputStream is = req.getInputStream();

	    // Now internalize the contents of a HTTP request and
	    // create a SOAPMessage
	    SOAPMessage msg = msgFactory.createMessage(headers, is);
	    
	    SOAPMessage reply = null;

	    // There are no replies in case of an OnewayListener.
	    reply = onMessage(msg);

	    if (reply != null) {
                
		// Need to saveChanges 'cos we're going to use the
		// MimeHeaders to set HTTP response information. These
		// MimeHeaders are generated as part of the save.

		if (reply.saveRequired()) {
		    reply.saveChanges(); 
		}

		resp.setStatus(HttpServletResponse.SC_OK);

		putHeaders(reply.getMimeHeaders(), resp);
                    
		// Write out the message on the response stream.
		OutputStream os = resp.getOutputStream();
		reply.writeTo(os);

		os.flush();
                    
	    } else 
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

	} catch (Exception ex) {
	    throw new ServletException("JAXM POST failed "+ex.getMessage());
	}
    }

    static MimeHeaders getHeaders(HttpServletRequest req) {

	Enumeration enum = req.getHeaderNames();
	MimeHeaders headers = new MimeHeaders();

	while (enum.hasMoreElements()) {
	    String headerName = (String)enum.nextElement();
	    String headerValue = req.getHeader(headerName);

	    StringTokenizer values = new StringTokenizer(headerValue, ",");
	    while (values.hasMoreTokens())
		headers.addHeader(headerName, values.nextToken().trim());
	}

	return headers;
    }

    static void putHeaders(MimeHeaders headers, HttpServletResponse res) {

	Iterator it = headers.getAllHeaders();
	while (it.hasNext()) {
	    MimeHeader header = (MimeHeader)it.next();

	    String[] values = headers.getHeader(header.getName());
	    if (values.length == 1)
		res.setHeader(header.getName(), header.getValue());
	    else {
		StringBuffer concat = new StringBuffer();
		int i = 0;
		while (i < values.length) {
		    if (i != 0)
			concat.append(',');
		    concat.append(values[i++]);
		}

		res.setHeader(header.getName(),
		concat.toString());
            }
        }
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

