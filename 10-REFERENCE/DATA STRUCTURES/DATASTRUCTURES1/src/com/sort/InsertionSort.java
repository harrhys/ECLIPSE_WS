package com.sort;

import com.sort.test.TestSort;

public class InsertionSort {

	public static String SORT_NAME = "INSERTION SORT";
	
	public static int copies = 0;
	
	public static int comps = 0;

	public static Long t = 0L;

	public static void sort(int[] a) {

		System.out.println(SORT_NAME);

		display(a);
		System.out.println();
		copies = 0;
		t = 0L;

		Long t1 = System.currentTimeMillis();

		boolean copy = false;

		for (int out = 1; out < a.length; out++) {

			int temp = a[out];
			copies++;
			int pos = out - 1;
			copies++;
			while (validate(pos, temp, a)) {
				a[pos + 1] = a[pos];
				copies++;
				display(a, TestSort.detaildisplay);
				pos--;
				copy = true;
			}
			if (true) {
				a[pos + 1] = temp;
				display(a, TestSort.detaildisplay);
				copies++;
			}

		}
		Long t2 = System.currentTimeMillis();
		t = t + t2 - t1;

		System.out.println();
		display(a);
		System.out.println("TOTAL COMPARISIONS REQUIRED:" + comps);
		System.out.println("TOTAL COPIES REQUIRED:" + copies);

	}

	static boolean validate(int pos, int temp, int[] a) {
		boolean result = (pos >= 0 && temp < a[pos]);

		comps++;

		return result;
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
