package com.farbig.practice.dsa.algorithms.dp;

import com.farbig.practice.util.PrintUtil;

public class Fibonacci {

	static int[] fib;

	public static void main(String[] args) {

		PrintUtil.LEVEL = 1;

		int number = 15;

		fib = new int[number+1];

		fib_tabulation(number);

		fib = new int[number+1];

		fib_Memoization(number);
	}

	// DP - Tabulation - Bottom Up
	public static int fib_tabulation(int n) {
		
		fib[1] = 1;
		fib[2] = 1;
		PrintUtil.print(2, "1 1 ");
		for (int i = 3; i <=n; i++) {
			fib[i] = fib[i - 2] + fib[i - 1];
			PrintUtil.print(2, fib[i] + " ");
		}
		PrintUtil.println();

		return fib[n];
	}

	// DP - Memoization - Top Down
	static int fib_Memoization(int n) {

		// if fib(n) has already
		// been computed we do not
		// do further recursive
		// calls and hence reduce
		// the number of repeated
		// work

		if (fib[n] != 0)
			return fib[n];

		else {

			// base case
			if (n <= 2) {
				fib[n] = 1;
				PrintUtil.print(2, fib[n] + " ");
				return fib[n];
			}

			// store the computed value
			// of fib(n) in an array
			// term at index n to so that
			// it does not needs to be
			// precomputed again
			fib[n] = fib_Memoization(n - 2) + fib_Memoization(n - 1);
			PrintUtil.print(2, fib[n] + " ");

			return fib[n];
		}
	}
}
