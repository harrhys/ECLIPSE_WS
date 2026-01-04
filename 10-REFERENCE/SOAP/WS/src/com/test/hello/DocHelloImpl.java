package com.test.hello;

@javax.jws.WebService(targetNamespace = "http://hello.test.com/", serviceName = "DocHello", portName = "DocHelloServices", wsdlLocation = "WEB-INF/wsdl/DocHello.wsdl")
public class DocHelloImpl {

	com.test.hello.HelloServiceImpl helloServiceImpl = new com.test.hello.HelloServiceImpl();

	public String sayHello(String name, Integer i) {
		return helloServiceImpl.sayHello(name, i);
	}

}