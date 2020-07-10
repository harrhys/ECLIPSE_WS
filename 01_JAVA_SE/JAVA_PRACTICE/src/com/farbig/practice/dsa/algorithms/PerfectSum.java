package com.farbig.practice.dsa.algorithms;

import java.util.HashMap;

import com.farbig.practice.util.MathUtil;
import com.farbig.practice.util.PrintUtil;

public class PerfectSum {

	public static void main(String... strings) {

		int[] input = { 10, 2, -2, -20, 10  };
		//	input  = {1,2,3,4,5};
		//	output = 1 5 , 2 4 , 1 2 3

		int targetSum = -10;
		
		findSubsets(input, targetSum);
		
		findSubArrays(input, targetSum);
		
	}

	// Function to find the subsets with sum K
	static void findSubsets(int[] input, int targetSum) {

		// total no. of possible subsets - 2 power n
		int totalSubSets = (int) Math.pow(2, input.length);

		// Loop all subsets and find the sum of each subset
		for (int subsetNum = 1; subsetNum < totalSubSets; subsetNum++) {

			// binary representation of n
			int subArray[] = MathUtil.binarySubSet(subsetNum, input);

			int sum = 0;

			// Calculate the sum of this subset
			for (int j = 0; j < subArray.length; j++)
				sum = sum + subArray[j];

			// Check whether sum is equal to target
			// if it is equal, then print the subset
			if (sum == targetSum)
				PrintUtil.printArray(0, subArray);
		}
	}

	// Function to find the subarrays with sum K
	static int findSubArrays(int input[], int targetSum) {
		
		// HashMap to store number of subarrays
		// starting from index zero having
		// particular value of sum.
		HashMap<Integer, Integer> prevSum = new HashMap<>();

		int res = 0;

		// Sum of elements so far.
		int currSum = 0;

		//	int[] input = { 10, 2, -2, -20, 10  }; targetSum=-10

		for (int i = 0; i < input.length; i++) {

			// Add current element to sum so far.
			currSum += input[i];

			// If currsum is equal to desired sum, 
			// then a new subarray is found.
			// So increase count of subarrays.
			if (currSum == targetSum)
				res++;

			// currsum exceeds given sum by currsum - sum.
			// Find number of subarrays having this sum and
			// exclude those subarrays from currsum by
			// increasing count by same amount.
			if (prevSum.containsKey(currSum - targetSum))
				res += prevSum.get(currSum - targetSum);

			// Add currsum value to count of different values of sum.
			Integer count = prevSum.get(currSum);
			
			if (count == null)
				prevSum.put(currSum, 1);
			else
				prevSum.put(currSum, count + 1);

		}
		System.out.println(res);
		return res;
	}

}
