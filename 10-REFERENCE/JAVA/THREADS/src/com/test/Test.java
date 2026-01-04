package com.test;
import java.util.Random;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		  Random randomGenerator = new Random();
          int sessionID = randomGenerator.nextInt(10000000);
          System.out.println(sessionID);


	}

}
