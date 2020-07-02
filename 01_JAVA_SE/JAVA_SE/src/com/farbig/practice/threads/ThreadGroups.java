package com.farbig.practice.threads;

import com.farbig.practice.util.ThreadUtil;

public class ThreadGroups {

	public static void main(String[] args) throws InterruptedException {

		ThreadUtil.logThreadsOnce(false);
		ThreadUtil.sleep(2000);

		ThreadGroup myThreadGroup = new ThreadGroup("MyThreads");
		myThreadGroup.setMaxPriority(7);

		Thread myThread = null;
		for (int i = 0; i < 5; i++) {
			myThread = new Thread(new GroupTask());
			myThread.start();
		}
		for (int i = 0; i < 5; i++) {
			myThread = new Thread(myThreadGroup, new GroupTask());

			myThread.start();
		}

		// system thread group
		// listThreadsInGroup(myThreadGroup.getParent().getParent());
		// main thread group
		// listThreadsInGroup(myThreadGroup.getParent());
		// mythread group
		// listThreadsInGroup(myThreadGroup);

		// Thread.currentThread().getThreadGroup().getParent().list();

		ThreadUtil.logThreadsOnce(true);

		ThreadUtil.sleep(2000);

		ThreadUtil.logThreadsOnce(true);
		
		//ThreadUtil.sleep(2000);

	}

	static void listThreadsInGroup(ThreadGroup tg) {

		System.out.println(tg.getName() + " thread group count - "
				+ tg.activeCount() + " " + tg.activeGroupCount() + "\n");
		Thread ta[] = new Thread[tg.activeCount()];
		tg.enumerate(ta);

		for (int i = 0; i < ta.length; i++) {
			Thread t = ta[i];
			System.out.println(t + " - " + t.getId());
			// System.out.println(t.getThreadGroup().getName()+" : "+t.getName()+" "+t.getId());
		}
		System.out.println();
		// tg.list();

	}

	

}

class GroupTask implements Runnable {

	public void run() {
		try {
			ThreadUtil.sleep(1000);
		} catch (Exception e) {
			System.err.println("Error in running "+Thread.currentThread().getName());
			//e.printStackTrace();
		}

	}

}
