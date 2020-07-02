package com.farbig.practice.dsa.algorithms.dp;

import java.util.Random;

import com.farbig.practice.util.PrintUtil;

public class MaxSumPath {

	public static void main(String[] args) {

		PrintUtil.LEVEL = 2;

		int size = 4;
		m = size;
		n = size;
		createInputMatrix();
		long t1, t2;

		initialize();
		printInputMatrix(2);
		t1 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumIterative : Max Sum : " + maxSumIterative());
		t2 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumIterative : Max Sum Path : " + maxSumPath[m - 1][n - 1]);
		PrintUtil.println(2, "maxSumIterative : Call Counter : " + callCounter);
		PrintUtil.println(2, "maxSumIterative : Time taken : " + (t2 - t1));
		PrintUtil.println();

		initialize();
		memoize = false;
		printInputMatrix(0);
		t1 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumRecursive : Max Sum : " + maxSumRecursive(m - 1, n - 1));
		t2 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumRecursive : Max Sum Path : " + maxSumPath[m - 1][n - 1]);
		PrintUtil.println(2, "maxSumRecursive : Memoized: " + memoize);
		PrintUtil.println(2, "maxSumRecursive : Call Counter : " + callCounter);
		PrintUtil.println(2, "maxSumRecursive : Time taken : " + (t2 - t1));
		PrintUtil.println();

		initialize();
		memoize = true;
		printInputMatrix(0);
		t1 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumRecursive : Max Sum : " + maxSumRecursive(m - 1, n - 1));
		t2 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumRecursive : Max Sum Path : " + maxSumPath[m - 1][n - 1]);
		PrintUtil.println(2, "maxSumRecursive : Memoized: " + memoize);
		PrintUtil.println(2, "maxSumRecursive : Call Counter : " + callCounter);
		PrintUtil.println(2, "maxSumRecursive : Time taken : " + (t2 - t1));
		PrintUtil.println();

		initialize();
		printInputMatrix(0);
		t1 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumRecursiveBest : Max Sum : " + maxSumRecursiveBest(m - 1, n - 1));
		t2 = System.currentTimeMillis();
		PrintUtil.println(2, "maxSumRecursiveBest : Max Sum Path : " + maxSumPath[m - 1][n - 1]);
		PrintUtil.println(2, "maxSumRecursiveBest : Call Counter : " + callCounter);
		PrintUtil.println(2, "maxSumRecursiveBest : Time taken : " + (t2 - t1));
		PrintUtil.println();
	}

