package com.test;

import javax.jws.WebService;

import com.test.WSImpl;


@WebService(targetNamespace = "http://test.com/", endpointInterface = "com.test.WSImpl", portName = "BSImplPort", serviceName = "BSImplService")
public class BSImpl implements WSImpl {
	
	public String sayHi(String name){
		
		return "Hi......."+name;
	}
	
	public String sayHello(String name){
		
		return "Hello......."+name;
	}

}
