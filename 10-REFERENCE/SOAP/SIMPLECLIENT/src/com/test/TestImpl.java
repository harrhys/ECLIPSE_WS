package com.test;

import javax.jws.WebService;

@WebService 
public interface TestImpl {
	
	
	public Emp getEmployee(String name);
	
}
	
