package com.farbig.services.ws.employee;


public class EmployeeServiceImpl implements EmployeeService {
	
	
	public EmployeeTO getEmployee(String name)
	{
		EmployeeTO employeeTO = new EmployeeTO();
		employeeTO.setName(name);
		System.out.println("Employee Name is set as "+name);
		return employeeTO;
	}

	public String getFullName(String first, String last) {
		System.out.println("First Name is set as "+first);
		return first+" "+last;
	}
	
	

}
