package com.farbig.services.ws.hello1;

@javax.jws.WebService(targetNamespace = "http://hello.test.com/", serviceName = "RPCHello", portName = "RPCHelloServices", wsdlLocation = "WEB-INF/wsdl/RPCHello.wsdl")
@javax.jws.soap.SOAPBinding(style = javax.jws.soap.SOAPBinding.Style.RPC)

public class RPCHelloImpl {
	

	com.farbig.services.ws.hello1.HelloServiceImpl helloServiceImpl = new com.farbig.services.ws.hello1.HelloServiceImpl();
	
 	
	public String sayHello(String name, Integer i) {
		return helloServiceImpl.sayHello(name, i);
	}

}