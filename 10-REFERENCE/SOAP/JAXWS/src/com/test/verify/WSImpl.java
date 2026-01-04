package com.test.verify;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "WSImpl", targetNamespace = "http://test.com/")
public interface WSImpl {

	@WebMethod(operationName = "sayHi", action = "urn:SayHi")
	@WebResult(name = "return")
	public String sayHi(@WebParam(name = "arg0") String name);
	
	@WebMethod(operationName = "sayHello", action = "urn:SayHello")
	@WebResult(name = "return")
	public String sayHello(@WebParam(name = "arg0") String name);

}