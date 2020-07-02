package com.test.myproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.test.proxy.IVehicle;

public class ServiceProxy implements InvocationHandler {

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		MyInterface impl = new MyImpl();
		
		method.invoke(impl, args);
		
		return null ;
	}
	
	public static Object getProxy()
	{
		ClassLoader cl = MyInterface.class.getClassLoader();
		
		Class[] ca = {MyInterface.class};
		
		ServiceProxy sp = new ServiceProxy();
		
		Object o =  Proxy.newProxyInstance(cl, ca, sp );
		
		return o;
		
		
	}
	

}
