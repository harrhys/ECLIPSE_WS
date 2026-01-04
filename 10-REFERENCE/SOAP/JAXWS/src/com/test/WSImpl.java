package com.test;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "WSImpl", targetNamespace = "http://test.com/")
public interface WSImpl {

	public String sayHi( String name);
	
	public String sayHello( String name);

}