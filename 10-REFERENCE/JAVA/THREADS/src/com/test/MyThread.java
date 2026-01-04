package com.test;

public class MyThread extends Thread{
	
	static Integer counter= 0;

	public void run() {
		
		synchronized (this) {
			counter++;
			System.out.print("Thread"+counter);
		}
		
		
	}

}
