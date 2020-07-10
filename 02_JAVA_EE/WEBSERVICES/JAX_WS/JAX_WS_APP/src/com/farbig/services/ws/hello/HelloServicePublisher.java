package com.farbig.services.ws.hello;

import javax.xml.ws.Endpoint;

public class HelloServicePublisher {

	public static void main(String[]  arg)
	{
		HelloService mysews = new HelloServiceImpl();
		
		Endpoint endpoint = Endpoint.publish("http://localhost:80/test/helloservice", mysews);
		
		System.out.println(endpoint.getImplementor());
	}

}
