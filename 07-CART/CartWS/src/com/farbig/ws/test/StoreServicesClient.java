package com.farbig.ws.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(
		name = "StoreServices", 
		targetNamespace = "http://localhost:7001/BOTH/StoreServices")
public class StoreServicesClient extends Service {

	protected StoreServicesClient(URL wsdlDocumentLocation, QName serviceName) {
		super(wsdlDocumentLocation, serviceName);
	}

	public static void main(String[] args) {

		try {
			StoreServicesClient client = new StoreServicesClient(
					new URL("http://localhost:7001/BOTH/StoreServices?WSDL"),
					new QName("http://localhost:7001/BOTH/StoreServices", "StoreServices"));
			client.getPorts().next();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

}
