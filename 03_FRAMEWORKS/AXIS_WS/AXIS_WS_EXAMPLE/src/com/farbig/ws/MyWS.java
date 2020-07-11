package com.farbig.ws;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class MyWS {
	
	public String sayHello(String name)
	{
		return "Hello.."+name;
	}
	
	

}
