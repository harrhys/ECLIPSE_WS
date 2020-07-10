package com.farbig.practice.threads;

public class ThreadLocalTest {

	public static void main(String[] args) {
		
		Thread t ;
		LocalTask task = new LocalTask();
		for (int i = 0; i < 5; i++) {
			t= new Thread(task, "task"+(i+1));
			t.start();
		}
	}
}

class LocalTask implements Runnable{
	
	ThreadLocal<String> localname = new ThreadLocal<String>();
	
	String name;

	public void run() {
		
		localname.set(Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("LocalTask Name "+ Thread.currentThread().getId()+ " Task Name- "+localname.get());
		
	}
	
}
