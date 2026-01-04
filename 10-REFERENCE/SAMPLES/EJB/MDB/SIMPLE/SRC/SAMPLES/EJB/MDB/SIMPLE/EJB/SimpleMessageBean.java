/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.ejb.mdb.simple.ejb;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.ejb.CreateException;
import javax.naming.*;
import javax.jms.*;

/**
 * A simple message driven bean.
 * The code illustrates the requirements of a message-driven bean class:
 * <ul>
 * <li>It implements the <code>MessageDrivenBean</code> and <code>MessageListener</code> interfaces. 
 * <li>The class is defined as <code>public</code>. 
 * <li>The class cannot be defined as <code>abstract</code> or <code>final</code>. 
 * <li>It implements one <code>onMessage</code> method. 
 * <li>It implements one <code>ejbCreate</code> method and one <code>ejbRemove</code> method. 
 * <li>It contains a public constructor with no arguments. 
 * <li>It must not define the <code>finalize</code> method.  
 * </ul>
 *
 * Unlike session and entity beans, message-driven beans do not have the remote or local
 * interfaces that define client access. Client components do not locate message-driven beans
 * and invoke methods on them. Although message-driven beans do not have business methods,
 * they may contain helper methods that are invoked internally by the onMessage method.
 *
 */
public class SimpleMessageBean implements MessageDrivenBean,
    MessageListener {

    private transient MessageDrivenContext mdc = null;
    private Context context;

    /**
     * Default constructor. Creates a bean. Required by EJB spec.
     */
    public SimpleMessageBean() {
        System.out.println("In SimpleMessageBean.SimpleMessageBean()");
    }

    /**
     * Sets the context for the bean.
     * @param mdc the message-driven-bean context.
     */
    public void setMessageDrivenContext(MessageDrivenContext mdc) {
        System.out.println("In "
            + "SimpleMessageBean.setMessageDrivenContext()");
	this.mdc = mdc;
    }

    /**
     * Creates a bean. Required by EJB spec.
     */
    public void ejbCreate() {
	System.out.println("In SimpleMessageBean.ejbCreate()");
    }

    /**
     * When the queue receives a message, the EJB container invokes the <code>onMessage</code>
     * method of the message-driven bean. In the <code>SimpleMessageBean</code> class, the
     * <code>onMessage</code> method casts the incoming message to a TextMessage and displays the text.
     * @param inMessage incoming message.
     */
    public void onMessage(Message inMessage) {
        TextMessage msg = null;

        try {
            if (inMessage instanceof TextMessage) {
                msg = (TextMessage) inMessage;
                System.out.println("MESSAGE BEAN: Message received: "
                    + msg.getText());
            } else {
                System.out.println("Message of wrong type: "
                    + inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }  // onMessage

    /**
     * Removes the bean. Required by EJB spec.
     */
    public void ejbRemove() {
        System.out.println("In SimpleMessageBean.remove()");
    }

} // class
