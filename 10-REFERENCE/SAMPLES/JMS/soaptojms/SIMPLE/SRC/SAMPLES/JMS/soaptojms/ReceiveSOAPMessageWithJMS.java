/*
 * @(#)ReceiveSOAPMessageWithJMS.java	1.4 02/05/02
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

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.AttachmentPart;

import com.sun.messaging.xml.MessageTransformer;
import com.sun.messaging.TopicConnectionFactory;

import javax.jms.MessageListener;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.JMSException;
import javax.jms.TopicSubscriber;

import java.util.Iterator;

/**
 * This sample program shows a JMS message listener can use the MessageTransformer
 * utility to convert JMS messages back to SOAP messages.
 */
public class ReceiveSOAPMessageWithJMS implements MessageListener {

    TopicConnectionFactory tcf = null;
    TopicConnection tc = null;
    TopicSession session = null;
    Topic topic = null;
    TopicSubscriber subscriber = null;

    MessageFactory messageFactory = null;

    /**
     * Default constructor.
     *
     * @param topicName  a String that contains the name of a JMS Topic  
     *
     */
    public ReceiveSOAPMessageWithJMS(String topicName) {
        init(topicName);
    }

    /**
     * JMS Connection/Session/Destination/MessageListener set ups.
     *
     * @param topicName a String that contains the name of a JMS Topic  
     */
    public void init(String topicName) {
        try {

            /**
             * construct a default SOAP message factory.
             */
            messageFactory = MessageFactory.newInstance();

            /**
             * JMS set up.
             */
            tcf = new com.sun.messaging.TopicConnectionFactory();
            tc = tcf.createTopicConnection();
            session = tc.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            topic = session.createTopic(topicName);
            subscriber = session.createSubscriber(topic);
            subscriber.setMessageListener( this );
            tc.start();

            System.out.println ("Ready to receive SOAP messages ...");
        } catch (Exception jmse) {
            jmse.printStackTrace();
        }
    }

    /**
     * JMS Messages are delivered to this method. The body of the message
     * contains SOAP streams.
     *
     * 1.  The message conversion utility converts JMS message to SOAP
     * message type.
     * 2.  Get the attachment parts and print content information to the
     * standard output stream.
     *
     * @param message a delivered message
     *
     */
    public void onMessage (Message message) {

        try {

            /**
             * convert JMS to SOAP message.
             */
            System.out.println("Message received! Converting the JMS message to SOAP message");

            SOAPMessage soapMessage =
            MessageTransformer.SOAPMessageFromJMSMessage( message, messageFactory );

            /**
             * Print attachment counts.
             */
            System.out.println("Attachment counts: " + soapMessage.countAttachments());

            /**
             * Get attachment parts of the SOAP message.
             */
            Iterator iterator = soapMessage.getAttachments();
            while ( iterator.hasNext() ) {
                /**
                 * Get next attachment.
                 */
                AttachmentPart ap = (AttachmentPart) iterator.next();
                /**
                 * Get content type.
                 */
                String contentType = ap.getContentType();
                System.out.println("Content type: " + contentType);
                /**
                 * Get content Id.
                 */
                String contentId = ap.getContentId();
                System.out.println("Content Id: " + contentId);

                /**
                 * Check if this is a Text attachment.
                 */
                if ( contentType.indexOf("text") >=0 ) {
                    /**
                     * Get and print the string content if it is a text
                     * attachment.
                     */
                    String content = (String) ap.getContent();
                    System.out.println("Attachment content:\n\n" + content);
                    System.out.println("*** attachment content ends ***");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * The main method to start the sample ReceiveSOAPMessageWithJMS.
     */
    public static void main (String[] args) {

        String topicName = "TestTopic";

        if (args.length > 0) {
            topicName = args[0];
        }
        try {
            ReceiveSOAPMessageWithJMS rsm = new ReceiveSOAPMessageWithJMS(topicName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
