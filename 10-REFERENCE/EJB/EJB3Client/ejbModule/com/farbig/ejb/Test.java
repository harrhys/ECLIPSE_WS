package com.farbig.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Test {

	public static void main(String[] args) throws Exception {
		
		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

		Context c  = new InitialContext(p);
		
	   MyEJBRemote bean = 	(MyEJBRemote) c.lookup("myfirstejb#com.farbig.ejb.MyEJBRemote");
	   
	   System.out.println(bean.sayHello("balaji"));
		
	}

}
