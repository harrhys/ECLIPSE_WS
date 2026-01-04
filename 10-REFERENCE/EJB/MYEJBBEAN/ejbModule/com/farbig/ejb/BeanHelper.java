package com.farbig.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.farbig.exception.ServiceException;

public class BeanHelper {

	public static Context getEJBContext() throws NamingException {

		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");

		Context context = new InitialContext(p);

		return context;
	}

	public static Object getBeanProxy(String jndi) throws NamingException {

		Object proxy = null;
		Properties p = new Properties();
		p.put(Context.PROVIDER_URL, "t3://localhost:7001");
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");

		Context context = new InitialContext(p);
		proxy = context.lookup(jndi);

		return proxy;
	}

	public static MyCMTBeanRemote getCMTBeanProxy() {

		MyCMTBeanRemote cmtproxy;
		try {
			cmtproxy = (MyCMTBeanRemote) (getBeanProxy("mycmtbean#com.farbig.ejb.MyCMTBeanRemote"));
		} catch (NamingException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Exception Occured while retrieving the CMT Proxy");
		}

		return cmtproxy;
	}

	public static MyBMTBeanRemote getBMTBeanProxy() {

		MyBMTBeanRemote bmtproxy;
		try {
			bmtproxy = (MyBMTBeanRemote) (getBeanProxy("mybmtbean#com.farbig.ejb.MyBMTBeanRemote"));
		} catch (NamingException e) {
			e.printStackTrace();
			throw new ServiceException(
					"Exception Occured while retrieving the BMT Proxy");
		}

		return bmtproxy;
	}

}
