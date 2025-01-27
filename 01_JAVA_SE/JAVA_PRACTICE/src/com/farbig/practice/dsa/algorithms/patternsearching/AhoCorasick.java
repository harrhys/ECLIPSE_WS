package com.farbig.practice.dsa.algorithms.patternsearching;

import java.util.LinkedList;
import java.util.Queue;

public class AhoCorasick {

	// Max number of states in the matching machine.
	// Should be equal to the sum of the length of all keywords.
	int MAXS = 500;

	// Maximum number of characters in input alphabet
	int MAXC = 26;

	// OUTPUT FUNCTION IS IMPLEMENTED USING out[]
	// Bit i in this mask is one if the word with index i
	// appears when the machine enters this state.
	int out[] = new int[MAXS];

	// FAILURE FUNCTION IS IMPLEMENTED USING f[]
	int f[] = new int[MAXS];

	// GOTO FUNCTION (OR TRIE) IS IMPLEMENTED USING g[][]
	int g[][]= new int [MAXS][MAXC];

	// Builds the string matching machine.
	// arr - array of words. The index of each keyword is important:
	// "out[state] & (1 << i)" is > 0 if we just found word[i]
	// in the text.
	// Returns the number of states that the built machine has.
	// States are numbered 0 up to the return value - 1, inclusive.
	int buildMatchingMachine(String arr[], int k) 
	{ 
		// Initialize all values in output function as 0. 
		//memset(out, 0, out.length); 
	
		// Initialize all values in goto function as -1. 
		//memset(g, -1, g.length); 
	
		// Initially, we just have the 0 state 
		int states = 1; 
	
		// Construct values for goto function, i.e., fill g[][] 
		// This is same as building a Trie for arr[] 
		for (int i = 0; i < k; ++i) 
		{ 
			String word = arr[i]; 
			int currentState = 0; 
	
			// Insert all characters of current word in arr[] 
			for (int j = 0; j < word.length(); ++j) 
			{ 
				int ch = word.charAt(j) - 'a'; 
	
				// Allocate a new node (create a new state) if a 
				// node for ch doesn't exist. 
				if (g[currentState][ch] == -1) 
					g[currentState][ch] = states++; 
	
				currentState = g[currentState][ch]; 
			} 
	
			// Add current word in output function 
			out[currentState] |= (1 << i); 
		} 
	
		// For all characters which don't have an edge from 
		// root (or state 0) in Trie, add a goto edge to state 
		// 0 itself 
		for (int ch = 0; ch < MAXC; ++ch) 
			if (g[0][ch] == -1) 
				g[0][ch] = 0; 
	
		// Now, let's build the failure function 
	
		// Initialize values in fail function 
		//memset(f, -1, f.length); 
	
		// Failure function is computed in breadth first order 
		// using a queue 
		Queue<Integer> q = new LinkedList<Integer>(); 
		
	
		// Iterate over every possible input 
		for (int ch = 0; ch < MAXC; ++ch) 
		{ 
			// All nodes of depth 1 have failure function value 
			// as 0. For example, in above diagram we move to 0 
			// from states 1 and 3. 
			if (g[0][ch] != 0) 
			{ 
				f[g[0][ch]] = 0; 
				Integer temp = g[0][ch];
				q.add(temp); 
			} 
		} 
	
		// Now queue has states 1 and 3 
		while (q.size()>0) 
		{ 
			// Remove the front state from queue 
			int state = q.peek(); 
			q.remove(); 
	
			// For the removed state, find failure function for 
			// all those characters for which goto function is 
			// not defined. 
			for (int ch = 0; ch <= MAXC; ++ch) 
			{ 
				// If goto function is defined for character 'ch' 
				// and 'state' 
				if (g[state][ch] != -1) 
				{ 
					// Find failure state of removed state 
					int failure = f[state]; 
	
					// Find the deepest node labeled by proper 
					// suffix of string from root to current 
					// state. 
					while (g[failure][ch] == -1) 
						failure = f[failure]; 
	
					failure = g[failure][ch]; 
					f[g[state][ch]] = failure; 
	
					// Merge output values 
					out[g[state][ch]] |= out[failure]; 
	
					// Insert the next level node (of Trie) in Queue 
					Integer temp = g[state][ch];
					q.add(temp);
				} 
			} 
		} 
	
		return states; 
	}

	// Returns the next state the machine will transition to using goto
	// and failure functions.
	// currentState - The current state of the machine. Must be between
	// 0 and the number of states - 1, inclusive.
	// nextInput - The next character that enters into the machine.
	int findNextState(int currentState, char nextInput) {
		int answer = currentState;
		int ch = nextInput - 'a';

		// If goto is not defined, use failure function
		while (g[answer][ch] == -1)
			answer = f[answer];

		return g[answer][ch];
	}

	// This function finds all occurrences of all array words
	// in text.
	void searchWords(String arr[], int k, String text) 
	{ 
		// Preprocess patterns. 
		// Build machine with goto, failure and output functions 
		buildMatchingMachine(arr, k); 
	
		// Initialize current state 
		int currentState = 0; 
	
		// Traverse the text through the nuilt machine to find 
		// all occurrences of words in arr[] 
		for (int i = 0; i < text.length(); ++i) 
		{ 
			currentState = findNextState(currentState, text.charAt(i)); 
	
			// If match not found, move to next state 
			if (out[currentState] == 0) 
				continue; 
	
			// Match found, print all matching words of arr[] 
			// using output function. 
			for (int j = 0; j < k; ++j) 
			{ 
				if ((out[currentState] & (1 << j))==1) 
				{ 
					System.out.println("Word " + arr[j] + " appears from "+ (i - arr[j].length()) + 1 + " to " + i ); 
				} 
			} 
		} 
	}

	// Driver program to test above
	public static void main(String...strings ) {
		
		String arr[] = { "he", "she", "hers", "his" };
		String text = "ahishers";
		int k = arr.length / arr[0].length();
		
		AhoCorasick alg = new AhoCorasick();

		alg.searchWords(arr, k, text);

	}

}
