package com.test.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		
		Test t = new Test();
		
		Method m = Test.class.getMethods()[1];
		
		m.invoke(t, 1,5);

	}
	
	public void test(int a, int b)
	{
		System.out.println("Test "+(a+b));
	}

}
