package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Test  {
	
	
	
	
	public static void main(String args[])
	{
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		for(int i =1;i<11;i++)
		{
			Processor proc = new Processor(i);
			executor.submit(proc);
			
		}
		
		executor.shutdown();
		
		System.out.println("All tasks submitted");
		
		try {
			executor.awaitTermination(10,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("All tasks completed");
		
		
	}

}


class Processor extends Thread
{
	
	private int id;
	
	public Processor(int id)
	{
		this.id=id;
	}
	
	public void run()
	{
		System.out.println("starting "+id);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("completing "+id);
	}
}
