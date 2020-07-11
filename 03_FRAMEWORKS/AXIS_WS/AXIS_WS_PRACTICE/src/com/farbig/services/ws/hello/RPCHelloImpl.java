package com.farbig.services.ws.hello;

@javax.jws.WebService(targetNamespace = "http://hello.ws.services.farbig.com/", serviceName = "RPCHello", portName = "RPCHelloServices", wsdlLocation = "WEB-INF/wsdl/RPCHello.wsdl")
@javax.jws.soap.SOAPBinding(style = javax.jws.soap.SOAPBinding.Style.RPC)

public class RPCHelloImpl {
	

	com.farbig.services.ws.hello.HelloServiceImpl helloServiceImpl = new com.farbig.services.ws.hello.HelloServiceImpl();
	
 	
	public String sayHello(String name, Integer i) {
		return helloServiceImpl.sayHello(name, i);
	}

}