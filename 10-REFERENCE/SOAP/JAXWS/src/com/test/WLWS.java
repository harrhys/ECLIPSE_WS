package com.test;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "WLWS", targetNamespace = "http://com.test/")
public interface WLWS {

	@WebMethod(operationName = "hello")
	public void hello();
}