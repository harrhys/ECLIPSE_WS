package com.sort;

import com.sort.test.TestSort;

public class BubbleSort {
	
	public static String SORT_NAME = "BUBBLE SORT";
	
	public static int copies = 0;
	
	public static int comps = 0;
	
	public static Long t=0L;
	
	public static void sort(int []a) {
		
		System.out.println(SORT_NAME);
		
		display(a);
		System.out.println();

		Long t1 = System.currentTimeMillis();
		//display(a);

		for (int out = 0; out < a.length ; out++) {

			for (int in = 0; in < a.length-1; in++) {
				comps++;
				if (a[in] > a[in + 1]) {
					swap(a,in);
					
					display(a,TestSort.detaildisplay);
				}
			}
			
			
			
		}
		
		Long t2 = System.currentTimeMillis();
		t = t+t2-t1;
		
		System.out.println();
		display(a);
		System.out.println("TOTAL COMPARISIONS REQUIRED:" + comps);
		System.out.println("TOTAL COPIES REQUIRED:" + copies);

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
	
	static void swap(int []a, int i)
	{
		int temp = a[i];
		a[i] = a[i+1];
		a[i+1] = temp;
		copies++;
		copies++;
		copies++;
	}

}
