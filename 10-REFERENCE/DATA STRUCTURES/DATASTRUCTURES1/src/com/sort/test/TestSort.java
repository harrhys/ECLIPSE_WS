package com.sort.test;

import com.sort.BubbleSort;
import com.sort.InsertionSort;
import com.sort.SelectionSort;

public class TestSort {
	
	public static boolean isdatasorted = true;
	
	public static boolean display = true;
	
	public static boolean detaildisplay = true;
	
	static int factor = 1;
	
	//static int sample[] = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
	
	 //static int sample[] = { 11, 18, 99, 44, 25, 67, 71,53,85,35 };
	
	 //static int sample[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
	 
	static int sample[] = { 1, 2, 3, 4 };

	static int a[] =  new int[sample.length*factor];
	static int b[] =  new int[sample.length*factor];
	static int c[] =  new int[sample.length*factor];
	
	public static void testSort() {
		
		if(isdatasorted)
		
		for (int i = 0; i < sample.length*factor; i++) {

			a[i]=i;
			b[i]=i;
			c[i]=i;
			
		}
		else
		for (int i = 1; i <= factor; i++) {

			System.arraycopy(sample, 0, a, (i-1)*sample.length, sample.length);
			System.arraycopy(sample, 0, b, (i-1)*sample.length, sample.length);
			System.arraycopy(sample, 0, c, (i-1)*sample.length, sample.length);
			
		}
		
		BubbleSort.sort(a);
		
		System.out.println("TOTAL TIME TAKEN :" + BubbleSort.t+"\n");
		
		SelectionSort.sort(b);
				
		System.out.println("TOTAL TIME TAKEN :" + SelectionSort.t+"\n");
		
		InsertionSort.sort(c);
		
		System.out.println("TOTAL TIME TAKEN :" + InsertionSort.t+"\n");
		
		
		
		System.out.println("SORT \t\t\tCOMPARISIONS\t\tCOPIES\t\tTIME\n");
		System.out.println(BubbleSort.SORT_NAME+"\t\t" + BubbleSort.comps+"\t\t"+BubbleSort.copies+"\t\t"+BubbleSort.t+"\n");
		System.out.println(SelectionSort.SORT_NAME+"\t\t" + SelectionSort.comps+"\t\t"+SelectionSort.copies+"\t\t"+SelectionSort.t+"\n");
		System.out.println(InsertionSort.SORT_NAME+"\t\t" + InsertionSort.comps+"\t\t"+InsertionSort.copies+"\t\t"+InsertionSort.t+"\n");
	
	}
	
	public static void main(String a[])
	{
		for (int i = 0; i < 1; i++) {
			
			testSort();
		}
	}

}
