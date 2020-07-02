package com.farbig.services.ws.hello;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(endpointInterface = "com.farbig.services.ws.hello.HelloService", serviceName = "HelloServices")
//@SOAPBinding(style = Style.DOCUMENT, use = Use.ENCODED, parameterStyle = ParameterStyle.WRAPPED)
public class HelloServiceImpl implements HelloService {
	
	public String sayHello(String firstname)
	{
		return "Hello.."+firstname;
	}
	
	

}
