/*
 * $Id: ReceivingServlet.java,v 1.1.2.2 2002/08/13 22:18:09 satyan Exp $
 * $Revision: 1.1.2.2 $
 * $Date: 2002/08/13 22:18:09 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package samples.webservices.jaxm.translator;

import javax.xml.messaging.*;
import javax.xml.soap.*;
import javax.xml.soap.Node;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.naming.*;

import org.apache.commons.logging.*;

/**
 * Sample servlet that receives messages containing text to be
 * translated,
 * does the translation, and sends back a message with translations as
 * attachments -or- in SOAPBody of the reply message.
 *
 * @author Manveen Kaur (manveen.kaur@sun.com)
 * @author Rajiv Mordani(rajiv.mordani@sun.com)
 *
 */

public class ReceivingServlet
extends JAXMServlet
implements ReqRespListener {
    
    private static String NS_PREFIX = "jaxm";
    private static String
    NS_URI = "http://java.sun.com/jaxm/samples/translation";
    private static Log logger = LogFactory.getLog("Samples/Translator");
    
    private String french = "";    
    private String german = "";
    private String italian = "";
    
    public void init(ServletConfig servletConfig) throws
    ServletException  {
        super.init(servletConfig);
    }
    
    public SOAPMessage onMessage(SOAPMessage message) {
        SOAPMessage msg = null;
        
        try {
            
            System.out.println("\n************** REQUEST ***************\n");
            
            message.writeTo(System.out);
            FileOutputStream os = new FileOutputStream("request.msg");
            message.writeTo(os);
            os.close();
            
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.getHeader();
            SOAPBody body = envelope.getBody();
            
            // Extracting Proxy information from SOAPHeader.
            String host = extract(envelope, header, "ProxyHost");
            
            String port = extract(envelope, header, "ProxyPort");            
            
            String translationAs = extract(envelope, header, "TranslationAs");
            
            // Extracting text to be translated from SOAPBody.
            String text = extract(envelope, body, "Text");
            
            TranslationService ts = null;
            if ((host==null) || (port == null)) {
                ts = new TranslationService();
            }
            else if ( (host.equals(""))  ||
            (port.equals(""))) {
                ts = new TranslationService();
            } else  {
                ts = new TranslationService(host,port);
            }
 
            
            // Translate using the Translation Web-Service.
            german = ts.translate(text, ts.ENGLISH_TO_GERMAN);
            french = ts.translate(text, ts.ENGLISH_TO_FRENCH);
            italian = ts.translate(text, ts.ENGLISH_TO_ITALIAN);
            
            // Create reply message
            msg = msgFactory.createMessage();
            
            if (translationAs.equals("body")) {
                addInSOAPBody(msg);
            } else {
                addAsAttachments(msg);
            }
            
            if (msg.saveRequired())
                msg.saveChanges();
            
        } catch(Exception e) {
            logger.error("Error in processing or replying to a message", e);
        }
        
        return msg;
    }
    
    private void addInSOAPBody(SOAPMessage msg) {
        try {
            
            SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
            SOAPBody body = envelope.getBody();
            
            // Adding the translated text to SOAPBody.
            body.addBodyElement(envelope.createName("FrenchText",
            NS_PREFIX, NS_URI)).addTextNode(french);
            
            body.addBodyElement(envelope.createName("GermanText",
            NS_PREFIX, NS_URI)).addTextNode(german);
            
            body.addBodyElement(envelope.createName( "ItalianText",
            NS_PREFIX, NS_URI)).addTextNode(italian);
            
        } catch(Exception e) {
            logger.error("Error in adding translation to the body", e);
        }
    }
    
    private void addAsAttachments(SOAPMessage msg) {
        // Adding the translations as attachments.
        try {
            
            SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
            
            AttachmentPart ap_french =
            msg.createAttachmentPart(french,
            "text/plain; charset=ISO-8859-1");
            msg.addAttachmentPart(ap_french);
            
            AttachmentPart ap_german =
            msg.createAttachmentPart(german,
            "text/plain; charset=ISO-8859-1");
            msg.addAttachmentPart(ap_german);
            
            AttachmentPart ap_italian =
            msg.createAttachmentPart(italian,
            "text/plain; charset=ISO-8859-1");
            msg.addAttachmentPart(ap_italian);
            
        } catch(Exception e) {
            logger.error("Error in adding translations as attachments",
            e);
        }
    }
    
    // extract the value of the first child element under element
    // with this localname
    private String extract(SOAPEnvelope envelope, SOAPElement element, String localname)
    throws SOAPException {
        
        Iterator it = element.getChildElements(
        envelope.createName(localname, NS_PREFIX, NS_URI));
        
        if( it.hasNext()) {
            SOAPElement e = (SOAPElement) it.next();
            return e.getValue();
        }
        logger.error("Could not extract " + localname + " from message");
        return null;
    }
    
}
