package com.weblogic.sample.ejbapp;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.jms.TextMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import weblogic.ejbgen.Constants;
import weblogic.ejbgen.FileGeneration;
import weblogic.ejbgen.JarSettings;
import weblogic.ejbgen.JndiName;
import weblogic.ejbgen.RemoteMethod;
import weblogic.ejbgen.Session;

import com.weblogic.sample.common.FubarRequestData;
import com.weblogic.sample.common.FubarResponseData;
import com.weblogic.sample.common.LH;

//For more info see:
//http://e-docs.bea.com/wls/docs92/ejb/EJBGen_reference.html#wp1096088
@Session(
	ejbName = "FubarBMTEJB",
	type = Session.SessionType.STATELESS,
	transactionType = Session.SessionTransactionType.BEAN,
	defaultTransaction = Constants.TransactionAttribute.UNSPECIFIED,
	transTimeoutSeconds = "0",
	sessionTimeoutSeconds = "0",
	allowConcurrentCalls = Constants.Bool.FALSE,
	enableCallByReference = Constants.Bool.TRUE,
	clientsOnSameServer = Constants.Bool.FALSE,
	methodsAreIdempotent = Constants.Bool.FALSE,
	isClusterable = Constants.Bool.TRUE,
	homeIsClusterable = Constants.Bool.TRUE,
	homeLoadAlgorithm = Constants.HomeLoadAlgorithm.ROUND_ROBIN,
	initialBeansInFreePool = "10",
	maxBeansInFreePool = "10")

//For more info see:
//http://e-docs.bea.com/wls/docs92/ejb/EJBGen_reference.html#wp1069615
@FileGeneration(
	localHome = Constants.Bool.FALSE,
	localClass = Constants.Bool.FALSE,
	remoteHome = Constants.Bool.TRUE,
	remoteHomeName = "FubarBMTEJBHome",
	remoteClass = Constants.Bool.TRUE,
	remoteClassName = "FubarBMTEJBInterface")

//For more info see:
//http://e-docs.bea.com/wls/docs92/ejb/EJBGen_reference.html#wp1069795
@JarSettings(
	ejbClientJar = "FubarBMTEJBClientJ2SE.jar")

//For more info see:
//http://e-docs.bea.com/wls/docs92/ejb/EJBGen_reference.html#wp1069845
@JndiName(
	remote = "com.weblogic.sample.ejbapp.FubarBMTEJBHome")

public class FubarBMTEJB extends FubarEJBBaseClass
{
	public final static String VERSION = "1.0";
	public final static String PACKAGE_AND_CLASS = "com.weblogic.sample.ejbapp.FubarBMTEJB";
	private static final long serialVersionUID = 947502096794576339L;
	
	private static final String QUEUE_FACTORY_NAME = "com.weblogic.sample.mdbapp.FubarQueueFactory";
	private static final String QUEUE_NAME = "com.weblogic.sample.mdbapp.FubarQueue";
	private static final String TOPIC_FACTORY_NAME = "com.weblogic.sample.mdbapp.FubarTopicFactory";
	private static final String TOPIC_NAME = "com.weblogic.sample.mdbapp.FubarTopic";
	
	private QueueConnection queueConnectionRef = null;
	private QueueSession queueSessionRef = null;
	private QueueSender queueSenderRef = null;
	private TextMessage queueMessage = null;
	private TopicConnection topicConnectionRef = null;
	private TopicSession topicSessionRef = null;
	private TopicPublisher topicSenderRef = null;
	private TextMessage topicMessage = null;
	
	public void ejbCreate() throws CreateException 
	{
		super.ejbCreate();
		final String METHOD = "ejbCreate";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}
	
