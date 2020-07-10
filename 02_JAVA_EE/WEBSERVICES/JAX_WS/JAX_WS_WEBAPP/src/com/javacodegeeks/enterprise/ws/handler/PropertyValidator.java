package com.javacodegeeks.enterprise.ws.handler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

public class PropertyValidator implements SOAPHandler<SOAPMessageContext> {

	private final String VALID_PROPERTY = "RANDOM";

	@Override
	public boolean handleMessage(SOAPMessageContext context) {

		System.out.println("Server executing SOAP Handler");

		Boolean outBoundProperty = (Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		// if this is an incoming message from the client
		if (!outBoundProperty) {

			try {

				// Get the SOAP Message and grab the headers
				SOAPMessage soapMsg = context.getMessage();
				SOAPEnvelope soapEnv = soapMsg.getSOAPPart().getEnvelope();
				SOAPHeader soapHeader = soapEnv.getHeader();

				// // if there are no headers, create an empty one
				// if (soapHeader == null) {
				// soapHeader = soapEnv.addHeader();
				// // throw exception
				// attacheErrorMessage(soapMsg, "No SOAP header.");
				// }

				// Grab an iterator to go through the headers
				Iterator<?> headerIterator = soapHeader
						.extractHeaderElements(SOAPConstants.URI_SOAP_ACTOR_NEXT);

				// if there is no additional header
				if (headerIterator != null && headerIterator.hasNext()) {

					// Extract the property node of the header
					Node propertyNode = (Node) headerIterator.next();

					String property = null;

					if (propertyNode != null)
						property = propertyNode.getValue();

					if (VALID_PROPERTY.equals(property)) {
						// Output the message to the Console -- for debug
						soapMsg.writeTo(System.out);
					} else {
						// Restrict the execution of the Remote Method
						// Attach an error message as a response
						SOAPBody soapBody = soapMsg.getSOAPPart().getEnvelope().getBody();
						SOAPFault soapFault = soapBody.addFault();
						soapFault.setFaultString("Invalid Property");
						
						throw new SOAPFaultException(soapFault);
					}
				}
			} catch (SOAPException e) {
				System.err.println(e);
			} catch (IOException e) {
				System.err.println(e);
			}

		}

		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
