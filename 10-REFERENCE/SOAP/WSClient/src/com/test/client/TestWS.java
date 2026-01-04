package com.test.client;

import com.test.client.WSClient.EmpTO;
import com.test.client.WSClient.EmpWS;
import com.test.client.WSClient.WSImpl;


public class TestWS  {
 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		EmpWS service = new EmpWS();
		 WSImpl portType = service.getEmpServices();
		 
		 	EmpTO emp1 = new EmpTO();
		 	emp1.setDepartment("d0");
		 	
		   portType.addEmployee(emp1);
		   emp1.setDepartment("d1");
		   portType.addEmployee(emp1);
		   emp1.setDepartment("d2");
		   portType.addEmployee(emp1);
		   emp1.setDepartment("d2");
		   portType.addEmployee(emp1);
		   portType.addEmployee(emp1);
		   
		   for( EmpTO emp :portType.getEmployees() )
		   {
		 System.out.println(emp.getDepartment());
		   }

	}

}
