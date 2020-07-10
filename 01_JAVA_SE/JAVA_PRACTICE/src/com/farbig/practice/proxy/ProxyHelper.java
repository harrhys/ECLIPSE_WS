package com.farbig.practice.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyHelper {

	public static  <T> Object getProxy(Class[] classes ) {
		
		ClassLoader classLoader = classes[0].getClassLoader();

		InvocationHandler handler = new ProxyHandler();

		Object proxy = Proxy.newProxyInstance(classLoader, classes, handler);

		return proxy;
	}

}
