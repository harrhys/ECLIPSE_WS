package com.farbig.practice.dsa.algorithms.recursion;

public class RecursionFunctions {

	public static void main(String[] args) {

		int result = SumOfFirstNProductofConsequetiveNumbers(5);

		System.out.println("Result : " + result);

		printStars(5);
	}

	// factorial
	public static int factorial(int n) {

		int f;

		if (n == 0)
			f = 1;
		else
			f = n * factorial(n - 1);

		return f;

	}

	// 2n+2(n-1)...+2*1+y
	static int fun1(int x, int y) {

		if (x == 0)
			return y;
		else
			return fun1(x - 1, 2 * x + y);
	}

	// n+(n-1)+...+1
	static int SumOfFirstN(int n) {

		if (n == 1)
			return 1;
		else
			return n + SumOfFirstN(n - 1);
	}

	// n*(n-1)+ (n-1)*(n-2)+...+2*1
	static int SumOfFirstNProductofConsequetiveNumbers(int n) {

		int nth_value;
		if (n == 1) {
			
			nth_value = 2;
			System.out.println(n + "->" + n + "*" + (n + 1) + " = " + nth_value);
		
		} else {
			
			nth_value = n * (n + 1) + SumOfFirstNProductofConsequetiveNumbers(n - 1);
			System.out.println(n + "->" + n + "*" + (n + 1) + " = " + nth_value);
		}
		return nth_value;

	}

	static void printStars(int n) {

		int i = 0;
		if (n > 1)
			printStars(n - 1);
		for (i = 0; i < n; i++)
			System.out.print(" * ");
		System.out.println();
	}

}
