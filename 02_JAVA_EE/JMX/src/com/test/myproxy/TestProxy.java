package com.test.myproxy;

public class TestProxy {

	public static void main(String[] args) {
		
		MyInterface proxy  = (MyInterface) ServiceProxy.getProxy();
		
		proxy.test();

	}

}
