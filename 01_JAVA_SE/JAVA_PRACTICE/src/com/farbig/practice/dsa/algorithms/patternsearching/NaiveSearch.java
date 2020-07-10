package com.farbig.practice.dsa.algorithms.patternsearching;

//Java program for Naive Pattern Searching 
public class NaiveSearch {

	public static void search(String txt, String pat) {
		int M = pat.length();
		int N = txt.length();

		/* A loop to slide pat one by one */
		for (int i = 0; i <= N - M; i++) {

			int j;

			/*
			 * For current index i, check for pattern match
			 */
			for (j = 0; j < M; j++)
				if (txt.charAt(i + j) != pat.charAt(j))
					break;

			if (j == M) // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
				System.out.println("Pattern found at index " + i);
		}
	}

	/*
	 * A modified Naive Pettern Searching algorithn that is optimized for the cases
	 * when all characters of pattern are different
	 */
	static void optimisedSearch(String pat, String txt) {
		int M = pat.length();
		int N = txt.length();
		int i = 0;

		while (i <= N - M) {
			int j;

			/* For current index i, check for pattern match */
			for (j = 0; j < M; j++)
				if (txt.charAt(i + j) != pat.charAt(j))
					break;

			if (j == M) // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
			{
				System.out.println("Pattern found at index " + i);
				i = i + M;
			} 
			else if (j == 0) {
				i = i + 1;
			} 
			else {
				i = i + j; // slide the pattern by j
			}
		}
	}

	public static void main(String[] args) {
		
		String txt = "AABAACAADAABAAABAA";
		String pat = "AABA";
		search(txt, pat);
		optimisedSearch(txt, pat);
	}
}