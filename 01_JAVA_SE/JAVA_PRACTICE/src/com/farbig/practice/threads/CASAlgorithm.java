package com.farbig.practice.threads;

import java.util.concurrent.atomic.AtomicInteger;

import com.farbig.practice.util.ThreadUtil;

public class CASAlgorithm {

	public static void main(String[] args) throws InterruptedException {
		
		Incrementor inc = new Incrementor();
		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(inc);
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
		}
		ThreadUtil.sleep(100);
		System.out.println(inc.wrongCounter);
		System.out.println(inc.counter);
		System.out.println(inc.newCounter);

	}
}

class Incrementor implements Runnable {

	long counter;
	
	long wrongCounter;
	
	AtomicInteger newCounter=new AtomicInteger();

	public void run() {
		
		long id = Thread.currentThread().getId();
		++wrongCounter;
		incrementAndGet();
		newCounter.incrementAndGet();
		//System.out.println(id + " Wrong Counter Value-"+ ++wrongCounter);;
		
		//System.out.println(id + " Counter Value-"+ counter);;

	}

	public final long incrementAndGet() {
		for (;;) {
			long expectedValue = counter;
//			try {
//				Thread.sleep(20);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			long next = expectedValue + 1;
			if (compareAndSet(expectedValue, next))
				return next;
		}
	}

	boolean compareAndSet(long expectedValue, long newValue) {

		if (expectedValue == counter) {
			counter = newValue;
			//System.out.println(counter+" "+current+" "+next);
			return true;
		}
		else
		{
			//System.out.println(counter+" "+current+" "+next);
			return false;
		}

		
	}

}
