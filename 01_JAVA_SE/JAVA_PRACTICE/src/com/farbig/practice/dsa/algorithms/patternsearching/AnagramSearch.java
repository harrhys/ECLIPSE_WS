package com.farbig.practice.dsa.algorithms.patternsearching;
// Java program to search all anagrams 
// of a pattern in a text 
public class AnagramSearch 
{ 
	static final int MAX = 256; 
	
	// This function returns true if contents 
	// of arr1[] and arr2[] are same, otherwise 
	// false. 
	static boolean compare(char arr1[], char arr2[]) 
	{ 
		for (int i = 0; i < MAX; i++) 
			if (arr1[i] != arr2[i]) 
				return false; 
		return true; 
	} 

	// This function search for all permutations 
	// of pat[] in txt[] 
	static void search(String pat, String txt) 
	{ 
		int M = pat.length(); 
		int N = txt.length(); 

		// countP[]: Store count of all 
		// characters of pattern 
		// countTW[]: Store count of current 
		// window of text 
		char[] countP = new char[MAX]; 
		char[] countTW = new char[MAX]; 
		for (int i = 0; i < M; i++) 
		{ 
			(countP[pat.charAt(i)])++; 
			(countTW[txt.charAt(i)])++; 
		} 

		// Traverse through remaining characters 
		// of pattern 
		for (int i = M; i < N; i++) 
		{ 
			// Compare counts of current window 
			// of text with counts of pattern[] 
			if (compare(countP, countTW)) 
				System.out.println("Found at Index " + 
										(i - M)); 
			
			// Add current character to current 
			// window 
			(countTW[txt.charAt(i)])++; 

			// Remove the first character of previous 
			// window 
			countTW[txt.charAt(i-M)]--; 
		} 

		// Check for the last window in text 
		if (compare(countP, countTW)) 
			System.out.println("Found at Index " + 
									(N - M)); 
	} 

	/* Driver program to test above function */
	public static void main(String args[]) 
	{ 
		String txt = "BACDGABCDA"; 
		String pat = "ABCD"; 
		search(pat, txt); 
	} 
} 
// This code is contributed by Sumit Ghosh 
