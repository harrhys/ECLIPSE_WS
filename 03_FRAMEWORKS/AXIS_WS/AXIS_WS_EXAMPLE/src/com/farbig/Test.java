package com.farbig;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

public class Test {

	/*
	 * @EJB TestEJB myejb;
	 * 
	 * public String testHello(String name) { return myejb.sayHello(name); }
	 */

	public static void main(String ar[]) throws NamingException {

		Properties p = new Properties();
		p.put(EJBContainer.MODULES, "myejbcon");
		EJBContainer con = EJBContainer.createEJBContainer();

		//Context c = con.getContext();
		
		System.out.println(con.getContext().list("/"));

		//TestEJB ejb = (TestEJB) c.lookup("java:global/classes/TestEJB");

		// ejb.sayHello("Balaji");

	//	System.out.println(ejb.sayHello("Balaji"));

		con.close();

	}

}
