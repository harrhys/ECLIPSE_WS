package com.farbig.services.ws.employee;

public interface EmployeeService {
	
	public EmployeeTO getEmployee(String name);
	
	public String getFullName(String first, String last);
	

}
