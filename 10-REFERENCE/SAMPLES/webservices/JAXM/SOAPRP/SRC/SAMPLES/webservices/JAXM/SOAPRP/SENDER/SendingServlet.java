/*
 * $Id: SendingServlet.java,v 1.1.2.1 2002/08/05 21:32:53 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 21:32:53 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package samples.webservices.jaxm.soaprp.sender;

import java.net.*;
import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.*;

import javax.xml.messaging.*;
import javax.xml.soap.*;

import javax.activation.*;
import com.sun.xml.messaging.jaxm.soaprp.*;


import org.apache.commons.logging.*;

/**
 * Sample servlet that is used for sending the message.
 *
 * @author Rajiv Mordani (rajiv.mordani@sun.com)
 */
public class SendingServlet extends HttpServlet {
    private static Log logger = LogFactory.getLog("Samples/SoapRP");

    private String from ="http://www.wombats.com/soaprp/sender";
    private String to = "http://www.wombats.com/soaprp/sender";
    private String data = "http://127.0.0.1:8080/jaxm-soaprp/index.html";

    private ProviderConnectionFactory pcf;
    private ProviderConnection pc;
    private MessageFactory mf = null;

    private static final String providerURI = 
    			"http://java.sun.com/xml/jaxm/provider";
        
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init( servletConfig );

        try {
	    pcf = ProviderConnectionFactory.newInstance();
	    pc = pcf.createConnection();
        } catch(Exception e) {
            logger.error("Unable to open connection to the provider", e);
        }
        
        InputStream in = servletConfig.getServletContext().
			   getResourceAsStream("/WEB-INF/address.properties");
        
        if (in != null) {
            Properties props = new Properties();

            try {
                props.load(in);
            
		String from = props.getProperty("from");
                String to = props.getProperty("to");
                String data = props.getProperty("data");
		if (from != null)
		    this.from = from;
                if (to != null)
                    this.to = to;
                if (data != null)
                    this.data = data;
            } catch (IOException ex) {
                // Ignore
            }
        }
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException
    
    {
        try {
            // Create a message factory.
	    if (mf == null) {
	        ProviderMetaData metaData = pc.getMetaData();
	        String[] supportedProfiles = metaData.getSupportedProfiles();
	        String profile = null;

	        for(int i=0; i < supportedProfiles.length; i++) {
	  	    if(supportedProfiles[i].equals("soaprp")) {
		        profile = supportedProfiles[i];
		    break;
		    } 
	        }
                mf = pc.createMessageFactory(profile);
	    }

            
            // Create a message from the message factory.
            SOAPRPMessageImpl soaprpMsg = (SOAPRPMessageImpl)mf.createMessage();

	    soaprpMsg.setFrom(new Endpoint(from));
	    soaprpMsg.setTo(new  Endpoint(to));
            
            
            URL url = new URL(data);
            
            AttachmentPart ap = 
	    		soaprpMsg.createAttachmentPart(new DataHandler(url));
            
            ap.setContentType("text/xml");
            
            // Add the attachment part to the message.
            soaprpMsg.addAttachmentPart(ap);
            
            System.err.println("Sending message to : "+soaprpMsg.getTo());
            System.err.println("Sent message is logged in \"sent.msg\"");

            FileOutputStream sentFile = new FileOutputStream("sent.msg");
            soaprpMsg.writeTo(sentFile);
            sentFile.close();
            
	    pc.send(soaprpMsg);
            String retval =
	    	"<html> <H4> Message delivered to provider.</H4></html>";
	    OutputStream os = resp.getOutputStream();
	    os.write(retval.getBytes());
	    os.flush();
	    os.close();

        } catch(Throwable e) {
	    e.printStackTrace();
            logger.error("Error in constructing or sending message "
                         +e.getMessage());
        }
    }
}
