package com.farbig.practice.util;

public class PrintUtil {
	
	public static int LEVEL=0;
	
	public static void println(int level,Object s) {
		if(level>=LEVEL)
		System.out.println(s);
	}
	
	public static void print(int level,Object s) {
		if(level>=LEVEL)
		System.out.print(s);
	}
	
	public static void println() {
	
		System.out.println();
	}
	
	public static void println(int level) {
		if(level>=LEVEL)
		System.out.println();
	}
	
	public static void printArray(int level, int[] temp)
	{
		if(level>=LEVEL)
		{	System.out.print("[");
			for (int i = 0; i < temp.length; i++) {
				if(i!=0)
					System.out.print(",");
				System.out.print(temp[i]);
			}
			System.out.println("]");
		}
	}
	
	public static void main(String args[]) {
	      try {
	         InnerClass inner =  InnerClass.class.newInstance();
	         inner.test();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	   }
	   // inner class
	    class InnerClass {
	      public void test() {
	         System.out.println("Welcome to TutorialsPoint !!!");
	      }
	   }
}
