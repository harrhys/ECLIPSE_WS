package com.farbig.services.ws.emp;

import java.util.ArrayList;

@javax.jws.WebService(targetNamespace = "http://emp.test.com/", serviceName = "RPCEmpService", portName = "RPCEmpServices", wsdlLocation = "WEB-INF/wsdl/RPCEmpService.wsdl")
@javax.jws.soap.SOAPBinding(style = javax.jws.soap.SOAPBinding.Style.RPC)
public class RPCEmpImpl {

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