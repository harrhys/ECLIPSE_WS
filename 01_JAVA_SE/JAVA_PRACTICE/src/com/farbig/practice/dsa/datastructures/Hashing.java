package com.farbig.practice.dsa.datastructures;
import java.util.Arrays;


public class Hashing {
	
	String[] theArray;
	int itemsInArray;
	
	Hashing(int size)
	{
		theArray = new String[size];
		Arrays.fill(theArray, "x");
		
	}
	
	public void hash(String[] stringsForArray, String[] theArray)
	{
		for (int i = 0; i < stringsForArray.length; i++) {
			
			String value = stringsForArray[i];
			
			int index = Integer.parseInt(value);
			
			theArray[index] = value;
		}
	}
	
	
	public void displayArray()
	{
		for (int i = 0; i < theArray.length; i++) {
			System.out.println(i + " ----- " + theArray[i]);
		}
	}

	public static void main(String[] args) {

		Hashing function =  new Hashing(100);
		
		String[] items = {"26","4","7"};
		
		function.hash(items, function.theArray);
		
		function.displayArray();
		
	}

}
