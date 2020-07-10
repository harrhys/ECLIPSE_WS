package com.farbig.ws.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		logToSystemOut(context);
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		logToSystemOut(context);
		return true;
	}

	private void logToSystemOut(SOAPMessageContext context) {

		try {
			SOAPMessage message = context.getMessage();

			System.out.println("Incoming message:");
			ByteArrayOutputStream stream = new ByteArrayOutputStream();

			message.writeTo(stream);

			System.out.println(stream.toString());
			System.out.println("=====================================");
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}