package com.test.hi;

import com.test.emp.dto.EmpTO;

@javax.jws.WebService(targetNamespace = "http://hi.test.com/", serviceName = "DocHiService", portName = "DocHiServices", wsdlLocation = "WEB-INF/wsdl/DocHiService.wsdl")
public class DocHiImpl {

	com.test.hi.HiServiceImpl hiServiceImpl = new com.test.hi.HiServiceImpl();

	public String sayHello(EmpTO emp) {
		return hiServiceImpl.sayHello(emp);
	}

	public String sayHi(EmpTO emp, Integer i) {
		return hiServiceImpl.sayHi(emp, i);
	}

}