/*
 * $Id: MessageUtil.java,v 1.1.2.1 2002/08/05 20:34:54 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:54 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package provideradmin.util;

import javax.servlet.http.*;
import javax.servlet.ServletContext;

import javax.xml.messaging.*;
import javax.xml.soap.*;
import com.sun.xml.messaging.jaxm.provider.Config;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Enumeration;

import org.apache.commons.logging.*;
import com.sun.xml.messaging.util.StringManager;

/**
 * A utility to form administrative messages.
 *
 * @author Manveen Kaur (manveen.kaur@sun.com)
 */

public class MessageUtil {
    
    private static final String NS_PREFIX = "provideradmin";
    private static final String NS_URI =
                    "http://java.sun.com/jaxm/provider/admin";
    static Log logger = 
                    LogFactory.getFactory().getInstance("Tools/ProviderAdmin");
    private static StringManager sm = StringManager.getManager(
                    "provideradmin.util");
    
    private SOAPElement adminCmd;
    Config config;    
    String to = null;
 
    /*
     * Initialize the command with the common elements.
     */
    private SOAPMessage newCommandMessage(HttpServletRequest req) {
        
        SOAPMessage msg = null;
        
        try {
            // Create a message factory.
            MessageFactory mf = MessageFactory.newInstance();
            
            // Create a message from the message factory.
            msg = mf.createMessage();
            
            SOAPPart sp = msg.getSOAPPart();
            
            // Retrieve the envelope from the soap part to start building
            // the soap message.
            SOAPEnvelope envelope = sp.getEnvelope();
            
            // Create a soap header from the envelope.
            SOAPHeader hdr = envelope.getHeader();
            
            // Create a soap body from the envelope.
            SOAPBody bdy = envelope.getBody();
            
            // Add a soap body element to the soap body
            adminCmd
            = bdy.addBodyElement(envelope.createName("AdminCommand", NS_PREFIX, NS_URI));
            
            // adding commmon values
            
            String type = req.getParameter("type");
            String action = req.getParameter("action");
            String profile = req.getParameter("profile");
            String protocol = req.getParameter("protocol");
            
            addElementText(msg, adminCmd, "Type", type);
            addElementText(msg, adminCmd, "Action", action);
            addElementText(msg, adminCmd, "Profile", profile);
            addElementText(msg, adminCmd, "Protocol", protocol);
        } catch (Exception e) {
            logger.error(sm.getString("error.adminmsg.create") + e.getMessage() );
        }        
        return msg;
    }
    
    public SOAPMessage topLevelChangeCommand(HttpServletRequest req){
        
        SOAPMessage msg = newCommandMessage(req);
        try {
            String maxretries = req.getParameter("maxretries");
            String interval = req.getParameter("interval");
            
            String directory = req.getParameter("directory");
            String records = req.getParameter("records");
            
            addElementText(msg, adminCmd, "MaxRetries", maxretries);
            addElementText(msg, adminCmd, "Interval", interval);
            addElementText(msg, adminCmd, "Directory", directory);
            addElementText(msg, adminCmd, "Records", records);
            
        } catch (Exception e) {
            logger.error(sm.getString("error.adminmsg.create") + e.getMessage() );
        }
        return msg;
    }

    public SOAPMessage createMappingCommand(HttpServletRequest req) {
        
        SOAPMessage msg = newCommandMessage(req);
        try {
            String uri = req.getParameter("uri");
            String url = req.getParameter("url");
            addElementText(msg, adminCmd, "URI", uri);
            addElementText(msg, adminCmd, "URL", url);
            
        } catch (Exception e) {
            logger.error(sm.getString("error.adminmsg.create") + e.getMessage() );
        }
        return msg;
        
    }
    
    /* deletes multiple uri's from all the checked boxes check boxes*/
    public SOAPMessage deleteMappingCommand(HttpServletRequest req) {
        
        SOAPMessage msg = newCommandMessage(req);
        try {
            // list of uris that must be deleted.
            String[] uris =  req.getParameterValues("checkbox");
            
            if (uris != null) {
                for (int i=0; i< uris.length; i++)
                    addElementText(msg, adminCmd, "URI", uris[i]);
            }
            
        } catch (Exception e) {
            logger.error( sm.getString("error.adminmsg.create") + e.getMessage() );
        }
        return msg;
        
    }
    
