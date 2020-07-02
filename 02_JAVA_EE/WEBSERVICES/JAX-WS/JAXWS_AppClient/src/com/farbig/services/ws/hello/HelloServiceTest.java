package com.farbig.services.ws.hello;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class HelloServiceTest {

	public static void main(String[] args) throws MalformedURLException {
		
		  URL url = new URL("http://localhost:80/test/helloservice?wsdl");  
		   
	        //1st argument service URI, refer to wsdl document above  
	        //2nd argument is service name, refer to wsdl document above  
	        QName qname = new QName("http://hello.ws.services.farbig.com/", "MyWSImplService");  
	        Service service = Service.create(url, qname);  
	        HelloService hello = service.getPort(HelloService.class);  
	        System.out.println(hello.sayHello("javatpoint"));  

	}

}
