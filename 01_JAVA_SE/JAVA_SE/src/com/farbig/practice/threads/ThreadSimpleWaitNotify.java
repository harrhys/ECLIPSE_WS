package com.farbig.practice.threads;

import java.util.Random;

import com.farbig.practice.util.PrintUtil;
import com.farbig.practice.util.ThreadUtil;

public class ThreadSimpleWaitNotify {

	public static void main(String[] args) throws InterruptedException {

		PrintUtil.LEVEL = 1;
		
		int MAX_PRODUCERS = 3;
		
		int MAX_CONSUMERS = 3;

		SimpleInventory inv = new SimpleInventory();
		
		for (int i = 1; i <= MAX_PRODUCERS; i++) {
			Thread producer = new Thread(new SimpleProducer(inv), "P"+i);
			producer.start();
		}
		for (int i = 1; i <= MAX_CONSUMERS; i++) {
			Thread consumer = new Thread(new SimpleConsumer(inv), "C"+i);
			consumer.start();
		}

		ThreadUtil.logThreads(false);
	}
}

class SimpleInventory {

	public void produce(String name) throws InterruptedException {

		long t1 = System.currentTimeMillis();
		while (System.currentTimeMillis() - t1 < 1500) {
			// some operation which is not blocking other threads
			// other threads accessing this block will in RUNNABLE state
		}

		synchronized (this) {

			long t2 = System.currentTimeMillis();
			while (System.currentTimeMillis() - t2 < 1500) {
				// some operation which is blocking the inventory object like db or http calls
				// other threads accessing this will be in BLOCKED state
			}
			System.out.println(name + " waiting");
			this.wait();
		}

		System.out.println(name + " notified after wait");
		long t2 = System.currentTimeMillis();
		while (System.currentTimeMillis() - t2 < 1500) {
			// some operation which is blocking the 
			// inventory object once notified like formating etc;
			// other threads accessing this will be in BLOCKED state
		}

	}

	public void consume(String name) {
		
		long t1 = System.currentTimeMillis();
		while (System.currentTimeMillis() - t1 < 1500) {

		}

		synchronized (this) {
			long t2 = System.currentTimeMillis();
			while (System.currentTimeMillis() - t2 < 1500) {

			}
			System.out.println(name + " notifying..");
			this.notify();
		}
		long t3 = System.currentTimeMillis();
		while (System.currentTimeMillis() - t3 < 1500) {
			// some operation which is blocking the 
			// inventory object once notified like formating etc;
			// other threads accessing this will be in BLOCKED state
		}


	}

	public synchronized void testNotifyAll(String name) {
		System.out.println(name + " notifying..");
		notifyAll();
	}
}

class SimpleProducer implements Runnable {

	SimpleInventory inv;
	ThreadLocal<String> name = new ThreadLocal<String>();

	SimpleProducer(SimpleInventory inv) {
		this.inv = inv;
	}

	public void run() {

		name.set(Thread.currentThread().getName());
		try {
			inv.produce(name.get());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class SimpleConsumer implements Runnable {

	SimpleInventory inv;

	ThreadLocal<String> name = new ThreadLocal<String>();

	SimpleConsumer(SimpleInventory inv) {
		this.inv = inv;
	}

	public void run() {
		
		name.set(Thread.currentThread().getName());

		try {
			Random r = new Random();
			int time = r.nextInt(10000);
			PrintUtil.println(0,name.get() + " sleeping for " + time);
			Thread.sleep(time);
			System.out.println(name.get() + " notifying the First waiting Thread");
			inv.consume(name.get());
			// Thread.sleep(3000);
			// System.out.println(name+ " notifying All the waiting Threads");
			// inv.testNotifyAll(name);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