	public static void createInputMatrix() {

		if (m == 0 || n == 0) {
			createDefaultMatrix();
		} else {
			input = new int[m][n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					input[i][j] = new Random().nextInt(10);
				}
			}
		}
	}

	// default 3 x 3 matrix
	public static void createDefaultMatrix() {

		m = n = 3;
		input = new int[m][n];
		input[0][0] = 1;
		input[0][1] = 2;
		input[0][2] = 7;
		input[1][0] = 4;
		input[1][1] = 5;
		input[1][2] = 6;
		input[2][0] = 7;
		input[2][1] = 8;
		input[2][2] = 9;
	}

	public static void printInputMatrix(int level) {

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {

				PrintUtil.print(level, input[i][j] + " ");
			}
			PrintUtil.println(level);
		}
		PrintUtil.println(level);
	}

	public static void initialize() {

		maxSum = new int[m][n];
		maxSumPath = new String[m][n];
		visited = new boolean[m][n];
		callCounter = 0;
	}

	public static void printSumPaths() {

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				PrintUtil.println(1, "maxSum of [" + i + "," + j + "] :: " + maxSum[i][j] + " :: " + maxSumPath[i][j]);
			}
		}

	}

	// DP - Tabulation
	public static int maxSumIterative() {

		for (int i = 0; i < m; i++) {

			for (int j = 0; j < n; j++) {

				callCounter++;

				if (i == 0 && j == 0) {

					maxSum[i][j] = input[i][j];
					maxSumPath[i][j] = "" + input[i][j];

				} else if (i == 0) {

					maxSum[i][j] = maxSum[i][j - 1] + input[i][j];
					maxSumPath[i][j] = maxSumPath[i][j - 1] + "->" + input[i][j];

				} else if (j == 0) {

					maxSum[i][j] = maxSum[i - 1][j] + input[i][j];
					maxSumPath[i][j] = maxSumPath[i - 1][j] + "->" + input[i][j];

				} else if (i > 0 && j > 0) {

					if (maxSum[i][j - 1] > maxSum[i - 1][j]) {

						maxSum[i][j] = maxSum[i][j - 1] + input[i][j];
						maxSumPath[i][j] = maxSumPath[i][j - 1] + "->" + input[i][j];

					} else {

						maxSum[i][j] = maxSum[i - 1][j] + input[i][j];
						maxSumPath[i][j] = maxSumPath[i - 1][j] + "->" + input[i][j];
					}
				}

				PrintUtil.println(1, "maxSum of [" + i + "," + j + "] :: " + maxSum[i][j] + " :: " + maxSumPath[i][j]);
			}
		}
		return maxSum[m - 1][n - 1];
	}

	// DP - Memoization
	public static int maxSumRecursive(int i, int j) {

		callCounter++;

		PrintUtil.println(0, "finding maxSum of [" + i + "," + j + "]");

		if (memoize && visited[i][j]) {
			// just simply return the maxSum value..its already found!
			PrintUtil.println(0,
					"Mem:: maxSum of [" + i + "," + j + "] :: " + maxSum[i][j] + " :: " + maxSumPath[i][j]);

		} else {

			if (i > 0 && j > 0) {

				if (maxSumRecursive(i - 1, j) > maxSumRecursive(i, j - 1)) {

					maxSum[i][j] = maxSum[i - 1][j] + input[i][j];
					maxSumPath[i][j] = maxSumPath[i - 1][j] + "->" + input[i][j];

				} else {

					maxSum[i][j] = maxSum[i][j - 1] + input[i][j];
					maxSumPath[i][j] = maxSumPath[i][j - 1] + "->" + input[i][j];

				}

			} else if (i == 0 && j > 0) {

				maxSum[i][j] = input[i][j] + maxSumRecursive(i, j - 1);
				maxSumPath[i][j] = maxSumPath[i][j - 1] + "->" + input[i][j];

			} else if (j == 0 && i > 0) {

				maxSum[i][j] = input[i][j] + maxSumRecursive(i - 1, j);
				maxSumPath[i][j] = maxSumPath[i - 1][j] + "->" + input[i][j];

			} else if (i == 0 && j == 0) {
				// Base Condition where recursion stops
				maxSum[i][j] = input[i][j];
				maxSumPath[i][j] = "" + input[i][j];

			}

			if (visited[i][j]) {
				PrintUtil.println(0,
						"Rep:: maxSum of [" + i + "," + j + "] :: " + maxSum[i][j] + " :: " + maxSumPath[i][j]);
			} else {
				PrintUtil.println(1, "maxSum of [" + i + "," + j + "] :: " + maxSum[i][j] + " :: " + maxSumPath[i][j]);
				visited[i][j] = true;
			}
		}
		return maxSum[i][j];
	}

	// DP - Memoization + visited check before subproblem repeat call
	public static int maxSumRecursiveBest(int i, int j) {

		callCounter++;

		PrintUtil.println(0, "finding maxSum of [" + i + "," + j + "]");

		if (i > 0 && j > 0) {

			if ((visited[i - 1][j] ? maxSum[i - 1][j] : maxSumRecursiveBest(i - 1, j)) > (visited[i][j - 1]
					? maxSum[i][j - 1]
					: maxSumRecursiveBest(i, j - 1))) {

				maxSum[i][j] = maxSum[i - 1][j] + input[i][j];
				maxSumPath[i][j] = maxSumPath[i - 1][j] + "->" + input[i][j];

			} else {

				maxSum[i][j] = maxSum[i][j - 1] + input[i][j];
				maxSumPath[i][j] = maxSumPath[i][j - 1] + "->" + input[i][j];

			}

		} else if (i == 0 && j > 0) {

			maxSum[i][j] = input[i][j] + (visited[i][j - 1] ? maxSum[i][j - 1] : maxSumRecursiveBest(i, j - 1));
			maxSumPath[i][j] = maxSumPath[i][j - 1] + "->" + input[i][j];

		} else if (j == 0 && i > 0) {

			maxSum[i][j] = input[i][j] + (visited[i - 1][j] ? maxSum[i - 1][j] : maxSumRecursiveBest(i - 1, j));
			maxSumPath[i][j] = maxSumPath[i - 1][j] + "->" + input[i][j];

		} else if (i == 0 && j == 0) {
			// Base Condition where recursion stops
			maxSum[i][j] = input[i][j];
			maxSumPath[i][j] = "" + input[i][j];

		}

		PrintUtil.println(1, "maxSum of [" + i + "," + j + "] :: " + maxSum[i][j] + " :: " + maxSumPath[i][j]);

		visited[i][j] = true;

		return maxSum[i][j];
	}

	static int m, n;

	static int callCounter;

	static int input[][];

	static boolean visited[][];

	static int maxSum[][];

	static String maxSumPath[][];

	static boolean memoize = true;

}
