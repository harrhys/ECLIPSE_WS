package com.farbig.ws;

import javax.xml.ws.Endpoint;

public class WSPublisher {

	public static void main(String[]  arg)
	{
		MyWS mysews = new MyWS();
		
		Endpoint endpoint = Endpoint.publish("http://localhost://8001/test/mysews", mysews);
		
		System.out.println(endpoint.getImplementor());
	}

}
