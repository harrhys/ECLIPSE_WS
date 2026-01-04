package com.test;
import java.util.ArrayList;

public class TestThreads {

	public static void main(String[] args) {
		
		ArrayList<Thread> myThreads = new ArrayList<Thread>();
		
		for (int i = 1; i < 11; i++) {

			Thread myThread = new Thread(new MyThread());
			myThread.setPriority(11-i);
			myThreads.add(myThread);
		}
		
		for (int i = 0; i < 10; i++) {
			
			//myThreads.get(i).setPriority(i+1);
			System.out.println(myThreads.get(i).getPriority());
			myThreads.get(i).start();
		}

	}

}
