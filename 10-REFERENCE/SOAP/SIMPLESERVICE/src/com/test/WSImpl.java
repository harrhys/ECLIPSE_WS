package com.test;


public class WSImpl implements WSInterface {
	
	
	public Emp getEmployee(String name)
	{
		Emp emp = new Emp();
		emp.setName(name);
		System.out.println("Employee Name is set as "+name);
		return emp;
	}

	public String getFullName(String first, String last) {
		System.out.println("First Name is set as "+first);
		return first+" "+last;
	}
	
	

}