    public SOAPMessage createPersistenceCommand(HttpServletRequest req) {
        SOAPMessage msg = newCommandMessage(req);
        try {
            String directory = req.getParameter("directory");
            String records = req.getParameter("records");
            addElementText(msg, adminCmd, "Directory", directory);
            addElementText(msg, adminCmd, "Records", records);
        } catch (Exception e) {
            logger.error( sm.getString("error.adminmsg.create") + e.getMessage() );
        }
        return msg;
    }
    
    public SOAPMessage createErrorHandlingCommand(HttpServletRequest req) {
        
        SOAPMessage msg = newCommandMessage(req);
        try {
            String maxretries = req.getParameter("maxretries");
            String interval = req.getParameter("interval");
            addElementText(msg, adminCmd, "MaxRetries", maxretries);
            addElementText(msg, adminCmd, "Interval", interval);
        } catch (Exception e) {
            logger.error(sm.getString("error.adminmsg.create") + e.getMessage() );
        }
        return msg;
    }
    
    private void addElementText(SOAPMessage msg, SOAPElement element,
    String localname, String value) throws SOAPException {
        
        SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
        
        element.addChildElement(envelope.createName(localname,
        NS_PREFIX,
        NS_URI)).addTextNode(value);
    }
    
    // Connection to send messages.
    private SOAPConnection con;
    
    public SOAPMessage sendMessage(HttpServletRequest request, SOAPMessage msg) {
        
        try {
            
            SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
            con = scf.createConnection();
            
        } catch(Exception e) {            
            logger.error(sm.getString("error.open.connection"), e); 
        }
        
        SOAPMessage reply = null;
        
        try {
            
            StringBuffer urlSB=new StringBuffer();
            urlSB.append(request.getScheme()).append("://").append(request.getServerName());
            urlSB.append( ":" ).append( request.getServerPort() );
            String reqBase=urlSB.toString();
            
            // Create an endpoint for the recipient of the message.
            if(to==null) {
                to=reqBase + "/jaxm-provider/digest";
            }
            
            URLEndpoint urlEndpoint
            = new URLEndpoint(to);
            
            System.err.println( sm.getString("msg.sending") + urlEndpoint.getURL());
            msg.writeTo(System.out);
            
           // Send the message to the provider using the connection.
            reply = con.call(msg, urlEndpoint);
            System.out.println("\n" + sm.getString("reply.recieved") +"\n");
            
        } catch(Throwable e) {
            e.printStackTrace();
            logger.error(sm.getString("error.contruct.send") + e.getMessage());
        }
        
        return reply;
    }
    
    
    // the initial handshake message that initializes Config for this webapp.
    private SOAPMessage InitSyncMessage() {
        
        SOAPMessage msg = null;
        
        try {
            // Create a message factory.
            MessageFactory mf = MessageFactory.newInstance();
            
            // Create a message from the message factory.
            msg = mf.createMessage();
            
            SOAPPart sp = msg.getSOAPPart();
            
            // Retrieve the envelope from the soap part to start building
            // the soap message.
            SOAPEnvelope envelope = sp.getEnvelope();
            
            // Create a soap header from the envelope.
            SOAPHeader hdr = envelope.getHeader();
            
            // Create a soap body from the envelope.
            SOAPBody bdy = envelope.getBody();
            
            // Add a soap body element to the soap body
            adminCmd =
                 bdy.addBodyElement(envelope.createName("AdminCommand", NS_PREFIX, NS_URI));
            
            addElementText(msg, adminCmd, "Type", "init");
            
        } catch (Exception e) {            
            logger.error(sm.getString("error.initsync.extract"), e);    
        }
        
        return msg;
        
    }
    
    public Config initializeConfig(HttpServletRequest request) {
        
        System.out.println(sm.getString("msg.init.config"));
        SOAPMessage reply =  sendMessage(request, InitSyncMessage());
        String providerxml = extractProviderXml(reply);
        
        this.config = new Config(new ByteArrayInputStream(providerxml.getBytes()));
        
        return config;
        
    }
    
    // extract provider.xml from the reply.
    private String extractProviderXml(SOAPMessage msg) {
        
        String provider = null;
        
        try {
            
            // Extracting the content from the message attachments.
            Iterator iterator = msg.getAttachments();
            
            if (iterator.hasNext())
                provider  = (String)
                ((AttachmentPart) iterator.next()).getContent();
            
            
        } catch (Exception e) {            
            logger.error(sm.getString("error.xml.extract"), e);            
        }
        return provider;
    }
}
