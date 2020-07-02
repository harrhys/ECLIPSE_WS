package com.farbig.practice.dsa.algorithms.recursion;

public class LinearRecurrsion {

	public static void main(String[] args) {

		int[] A = { 1, 2, 3, 4, 5, 6 };

		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i] + " ");
		}

		// Reversing an Array

		reverseArray(A, 0);

		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i] + " ");
		}

		// Sum of Array elements

		System.out.println(ArraySum(A, 0));

	}

	//n should start from 0
	public static void reverseArray(int[] A, int n) {

		int lastIndex = A.length - 1;

		// Swap first and last elements and go further inside
		if (n < (lastIndex - n)) {
			int temp = A[n];
			A[n] = A[lastIndex - n];
			A[lastIndex - n] = temp;
			reverseArray(A, n + 1);

		}

	}

	//n should start from 0
	public static int ArraySum(int[] A, int n) {
		
		int lastIndex = A.length - 1;

		if (n == (lastIndex)) {
			return A[lastIndex];

		} else
			return A[n] + ArraySum(A, n + 1);

	}

}
