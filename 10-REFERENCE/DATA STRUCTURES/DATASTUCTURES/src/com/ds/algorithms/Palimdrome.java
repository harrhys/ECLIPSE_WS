package com.ds.algorithms;

public class Palimdrome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Palimdrome p  = new Palimdrome();
		
		int actual = 12345;
		
		Integer reverse = 0;
		
		reverse = p.getReverseNumber(actual, reverse);
		
		System.out.println(reverse);
		
		double rand = (Math.random()*.6)-.3;
		
		System.out.println(rand);
		
		
		

	}

	public int getReverseNumber(Integer x,  Integer rev) {
		
		
			int a = x / 10;

			int b = x % 10;
			
			rev = rev*10+b;
			
			if(a>0)
			{
				
			rev = getReverseNumber(a, rev);
			
			}
			
			return rev;
	}

}
