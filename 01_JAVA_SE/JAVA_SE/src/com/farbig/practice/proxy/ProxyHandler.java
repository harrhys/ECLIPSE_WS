package com.farbig.practice.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
	
	

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		System.out.println(proxy.getClass().getName());
		
		ServiceImpl impl = new ServiceImpl();
		
		return impl.getMsg((String) args[0]);
	}

}
