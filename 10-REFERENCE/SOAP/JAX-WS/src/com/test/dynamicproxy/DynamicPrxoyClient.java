package com.test.dynamicproxy;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;



public class DynamicPrxoyClient {

	public static void main(String[] args) throws Exception {
		
		try {

            String UrlString =
                "http://localhost:7001/SOAP/CalculatorImpl?WSDL";
            String nameSpaceUri = "vishar.com";
            String serviceName = "CalculatorImpl";
            String portName = "CalculatorImplPort";

            URL helloWsdlUrl = new URL(UrlString);
            
            ServiceFactory serviceFactory =
                ServiceFactory.newInstance();
            
            Service helloService =
                serviceFactory.createService(helloWsdlUrl, 
                new QName(nameSpaceUri, serviceName));
            
            Calculator myProxy = (Calculator) helloService.getPort(
                new QName(nameSpaceUri, portName), 
                com.test.dynamicproxy.Calculator.class); 

            System.out.println(myProxy.add(25,33));

        } catch (Exception ex) {
            ex.printStackTrace();
        } 
	}
}
