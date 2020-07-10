package com.farbig.practice.threads;

import com.farbig.practice.util.ThreadUtil;

public class ThreadYield {
	public static void main(String[] args) {
		Thread producer = new YeildProducer("Producer");
		Thread consumer = new YeildConsumer("Consumer");

		producer.setPriority(Thread.MIN_PRIORITY); // Min Priority
		consumer.setPriority(Thread.MAX_PRIORITY); // Max Priority

		consumer.start();

		producer.start();
		
		System.out.println("exiting");
		
		ThreadUtil.logThreads(true);
	}
}

class YeildProducer extends Thread {
	
	public YeildProducer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public void run() {
		for (int i = 0; i < 30000; i++) {
			System.out.println("Producer : Produced Item " + i);
			 Thread.yield();
		}
	}
}

class YeildConsumer extends Thread {
	
	public YeildConsumer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public void run() {
		for (int i = 0; i < 30000; i++) {
			System.out.println("Consumer : Consumed Item " + i);
			Thread.yield();
		}
	}
}