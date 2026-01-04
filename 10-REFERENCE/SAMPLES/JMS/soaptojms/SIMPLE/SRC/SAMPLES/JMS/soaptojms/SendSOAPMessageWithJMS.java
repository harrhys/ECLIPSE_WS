/*
 * @(#)SendSOAPMessageWithJMS.java	1.6 02/05/02
 *
 * Copyright (c) 2002 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package samples.jms.soaptojms;

import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.Name;

import java.net.URL;
import javax.activation.DataHandler;

import com.sun.messaging.xml.MessageTransformer;
import com.sun.messaging.TopicConnectionFactory;

import javax.jms.TopicConnection;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TopicSession;
import javax.jms.Topic;
import javax.jms.TopicPublisher;

/**
 * This example shows how to use the MessageTransformer utility to send SOAP
 * messages with JMS.
 * <p>
 * SOAP messages are constructed with javax.xml.soap API.  The messages
 * are converted with MessageTransformer utility to convert SOAP to JMS
 * message types.  The JMS messages are then published to the JMS topics.
 */
public class SendSOAPMessageWithJMS {

    TopicConnectionFactory tcf = null;
    TopicConnection tc = null;
    TopicSession session = null;
    Topic topic = null;

    TopicPublisher publisher = null;

    /**
     * default constructor.
     *
     * @param topicName a String that contains the name of a JMS Topic 
     *
     */
    public SendSOAPMessageWithJMS(String topicName) {
        init(topicName);
    }

    /**
     * Initialize JMS Connection/Session/Topic and Publisher.
     *
     * @param topicName a String that contains the name of a JMS Topic 
     *
     */
    public void init(String topicName) {
        try {
            tcf = new TopicConnectionFactory();
            tc = tcf.createTopicConnection();
            session = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            topic = session.createTopic(topicName);
            publisher = session.createPublisher(topic);
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

    /**
     * Send SOAP message with JMS API.
     */
    public void send () throws Exception {

        /**
         * Construct a default SOAP message factory.
         */
        MessageFactory mf = MessageFactory.newInstance();
        /**
         * Create a SOAP message object.
         */
        System.out.println ("Create a SOAP message"); 
        SOAPMessage soapMessage = mf.createMessage();
        /**
         * Get SOAP part.
         */
        SOAPPart soapPart = soapMessage.getSOAPPart();
        /**
         * Get SOAP envelope.
         */
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

        /**
         * Get SOAP body.
         */
        SOAPBody soapBody = soapEnvelope.getBody();
        /**
         * Create a name object. with name space http://www.sun.com/imq.
         */
        Name name = soapEnvelope.createName("HelloWorld", "hw", "http://www.sun.com/imq");
        /**
         * Add child element with the above name.
         */
        SOAPElement element = soapBody.addChildElement(name);

        /**
         * Add another child element.
         */
        element.addTextNode( "Welcome to SunOne Web Services." );

        /**
         * Create an atachment with activation API.
         */
        System.out.println ("Attach http://java.sun.com/webservices web site to the message"); 

        URL url = new URL ("http://java.sun.com/webservices/");
        DataHandler dh = new DataHandler (url);
        AttachmentPart ap = soapMessage.createAttachmentPart(dh);
        /**
         * set content type/ID.
         */
        ap.setContentType("text/html");
        ap.setContentId("cid-001");

        /**
         *  add the attachment to the SOAP message.
         */
        soapMessage.addAttachmentPart(ap);
        soapMessage.saveChanges();

        /**
         * Convert SOAP to JMS message.
         */
        System.out.println ("Convert the message to JMS message"); 
        Message m = MessageTransformer.SOAPMessageIntoJMSMessage( soapMessage, session );

        /**
         * publish JMS message.
         */
        System.out.println ("Publish the message."); 
        publisher.publish( m );
    }

    /**
     * Close JMS connection.
     *
     * @exception JMSException 
     */
    public void close() throws JMSException {
        tc.close();
    }

    /**
     * The main program to send SOAP messages with JMS.
     */
    public static void main (String[] args) {

        String topicName = "TestTopic";

        if (args.length > 0) {
            topicName = args[0];
        }
        try {
            SendSOAPMessageWithJMS ssm = new SendSOAPMessageWithJMS(topicName);
            ssm.send();
            ssm.close();
            System.out.println ("Finished"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
