package com.farbig.practice.proxy;

public class ProxyTest {

	public static void main(String[] args) throws ClassNotFoundException {

		Class<?>[] classes = { ServiceInteface.class, ServiceInteface2.class };

		ServiceInteface proxy =

				(ServiceInteface) ProxyHelper.getProxy(classes);

		System.out.println(proxy.getMsg("Test Msg"));
		
	}

}
