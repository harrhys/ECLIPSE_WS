package com.farbig.services.ws.emp;

import java.util.ArrayList;

@javax.jws.WebService(targetNamespace = "http://emp.ws.services.farbig.com/", serviceName = "DocEmpService", portName = "DocEmpService", wsdlLocation = "WEB-INF/wsdl/DocEmpService.wsdl")
public class DocEmpImpl {

	com.farbig.services.ws.emp.EmpServiceImpl empServiceImpl = new com.farbig.services.ws.emp.EmpServiceImpl();

	public String sayHello(String name) {
		return empServiceImpl.sayHello(name);
	}

	public void addEmployee(EmpTO emp) {
		empServiceImpl.addEmployee(emp);
	}

	public ArrayList<EmpTO> getEmployees() {
		return empServiceImpl.getEmployees();
	}

}