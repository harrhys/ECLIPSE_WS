package com.weblogic.sample.mdbapp;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import weblogic.ejbgen.Constants;
import weblogic.ejbgen.MessageDriven;

import com.weblogic.sample.common.FubarRequestData;
import com.weblogic.sample.common.LH;

//For more info see:
//http://e-docs.bea.com/wls/docs92/ejb/EJBGen_reference.html#wp1070171
@MessageDriven(
	ejbName = "FubarTopicMDB",
	destinationType = "javax.jms.Topic",
	destinationJndiName = "com.weblogic.sample.mdbapp.FubarTopic",
	durable = Constants.Bool.FALSE, // Applies to topics only
	transactionType = MessageDriven.MessageDrivenTransactionType.BEAN,
	defaultTransaction = MessageDriven.DefaultTransaction.UNSPECIFIED,
	transTimeoutSeconds = "0",
	jmsPollingIntervalSeconds = "0",
	clientsOnSameServer = Constants.Bool.FALSE,
	initialBeansInFreePool = "1",
	maxBeansInFreePool = "1")

public class FubarTopicMDB implements MessageDrivenBean, MessageListener
{
	public final static String VERSION = "1.0";
	public final static String PACKAGE_AND_CLASS = "com.weblogic.sample.mdbapp.FubarTopicMDB";
	private static final long serialVersionUID = -399858258999221338L;

	private MessageDrivenContext ctx = null;

	public void setMessageDrivenContext(MessageDrivenContext context)
	{
		final String METHOD = "setMessageDrivenContext";
		ctx = context;
		if (ctx == null)
		{
			LH.error(VERSION, PACKAGE_AND_CLASS, METHOD,
				"ctx is null");
		}
	}

	public void ejbCreate()
	{
		final String METHOD = "ejbCreate";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}

	public void ejbRemove()
	{
	}

	public FubarTopicMDB() 
	{
		final String METHOD = "FubarTopicMDB";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}
	
	public void onMessage(Message msg)
	{
		final String METHOD = "onMessage";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
		
		try
		{
			TextMessage tm = (TextMessage) msg;
			String text = tm.getText();
			if (text != null)
			{
				FubarRequestData rd = new FubarRequestData();
				rd.parseFromString(text);
				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"called with request: ["+rd.dump()+"]");
				if (rd.sleepTime != 0)
				{
					LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
						"sleeping for request: ["+rd.dump()+"]");
					try
					{
						Thread.sleep(rd.sleepTime);
					}
					catch(Throwable e)
					{
						LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
							"sleep interrupted");
					}
				}
				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"processed request: ["+rd.dump()+"]");
			}
			else
			{
				LH.error(VERSION, PACKAGE_AND_CLASS, METHOD,
					"null message");
			}
		}
		catch(Throwable e)
		{
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured", e);
		}
	}
}
