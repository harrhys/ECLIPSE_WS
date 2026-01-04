package com.test.jms;

import javax.ejb.MessageDrivenBean;
import javax.jms.Message;
import javax.jms.MessageListener;

import weblogic.ejb.GenericMessageDrivenBean;
import weblogic.ejbgen.MessageDriven;

@MessageDriven(ejbName = "MyMessageDrivenBean", destinationJndiName = "MYQUEUE", destinationType = "javax.jms.Queue")
public class MyMessageDrivenBean extends GenericMessageDrivenBean implements
		MessageDrivenBean, MessageListener {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message msg) {
		// IMPORTANT: Add your code here
		
		
	}
}
