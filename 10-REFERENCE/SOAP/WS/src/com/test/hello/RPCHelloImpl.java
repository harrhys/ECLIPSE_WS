package com.test.hello;

@javax.jws.WebService(targetNamespace = "http://hello.test.com/", serviceName = "RPCHello", portName = "RPCHelloServices", wsdlLocation = "WEB-INF/wsdl/RPCHello.wsdl")
@javax.jws.soap.SOAPBinding(style = javax.jws.soap.SOAPBinding.Style.RPC)

public class RPCHelloImpl {
	

	com.test.hello.HelloServiceImpl helloServiceImpl = new com.test.hello.HelloServiceImpl();
	
 	
	public String sayHello(String name, Integer i) {
		return helloServiceImpl.sayHello(name, i);
	}

}