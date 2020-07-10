package com.farbig.practice.dsa.algorithms.dp;

public class PerfectSquare {

	public static void main(String[] args) {

		System.out.println(isPerfectSquare(9));

		System.out.println(isPerfectSquare(10));

	}

	public static boolean isPerfectSquare(int x) {

		double y = Math.sqrt(x);

		x = (int) y;

		if (x < y)
			return false;
		else
			return true;
	}

}
