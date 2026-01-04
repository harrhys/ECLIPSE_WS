package com.farbig.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Test {

	public static void main(String[] args) throws Exception {

		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");

		Context c;
		c = new InitialContext(p);

		MyBMTBeanRemote bmtproxy = (MyBMTBeanRemote) c
				.lookup("mybmtbean#com.farbig.ejb.MyBMTBeanRemote");

		System.out.println(bmtproxy.createBaseUser());

	}

	public static void testEJBPool() {
		/*
		 * for (int i = 0; i < 10; i++) {
		 * 
		 * testEJB();
		 * 
		 * }
		 */

		for (int i = 1; i < 11; i++) {

			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());
					testEJB();
				}
			};

			Thread t = new Thread(runnable, "Thread-" + i);
			t.start();

		}
	}

	public static void testEJB() {

		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");

		Context c;
		try {
			c = new InitialContext(p);
			MyCMTBeanRemote cmtproxy = (MyCMTBeanRemote) c
					.lookup("mycmtbean#com.farbig.ejb.MyCMTBeanRemote");
			System.out.println(cmtproxy.testCMTBMTCMT());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
