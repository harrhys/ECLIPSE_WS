package com.farbig.cart.test;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.farbig.cart.entity.Admin;

public class JMSTest {

	public static void main(String[] args) throws NamingException, JMSException {
		
		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		
		Context context = new InitialContext(p);
		
		QueueConnectionFactory qf = (QueueConnectionFactory) context.lookup("CARTJMSCF");
		
		Queue q = (Queue) context.lookup("CARTJMSDQ");
		
		QueueConnection qc = qf.createQueueConnection();
		
		QueueSession qs = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		
		ObjectMessage msg = qs.createObjectMessage();
		
		Admin a = new Admin();
		
		a.setName("TEST JMS Admin");
		
		msg.setObject(a);
		
		qs.createSender(q).send(msg);
		
		qs.close();
		qc.close();
		
		
		

	}

}
