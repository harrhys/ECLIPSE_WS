package com.farbig.practice.dsa.algorithms.dp;

import java.util.Vector;

import com.farbig.practice.util.MathUtil;
import com.farbig.practice.util.PrintUtil;

public class MinAdjacentDiff {

	public static void main(String[] args) {
		
		PrintUtil.LEVEL=0;
		
		double a =  Math.pow(2, 32);
		System.out.println(a);
		
		System.out.println((Integer.MAX_VALUE));
		System.out.println((Integer.MAX_VALUE>>>1));
		System.out.println((~Integer.MIN_VALUE));
		System.out.println(Integer.bitCount(Integer.MAX_VALUE>>>1));
		int k = 3;

		int arr[] = {16, 21, 24, 27, 36, 41 };

		System.out.println(minimumAdjacentDifference(arr, arr.length, k)); // GeeksForGeeks
		
		System.out.println(findMinimumAdajacentDistance(arr, k));

	}

	// removes K elements from Array a of size n
	// The max difference between the adjacent elements should be minimum
	static int minimumAdjacentDifference(int a[], int n, int k) {

		// Intialising the minimum difference
		int minDiff = Integer.MAX_VALUE;
		
		// Traversing over subsets in iterative manner
		for (int i = 0; i < (1 << n); i++) {

			// Number of elements to be taken in the subset
			// ON bits of i represent elements not to be removed
			int cnt = Integer.bitCount(i);

			// If the removed set is of size k
			if (cnt == n - k) {

				// Creating the new array after removing elements
				Vector<Integer> temp = new Vector<Integer>();
				for (int j = 0; j < n; j++) {
					if ((i & 1 << j) != 0)
						temp.add(a[j]);
				}

				// Maximum difference of adjacent elements of remaining array
				int maxDiff = Integer.MIN_VALUE;
				for (int j = 0; j < temp.size() - 1; j++) {
					maxDiff = Math.max(maxDiff, temp.get(j + 1) - temp.get(j));
				}
				minDiff = Math.min(minDiff, maxDiff);
				
			}
		}
		return minDiff;
	}

	// My version of the above method
	static int findMinimumAdajacentDistance(int[] input, int k) {

		
		// each element can be either present or absent 1 or 0
		// so 2 combinations for each element..for n element 2 power n
		
		int totalCombinations = (int) Math.pow(2, input.length);
		
		int minDiff = Integer.MAX_VALUE; 
		
		for (int i = 0; i < totalCombinations; i++) {

			// no of 1's in the binary representation
			int bitCount = Integer.bitCount(i);

			// if k elements to be removed then n-k elements will be present
			if (bitCount == input.length - k) {
				
				int[] subArray = MathUtil.binarySubSet(i, input);
				
				int maxDiff =0;
				
				for (int j = 0; j < subArray.length-1; j++) {
					
					maxDiff = Math.max(maxDiff, subArray[j+1]-subArray[j]);
				}
				
				minDiff = Math.min(minDiff, maxDiff);
				
				if(minDiff==maxDiff)
				PrintUtil.printArray(0, subArray);
					
			}
		}

		return minDiff;
	}
}
