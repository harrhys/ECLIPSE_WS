package com.test.emp;

import java.util.ArrayList;
import com.test.emp.dto.EmpTO;

@javax.jws.WebService(targetNamespace = "http://emp.test.com/", serviceName = "RPCEmpService", portName = "RPCEmpServices", wsdlLocation = "WEB-INF/wsdl/RPCEmpService.wsdl")
@javax.jws.soap.SOAPBinding(style = javax.jws.soap.SOAPBinding.Style.RPC)
public class RPCEmpImpl {

	com.test.emp.EmpServiceImpl empServiceImpl = new com.test.emp.EmpServiceImpl();

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