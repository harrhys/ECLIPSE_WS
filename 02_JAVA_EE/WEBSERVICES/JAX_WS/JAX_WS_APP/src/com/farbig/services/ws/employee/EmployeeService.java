package com.farbig.services.ws.employee;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
public interface EmployeeService {
	
	public EmployeeTO getEmployee(String name);
	
	@SOAPBinding(style=Style.DOCUMENT)
	public String getFullName(String first, String last);
	

}
