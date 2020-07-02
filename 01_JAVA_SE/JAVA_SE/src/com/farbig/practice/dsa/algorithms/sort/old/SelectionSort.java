package com.farbig.practice.dsa.algorithms.sort.old;


public class SelectionSort {

	public static String SORT_NAME = "SELECTION SORT";

	public static int copies = 0;

	public static int comps = 0;

	public static Long t = 0L;

	public static void sort(int[] a) {

		System.out.println(SORT_NAME);

		display(a);
		copies = 0;
		comps = 0;
		t = 0L;

		Long t1 = System.currentTimeMillis();

		int min = 0;
		// display(a);

		for (int out = 0; out < a.length - 1; out++) {

			min = out;
			copies++;

			for (int in = out + 1; in < a.length; in++) {

				comps++;

				if (a[min] > a[in]) {
					min = in;
					copies++;
				}
			}
			if (true) {
				swap(a, out, min);
				display(a, TestSort.detaildisplay);
			}

		}

		Long t2 = System.currentTimeMillis();
		t = t + t2 - t1;

		display(a);
		System.out.println("TOTAL COMPARISIONS REQUIRED:" + comps);
		System.out.println("TOTAL COPIES REQUIRED:" + copies);

	}

	static void swap(int[] a, int i, int j) {
		System.out.println(i);
		System.out.println(j);
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		copies++;
		copies++;
		copies++;
	}

	static void display(int[] a) {

		if (TestSort.display) {

			for (int i = 0; i < a.length; i++) {
				System.out.print(a[i] + ",");
			}
			System.out.println();

		}
	}

	static void display(int[] a, boolean display) {

		if (display) {
			display(a);
		}
	}

}
