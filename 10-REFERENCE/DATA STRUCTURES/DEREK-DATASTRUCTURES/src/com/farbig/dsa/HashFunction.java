package com.farbig.dsa;
import java.util.Arrays;


public class HashFunction {
	
	String[] theArray;
	int arraySize;
	int itemsInArray;
	
	HashFunction(int size)
	{
		arraySize = size;
		theArray = new String[arraySize];
		Arrays.fill(theArray, "x");
		
	}
	
	public void hashFunction(String[] stringsForArray, String[] theArray)
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

		HashFunction hasFunc =  new HashFunction(100);
		
		String[] items = {"26","4","7"};
		
		hasFunc.hashFunction(items, hasFunc.theArray);
		
		hasFunc.displayArray();
		
	}

}