	public FubarBMTEJB() 
	{
		super();
		final String METHOD = "FubarBMTEJB";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}
	// For more info see:
	// http://e-docs.bea.com/wls/docs92/ejb/EJBGen_reference.html#wp1070679
	@RemoteMethod(
		isolationLevel=Constants.IsolationLevel.UNSPECIFIED)
	public FubarResponseData sampleMethod(
			FubarRequestData inputData) throws RemoteException
	{
		final String METHOD = "sampleMethod";
		FubarResponseData retval = new FubarResponseData();
		
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
		LH.staticversion(VERSION);
		//for database test
		//LH.testdb(VERSION,PACKAGE_AND_CLASS,METHOD,"TESTDB");
		if (inputData != null)
		{
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"called with request: ["+inputData.dump()+"]");
			retval.result = VERSION+"-"+PACKAGE_AND_CLASS+"."+METHOD+"():"+
				"->["+inputData.dump()+"]";
	
			if (inputData.asyncFlag == true)
			{
				if (queueSenderRef == null)
				{
					connectToQueue();
				}
				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"queuing request: ["+inputData.dump()+"]");
				putQueueMessage(inputData.convertToString());
			}
			else if (inputData.publishFlag == true)
			{
				if (topicSenderRef == null)
				{
					connectToTopic();
				}
				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"publishing request: ["+inputData.dump()+"]");
				putTopicMessage(inputData.convertToString());
			}
			else
			{
				if (inputData.sleepTime != 0)
				{
					LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
						"sleeping for request: ["+inputData.dump()+"]");
					try
					{
						Thread.sleep(inputData.sleepTime);
					}
					catch(Throwable e)
					{
						LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
							"sleep interrupted");
					}
				}
				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"processed request: ["+inputData.dump()+"]");
			}
		}
		else
		{
			LH.error(VERSION, PACKAGE_AND_CLASS, METHOD,
				"inputData is NULL");
		}
		
		return retval;
	}
	private void connectToQueue()
	{
		final String METHOD = "connectToQueue";
		try 
	    {
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Establish initial context");
			javax.naming.InitialContext initialContext = 
				new javax.naming.InitialContext();
	        
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Performing lookup on queue factory name ["+
				QUEUE_FACTORY_NAME+"]");
	        QueueConnectionFactory queueConnectionFactoryLookup =
	        	(QueueConnectionFactory)initialContext.lookup(QUEUE_FACTORY_NAME);
	        
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Performing lookup on queue name ["+
				QUEUE_NAME+"]");
	        Queue queueLookup = 
	        	(Queue)initialContext.lookup(QUEUE_NAME);
	        
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Creating queue connection...");
	        queueConnectionRef = queueConnectionFactoryLookup.
	        	createQueueConnection();

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Starting queue connection...");
	        queueConnectionRef.start();

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"Creating queue session...");
	        queueSessionRef = queueConnectionRef.createQueueSession(
				false, // non transacted
				javax.jms.Session.AUTO_ACKNOWLEDGE);

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Creating queue sender...");
	        queueSenderRef = queueSessionRef.
	        	createSender(queueLookup);
	    	
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Creating queue message...");
	        queueMessage = queueSessionRef.createTextMessage();
	    }
	    catch(Throwable e)
	    {
	    	queueConnectionRef = null;
	    	queueSessionRef = null;
	    	queueSenderRef = null;
	    	queueMessage = null;
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured: ", e);
	    }
	}
	private void putQueueMessage(String message)
	{
		final String METHOD = "putQueueMessage";
	    try 
	    {
	    	queueMessage.setText(message);
    		queueSenderRef.send(queueMessage);
	    }
	    catch(Throwable e)
	    {
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured", e);
	    }
	}
	private void connectToTopic()
	{
		final String METHOD = "connectToTopic";
		try 
	    {
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Establish initial context");
			javax.naming.InitialContext initialContext = 
				new javax.naming.InitialContext();
	        
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Performing lookup on topic factory name ["+
				TOPIC_FACTORY_NAME+"]");
	        TopicConnectionFactory topicConnectionFactoryLookup =
	        	(TopicConnectionFactory)initialContext.lookup(TOPIC_FACTORY_NAME);
	        
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Performing lookup on topic name ["+
				TOPIC_NAME+"]");
	        Topic topicLookup = 
	        	(Topic)initialContext.lookup(TOPIC_NAME);
	        
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Creating topic connection...");
	        topicConnectionRef = topicConnectionFactoryLookup.
	        	createTopicConnection();

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Starting topic connection...");
	        topicConnectionRef.start();

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"Creating topic session...");
	        topicSessionRef = topicConnectionRef.createTopicSession(
				false, // non transacted
				javax.jms.Session.AUTO_ACKNOWLEDGE);

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Creating topic sender...");
	        topicSenderRef = topicSessionRef.
	        	createPublisher(topicLookup);
	    	
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Creating topic message...");
	        topicMessage = topicSessionRef.createTextMessage();
	    }
	    catch(Throwable e)
	    {
	    	topicConnectionRef = null;
	    	topicSessionRef = null;
	    	topicSenderRef = null;
	    	topicMessage = null;
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured: ", e);
	    }
	}
	private void putTopicMessage(String message)
	{
		final String METHOD = "putTopicMessage";
	    try 
	    {
	    	topicMessage.setText(message);
    		topicSenderRef.send(topicMessage);
	    }
	    catch(Throwable e)
	    {
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured", e);
	    }
	}
}
