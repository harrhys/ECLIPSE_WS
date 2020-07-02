package com.farbig.practice.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ThreadWNSI {

	public static void main(String ar[]) throws InterruptedException {

		int max = 5;

		MyObject obj = new MyObject();

		for (int i = 0; i < max; i++) {
			Thread p = new Thread(new MyProducer(obj));
			p.start();
		}

		for (int i = 0; i < max; i++) {
			Thread c = new Thread(new MyConsumer(obj));
			c.start();
		}

		Thread s1 = new Thread(new MySleeper(obj));
		Thread s2 = new Thread(new MySleeper(obj));

		s1.start();
		s2.start();

		Thread.sleep(2000);

		obj.id = s1.getId();
		obj.myInterrupt();
		obj.id = s2.getId();
		obj.myInterrupt();

		// ThreadUtil.listMyThreads();

	}

}

class MyObject {

	long id;

	MyThread thread;

	Map<Long, MyThread> waitMap = Collections
			.synchronizedMap(new MyMap<Long, MyThread>());

	List<MyThread> waitList = new ArrayList<MyThread>();

	public void mywait() {

		Long tid = Thread.currentThread().getId();
		MyThread t = new MyThread();
		t.id = tid;
		t.state = "waiting";
		waitMap.put(tid, t);
		waitList.add(t);
		System.out.println(tid + " waiting" + waitMap);

		while (waitMap.get(tid) != null) {

			if (waitMap.get(tid).state.equals("notified")) {
				System.out.println(tid + " object Notified \n" + waitMap);
				waitMap.remove(tid);
				break;
			} else if (waitMap.get(tid).state.equals("interrupted")) {
				System.out.println(tid + " object Interrupted \n" + waitMap);
				waitMap.remove(tid);
				break;
			} else {
				// System.out.print("");
			}
		}
	}

	public void mynotify() {
		Long id = Thread.currentThread().getId();
		if (waitList.size() > 0) {
			MyThread waitObj = waitList.remove(0);
			System.out.println(id + " notifying " + waitObj.id);
			waitObj.callerId = id;
			waitObj.state = "notified";
			waitMap.put(waitObj.id, waitObj);
			System.out.println(waitObj.id + "-" + waitMap);
		}
	}

	public void mysleep(long time) {

		Long id = Thread.currentThread().getId();
		long t1 = System.currentTimeMillis();
		MyThread t = new MyThread();
		t.id = id;
		t.state = "sleeping";
		waitMap.put(id, t);
		System.out.println(id + " sleeping for " + time + waitMap);
		while (waitMap.get(id) != null) {

			if (waitMap.get(id).state.equals("interrupted")) {

				waitMap.remove(id);
				System.out.println(id + " Interrupted \n" + waitMap);
				break;
			} else if (System.currentTimeMillis() - t1 > time) {
				System.out.println(id + " sleep over");
				waitMap.remove(id);
				break;
			} else {
				// just sleeping without any operation
			}
		}
	}

	public void myInterrupt() {

		if (waitMap.get(id) != null) {
			MyThread t = waitMap.get(id);
			t.state = "interrupted";
			waitMap.put(id, t);
			System.out.println(id + " is getting interrupted");
		} else {
			System.out.println(id + " object cant be interrupted");
		}
	}
}

class MyThread {

	Long id;

	Long callerId;

	String state;

	public String toString() {
		return String.format("%s-%s-%s", id, callerId, state);
	}

}

class MyMap<Long, MyThread> extends HashMap {

	public String toString() {

		StringBuffer buf = new StringBuffer("\nMyMap\n[");
		if (this.entrySet().size() > 0) {

			for (Iterator iterator = this.keySet().iterator(); iterator
					.hasNext();) {
				Long id = (Long) iterator.next();

				buf.append(this.get(id));
				if (iterator.hasNext())
					buf.append("\n");
			}
		} else {
			buf.append("null");
		}
		buf.append("]\n");

		return buf.toString();

	}
}

class MyProducer implements Runnable {

	MyObject obj;

	MyProducer(MyObject obj) {
		this.obj = obj;
	}

	public void run() {
		long t1 = System.currentTimeMillis();
		Random r = new Random();
		int time = r.nextInt(5000);
		obj.mysleep(time);
		System.out
				.println(Thread.currentThread().getId() + " created Producer");
		obj.mywait();
		System.out.println(Thread.currentThread().getId() + " notifited ");

	}
}

class MyConsumer implements Runnable {

	MyObject obj;

	MyConsumer(MyObject obj) {
		this.obj = obj;
	}

	public void run() {

		long t1 = System.currentTimeMillis();
		Random r = new Random();
		int time = r.nextInt(10000);
		obj.mysleep(5000);
		System.out.println(Thread.currentThread().getId()
				+ " created consumer ");
		obj.mynotify();
	}
}

class MySleeper implements Runnable {

	MyObject obj;

	MySleeper(MyObject obj) {
		this.obj = obj;
	}

	public void run() {
		obj.id = Thread.currentThread().getId();
		System.out
				.println(Thread.currentThread().getId() + " created Sleeper ");
		obj.mysleep(5000);

	}

}
