package com.farbig.services.ws.employee;

public class EmployeeTO {
	
	private String name;

	public String getName() {
		System.out.println("Returning Emp name as "+name);
		return name;
	}

	public void setName(String name) {
		System.out.println("Client:Emp Name set as "+name);
		this.name = name;
	}
}
