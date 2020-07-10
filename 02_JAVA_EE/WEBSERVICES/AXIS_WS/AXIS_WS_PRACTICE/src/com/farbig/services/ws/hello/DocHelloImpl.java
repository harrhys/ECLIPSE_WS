package com.farbig.services.ws.hello;

@javax.jws.WebService(targetNamespace = "http://hello.ws.services.farbig.com/", serviceName = "DocHello", portName = "DocHelloServices", wsdlLocation = "WEB-INF/wsdl/DocHello.wsdl")
public class DocHelloImpl {

	com.farbig.services.ws.hello.HelloServiceImpl helloServiceImpl = new com.farbig.services.ws.hello.HelloServiceImpl();

	public String sayHello(String name, Integer i) {
		return helloServiceImpl.sayHello(name, i);
	}

}