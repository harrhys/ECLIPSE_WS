package com.farbig.practice.dsa.algorithms.recursion;

import java.util.Stack;

public class TowerProblem {

	static Stack<Integer> s1 = new Stack<Integer>();
	static Stack<Integer> s2 = new Stack<Integer>();
	static Stack<Integer> s3 = new Stack<Integer>();

	public static void main(String[] args) {

		fillStack(3);
		int moves = moveElements(s1.size(), s1, s2, s3);
		System.out.println("Total Moves : " + moves);

	}

	private static void fillStack(int n) {

		System.out.println("Intial Stage");
		
		System.out.println("--------------------------------------");

		for (int i = n; i > 0; i--) {
			s1.add(i);
		}
		printStack(0);
		
		System.out.println("--------------------------------------");

	}

	private static void printStack(int n) {

		System.out.println("n=" + n);
		
		int size = s1.size()+s2.size()+s3.size();
		

		for (int i = size; i > 0; i--) {
			System.out.print("| ");
			System.out.print(s1.size() == 0 ? " " : (i > s1.size() ? " " : s1.get(i - 1)));
			System.out.print(" |\t\t | ");
			System.out.print(s2.size() == 0 ? " " : (i > s2.size() ? " " : s2.get(i - 1)));
			System.out.print(" |\t\t | ");
			System.out.print(s3.size() == 0 ? " " : (i > s3.size() ? " " : s3.get(i - 1)));
			System.out.println(" |");
		}
		System.out.println();

	}

	private static int moveElements(int n, Stack<Integer> s1, Stack<Integer> s2, Stack<Integer> s3) {
		
		int count = 0;

		if (n >0) {
			count += moveElements(n - 1, s1, s3, s2);
			s2.push(s1.pop());
			count++;
			System.out.println("count:" + count);
			printStack(n);
			count += moveElements(n - 1, s3, s2, s1);
		}
		

		return count;
	}

}
