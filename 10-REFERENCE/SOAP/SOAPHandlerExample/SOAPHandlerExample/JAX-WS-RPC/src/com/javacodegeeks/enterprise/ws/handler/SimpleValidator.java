package com.javacodegeeks.enterprise.ws.handler;

import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public class SimpleValidator implements LogicalHandler<LogicalMessageContext> {

	

	@Override
	public boolean handleMessage(LogicalMessageContext context) {

		System.out.println("Inside Logical Handler"+context.getMessage().getPayload());
		
		return true;
	}
	
	@Override
	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleFault(LogicalMessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
