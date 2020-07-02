package com.farbig.practice.dsa.algorithms.recursion;

import java.util.Scanner;
import java.util.Stack;

public class TowerOfHanoiUsingStacks {
	public static int N;
	/* Creating Stack array */
	public static Stack<Integer>[] tower = new Stack[4];

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of disks");
		int num = scan.nextInt();
		N = num;
		tohStack(num);
	}

	/* Function to push disks into stack */
	public static void toh(int n) {
		for (int d = n; d > 0; d--)
			tower[1].push(d);
		display();
		move(n, 1, 2, 3);
	}

	/* Recursive Function to move disks */
	public static void move(int n, int a, int b, int c) {
		if (n > 0) {
			move(n - 1, a, c, b);
			int d = tower[a].pop();
			tower[c].push(d);
			display();
			move(n - 1, b, a, c);
		}
	}

	/* Function to push disks into stack */
	public static void tohStack(int n) {
		
		initialiseStacks(n);
		display();
		long t1 = System.currentTimeMillis();
		moveNFromAToC(n, tower[1], tower[2], tower[3]);
		long t2 = System.currentTimeMillis();
		System.out.println("Time taken for moving " + n + " Disks--"
				+ ((t2 - t1)));

	}
	/* Function to push disks into stack */
	public static void initialiseStacks(int n) {
		tower[1] = new Stack<Integer>();
		tower[2] = new Stack<Integer>();
		tower[3] = new Stack<Integer>();
		for (int d = n; d > 0; d--)
			tower[1].push(d);
	}

	/* Function to push disks into stack */
	public static void tohStackAll(int n) {
		for (int d = n; d > 0; d--)
			tower[1].push(d);
		display();
		long t1 = System.currentTimeMillis();

		for (int i = 1; i <= n; i++) {
			initialiseStacks(i);
			moveNFromAToC(i, tower[1], tower[2], tower[3]);
			long t2 = System.currentTimeMillis();
			System.out.println("Time taken for moving " + i + " Disks--"
					+ ((t2 - t1)));
			t1 = t2;
		}

	}

	/* Recursive Function to move disks */
	public static void moveNFromAToB(int n, Stack<Integer> a, Stack<Integer> b,
			Stack<Integer> c) {
		if (n > 0) {
			moveNFromAToB(n - 1, a, c, b);
			b.push(a.pop());
			display();
			moveNFromAToB(n - 1, c, b, a);
		}
	}

	/* Recursive Function to move disks */
	public static void moveNFromAToC(int n, Stack<Integer> a, Stack<Integer> b,
			Stack<Integer> c) {
		if (n > 0) {
			moveNFromAToC(n - 1, a, c, b);
			c.push(a.pop());
			display();
			moveNFromAToC(n - 1, b, a, c);
		}
	}

	/* Function to display */
	public static void display() {
		System.out.println("  A  |  B  |  C");
		System.out.println("---------------");
		for (int i = N - 1; i >= 0; i--) {
			String d1 = " ", d2 = " ", d3 = " ";
			try {
				d1 = String.valueOf(tower[1].get(i));
			} catch (Exception e) {
			}
			try {
				d2 = String.valueOf(tower[2].get(i));
			} catch (Exception e) {
			}
			try {
				d3 = String.valueOf(tower[3].get(i));
			} catch (Exception e) {
			}
			System.out.println("  " + d1 + "  |  " + d2 + "  |  " + d3);
		}
		System.out.println("\n");
	}
}