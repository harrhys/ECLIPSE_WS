package com.farbig.practice.dsa.algorithms.recursion;

public class ReverseNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("0\t"+getReverseNumber_1(12345, 0));

		System.out.println("0\t"+getReverseNumber_2(12345, 0));
		
		System.out.println(" \t"+getReverseString("ab", ""));

		Double rand = (Math.random() * 1000);

		System.out.println(rand.floatValue());

		double rand1 = (Math.random() * .6) - .3;

		System.out.println(rand1);

	}

	public static int getReverseNumber_1(Integer actual, Integer reverse) {
		
		System.out.println("Printing stack trace:START");
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < elements.length; i++) {
		     StackTraceElement s = elements[i];
		     System.out.println(s.getClassName() + "." + s.getMethodName() + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
		}
		System.out.println("Printing stack trace:END");
		int a = actual % 10;
		reverse = reverse * 10 + a;
		int b = actual / 10;
		if (b > 0) {
			reverse = getReverseNumber_1(b, reverse);
		}
		
		return reverse;
	}

	// 12345
	public static int getReverseNumber_2(int actual, int reverse) {
		System.out.println(actual + "\t" + reverse);
		reverse = reverse * 10 + actual % 10;
		if (actual / 10 > 0) {

			reverse = getReverseNumber_2(actual / 10, reverse);
		}
		return reverse;
	}
	//abcde
	public static String getReverseString(String actual, String reverse) {
		System.out.println(actual + "\t" + reverse);
		reverse = reverse  + actual.substring(actual.length()-1);
		if (actual.length() > 1) {
			reverse = getReverseString(actual.substring(0,actual.length()-1) , reverse);
		}
		return reverse;
	}


	

}
