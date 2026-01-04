package com.farbig;

import javax.ejb.EJB;

public class Test {
	
	@EJB TestEJB myejb;
	
	public String testHello(String name)
	{
	   return	myejb.sayHello(name);
	}
	
	
	public static void main(String ar[])
	{
		Test test = new Test();
		
		test.testHello("harrhy");
	}

}
