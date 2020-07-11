package com.farbig.services.ws.employee;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

public class CFXClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		QName serviceName = new QName("");
		QName portName = new QName("");

		Service service = Service.create(serviceName);
		
		String address = "http://localhost:7777/MySimpleService/SimplePort";
		
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, address);
		
		EmployeeService client = service.getPort(portName, EmployeeService.class);
		
		System.out.println(client.getEmployee("Balaji"));

	}

}
