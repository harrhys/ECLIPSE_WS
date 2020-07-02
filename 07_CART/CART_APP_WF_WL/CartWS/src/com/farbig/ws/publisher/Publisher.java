package com.farbig.ws.publisher;

import javax.xml.ws.Endpoint;

import com.farbig.ws.services.StoreServiceImpl;

public class Publisher {

	public static void main(String[] args) {
		
		Endpoint.publish("http://localhost/services", new StoreServiceImpl());

	}

}
