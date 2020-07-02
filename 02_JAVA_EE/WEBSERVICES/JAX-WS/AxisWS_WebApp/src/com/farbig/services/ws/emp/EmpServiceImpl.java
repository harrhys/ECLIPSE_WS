package com.farbig.services.ws.emp;

import java.util.ArrayList;


public class EmpServiceImpl {
	
	private static ArrayList<EmpTO> Employees;
	
	static 
	{
		Employees = new ArrayList<EmpTO>();
	}
	
	public String sayHello(String name)
	{
		return "Hello "+name;
	}
	
	public void addEmployee(EmpTO emp)
	{
		Employees.add(emp);
	}
	
	public ArrayList<EmpTO> getEmployees()
	{
		return Employees;
	}

}
