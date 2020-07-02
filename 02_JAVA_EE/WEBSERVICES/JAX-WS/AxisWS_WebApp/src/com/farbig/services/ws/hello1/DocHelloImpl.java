package com.farbig.services.ws.hello1;

@javax.jws.WebService(targetNamespace = "http://hello.test.com/", serviceName = "DocHello", portName = "DocHelloServices", wsdlLocation = "WEB-INF/wsdl/DocHello.wsdl")
public class DocHelloImpl {

	com.farbig.services.ws.hello1.HelloServiceImpl helloServiceImpl = new com.farbig.services.ws.hello1.HelloServiceImpl();

	public String sayHello(String name, Integer i) {
		return helloServiceImpl.sayHello(name, i);
	}

}