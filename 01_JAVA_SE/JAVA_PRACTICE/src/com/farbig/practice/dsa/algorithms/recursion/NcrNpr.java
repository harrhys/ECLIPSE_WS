package com.farbig.practice.dsa.algorithms.recursion;

import com.farbig.practice.util.PrintUtil;

public class NcrNpr {
	
	static boolean isRecursiveCall;

	public static void main(String[] args) {

		PrintUtil.println(2, fact(7));
		PrintUtil.println(2, ncrSum(7));
		PrintUtil.println(2, ncrSum(7));
	}
	//DP - Tabulation
	public static int fact(int n) {

		int[] f = new int[n + 1];
		f[0] = 1;
		for (int i = 1; i < f.length; i++) {
			f[i] = i * f[i - 1];
		}
		return f[n];
	}

	static int ncr(int n, int r) {
		
		int ncr = fact(n) / (fact(r) * fact(n - r));
		PrintUtil.println(0, n+"C" + r + " is " + ncr);
		return ncr;
	}

	static int ncp(int n, int p) {
		
		int ncp = fact(n) /  fact(n - p);
		return ncp;
	}

	static int ncrSum(int n) {
		
		return ncrSum(n, 0, true);
	}

	public static int ncrSum(int n, int r, boolean isFirstCall) {
		
		
		int ncrSum=0;
		if (n >= r) {
			ncrSum =  ncr(n,r) + ncrSum(n, r + 1, false);
		}
		if(isFirstCall) {
			PrintUtil.println(2, "nCr Sum is "+ ncrSum);
		}
		return ncrSum;
	}

}
