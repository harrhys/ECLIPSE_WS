package com.farbig.practice.threads;

import com.farbig.practice.util.PrintUtil;


public class ThreadSafety {
	
	public static void main(String args[]) throws InterruptedException
	{
		Thread t = new Thread(new Runnable(){

			public void run() {
				/*try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				PrintUtil.println(2,Thread.currentThread().getName());
				
			}
			
		});
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		//t.join();
		//t.join(1000);
		Thread.yield();
		PrintUtil.println(2,Thread.currentThread().getName());
	}
	
	
}
