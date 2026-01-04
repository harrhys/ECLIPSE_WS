package com.test.hi;

import com.test.emp.dto.EmpTO;



public class HiServiceImpl {
	
	 
	
	public String sayHello(EmpTO emp)
	{
		return "Hi.. "+emp.getName();
	}
	
	public String sayHi(EmpTO emp, Integer i)
	{
		return "Hi.. "+emp.getName();
	}
	
	
 

}
