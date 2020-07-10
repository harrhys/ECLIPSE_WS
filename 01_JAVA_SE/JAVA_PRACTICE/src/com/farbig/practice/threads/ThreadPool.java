package com.farbig.practice.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	// FixedThreadPool
	// CachedThreadPool
	// ScheduledThreadPool
	// SingleThreadExecutor

	public static void main(String[] args) throws InterruptedException {

		// System Configuration
		System.out.println(Runtime.getRuntime().availableProcessors());
		System.out.println(Runtime.getRuntime().maxMemory());
		System.out.println(Runtime.getRuntime().freeMemory());
		System.out.println(Runtime.getRuntime().totalMemory());

		// testFixedThreadPool();
		// testCachedThreadPool();
		testDelayedThreadPool();
		// testSingleThreadExecutor();
		
		ThreadFactory f = new ThreadFactory() {
			
			public Thread newThread(Runnable arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};  
	}

	static void testFixedThreadPool() {

		ExecutorService service = Executors.newFixedThreadPool(5);
		for (int i = 1; i <= 10; i++) {
			service.execute(new Task());
		}
		service.shutdown();
		// System.out.println(service.isShutdown());
	}

	static void testCachedThreadPool() {
		ExecutorService service = Executors.newCachedThreadPool();

		for (int i = 1; i <= 10; i++) {
			service.execute(new Task());
		}
		service.shutdown();
		// System.out.println(service.isShutdown());
	}

	static void testDelayedThreadPool() throws InterruptedException {
		
		ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
		Task delayedTask = new Task();
		delayedTask.name= "Delayed Task";
		Task fixedRateTask = new Task();
		fixedRateTask.name= "Fixed Rate Task";
		Task fixedDelayTask = new Task();
		fixedDelayTask.name= "Fixed Delay Task";
		service.schedule(delayedTask, 5, TimeUnit.SECONDS);
		service.scheduleAtFixedRate(fixedRateTask, 0, 5, TimeUnit.SECONDS);
		service.scheduleWithFixedDelay(fixedDelayTask, 0, 5, TimeUnit.SECONDS);
		Thread.sleep(30000);
		service.shutdown();
		System.out.println(service.isShutdown());
	}

	static void testSingleThreadExecutor() {
		ExecutorService service = Executors.newSingleThreadExecutor();

		for (int i = 1; i <= 10; i++) {
			service.execute(new Task());
		}
		service.shutdown();
	}

}

class Task implements Runnable {

	static int counter = 1;
	
	String name;

	public void run() {
		
		System.out.println(Thread.currentThread().getName()
				+ " Performing the " + name +" Task " + counter++);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
