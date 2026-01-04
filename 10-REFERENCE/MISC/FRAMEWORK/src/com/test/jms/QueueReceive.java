package com.test.jms;
import java.util.Hashtable;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueReceive implements MessageListener
{
public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
public final static String JMS_FACTORY="QCF";
public final static String QUEUE="MYQUEUE";
private QueueConnectionFactory qconFactory;
private QueueConnection qcon;
private QueueSession qsession;
private QueueReceiver qreceiver;
private Queue queue;
private boolean quit = false;

public void onMessage(Message msg)
{
try {
String msgText;
if (msg instanceof TextMessage)
{
msgText = ((TextMessage)msg).getText();
}
else
{
msgText = msg.toString();
}
System.out.println("\n\t&lt;Msg_Receiver&gt; "+ msgText );
if (msgText.equalsIgnoreCase("quit"))
{
synchronized(this)
{
quit = true;
this.notifyAll(); // Notify main thread to quit
}
}
}
catch (JMSException jmse)
{
jmse.printStackTrace();
}
}
public void init(Context ctx, String queueName) throws NamingException, JMSException
{
qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
qcon = qconFactory.createQueueConnection();
qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
queue = (Queue) ctx.lookup(queueName);
qreceiver = qsession.createReceiver(queue);
qreceiver.setMessageListener(this);
qcon.start();
}

public void close()throws JMSException
{
qreceiver.close();
qsession.close();
qcon.close();
}

public static void main(String[] args) throws Exception
{
if (args.length != 1)
{
System.out.println("Usage: java QueueReceive WebLogicURL");
return;
}
InitialContext ic = getInitialContext(args[0]);
QueueReceive qr = new QueueReceive();
qr.init(ic, QUEUE);
System.out.println("JMS Ready To Receive Messages (To quit, send a \"quit\" message from QueueSender.class).");
// Wait until a "quit" message has been received.
synchronized(qr)
{
while (! qr.quit)
{
try
{
qr.wait();
}
catch (InterruptedException ie)
{}
}
}
qr.close();
}

private static InitialContext getInitialContext(String url) throws NamingException
{
Hashtable env = new Hashtable();
env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
env.put(Context.PROVIDER_URL, url);
return new InitialContext(env);
}
}