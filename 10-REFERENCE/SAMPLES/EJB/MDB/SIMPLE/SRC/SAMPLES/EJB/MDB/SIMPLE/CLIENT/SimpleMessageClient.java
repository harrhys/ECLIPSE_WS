/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.mdb.simple.client;

import javax.jms.*;
import javax.naming.*;

/**
 * A simple java client, demonstrating the use of message driven bean. The <code>SimpleMessageClient</code>
 * sends messages to the queue that the <code>SimpleMessageBean</code> listens to.
 * <p>In this process, client does the following, in order
 * <ul>
 * <li>Locates the connection factory and queue.
 * <li>creates the queue connection, session, and sender.
 * <li>Sends several messages to the queue.
 * </ul>
 * <br>
 * <b>Locating the connection factory and queue:</b>
 * <blockquote><pre>
 *  queueConnectionFactory = (QueueConnectionFactory)
 *  jndiContext.lookup ("java:comp/env/jms/MyQueueConnectionFactory");
 *  queue = (Queue) jndiContext.lookup("java:comp/env/jms/QueueName");
 * </pre></blockquote>
 * <br>
 * <b>Creating the queue connection, session, and sender:</b>
 * <blockquote><pre>
 * queueConnection = queueConnectionFactory.createQueueConnection();
 * queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
 * queueSender = queueSession.createSender(queue);
 * </pre></blockquote>
 * <br>
 * <b>Sending out messages:</b>
 * <blockquote><pre>
 * message = queueSession.createTextMessage();
 * for (int i = 0; i < NUM_MSGS; i++) {
 *      message.setText("This is message " + (i + 1));
 *      System.out.println("Sending message: " + message.getText());
 *      queueSender.send(message);
 * }
 * </pre></blockquote>
 * <br>
 * <b>The client prints the following messages:</b>
 * <pre>
 * Sending message: This is message 1
 * Sending message: This is message 2
 * Sending message: This is message 3
 * </pre>
 * <br>
 * The message bean receives the messages and are printed in the log file as follows:
 * <pre>
 * MESSAGE BEAN: Message received: This is message 1
 * MESSAGE BEAN: Message received: This is message 2
 * MESSAGE BEAN: Message received: This is message 3
 * </pre>
 *
 *
 */
public class SimpleMessageClient {

   /**
    * The main method of the client. The client sends three messages to the message queue
    * and on the other hand the bean receives these messges asynchronously from the queue.
    *
    */
    public static void main(String[] args) {

        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueSender             queueSender = null;
        TextMessage             message = null;
        final int               NUM_MSGS = 3;

        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI " +
                "context: " + e.toString());
            System.exit(1);
        }

        try {
            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup
                ("java:comp/env/jms/MyQcf");
            queue = (Queue) jndiContext.lookup("java:comp/env/jms/MyQueue");
        } catch (NamingException e) {
            System.out.println("JNDI lookup failed: " +
                e.toString());
            System.exit(1);
        }

        try {
            queueConnection =
                queueConnectionFactory.createQueueConnection();
            queueSession =
                queueConnection.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();

            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Sending message: " +
                    message.getText());
                queueSender.send(message);
            }

        } catch (Throwable e) {
            System.out.println("Exception occurred: " + e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            } // if
            System.exit(0);
        } // finally
    } // main
} // class

