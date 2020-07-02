package com.farbig.practice.reflection;

public class MyClass {
	static Object o = new Object() {
		public void m() {
		}
	};
	static Class c = o.getClass().getDeclaringClass();
	
	public static void main(String...strings)
	{
		System.out.println(c);
	}
}
