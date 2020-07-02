package com.farbig.practice.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ThreadUtil {

	private static LoggerTask logger;

	public static void main(String[] args) {
		
		Thread t  = new Thread();
		
		logThreads(false);

		setInterval(1000);

		sleep(3000);

		logThreadsOnce(false);

	}

	public static void sleep(long timeInMillis) {
		long t1 = System.currentTimeMillis();
		while (System.currentTimeMillis() - t1 < timeInMillis) {
		}
	}

	public static void shutdown() {
		logger.shutdown();
	}

	public static void setInterval(long interval) {
		logger.setInterval(interval);
	}

	public static void logThreads(boolean logAll) {

		if (logAll) {
			logger = new LoggerTask(true);
			Thread t = new Thread(logger, "AllThreads Logger");
			t.setDaemon(true);
			t.start();
		} else {
			logger = new LoggerTask(false);
			Thread t = new Thread(logger, "UserThreads Logger");
			t.setDaemon(true);
			t.start();
		}
	}

	public static void logThreadsOnce(boolean logAll) {

		if (logAll) {
			logger = new LoggerTask(true);
			new Thread(logger, "AllThreads Logger").start();
		} else {
			logger = new LoggerTask(false);
			new Thread(logger, "UserThreads Logger").start();
		}
		sleep(1000);
		logger.shutdown();
	}
}

class LoggerTask implements Runnable {

	boolean logAll;

	public LoggerTask(boolean logAll) {
		super();
		this.logAll = logAll;
	}

	private volatile boolean shuttingDown;

	private long logInterval = 1000;

	public void shutdown() {
		System.err.println("Thread Logger is shutdown");
		shuttingDown = true;
	}

	public void setInterval(long interval) {
		logInterval = interval;
	}

	public void run() {

		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		List<Thread> threads = new ArrayList<Thread>();

		if (logAll)
			for (Thread t : threadSet) {
				threads.add(t);
			}
		else
			for (Thread t : threadSet) {
				// checking for system and main threads
				if (t.getThreadGroup().getName() != null && !t.getThreadGroup().getName().contains("system"))
					threads.add(t);
			}

		logThreads(threads);
	}

	private synchronized void logThreads(List<Thread> threads) {

		Collections.sort(threads, new ThreadComparator());
		int activeCount = threads.size();
		do {
			try {
				activeCount = 0;
				System.out.printf("%-5s %-15s %-20s %-15s  %-10s %s\n", "ID", "GROUP", "NAME", "STATE", "PRIORITY",
						"TYPE");
				System.out.println("--------------------------------------------------------------------------------");
				for (int i = 0; i < threads.size(); i++) {
					Thread t = threads.get(i);
					String name = t.getName();
					long id = t.getId();
					String group = null;
					if (t != null && t.getThreadGroup() != null)
						group = t.getThreadGroup().getName();
					Thread.State state = t.getState();
					int priority = t.getPriority();

					String type = t.isDaemon() ? "Daemon" : "User";

					System.out.printf("%-5d %-15s %-20s %-15s  %-10d %s\n", id, group, name, state, priority, type);

					if ((t.getName().equals("AllThreads Logger") && !shuttingDown)
							|| (t.getName().equals("UserThreads Logger") && !shuttingDown)
							|| (!t.getName().equals("AllThreads Logger") && !state.equals(Thread.State.TERMINATED))
							|| (!t.getName().equals("UserThreads Logger") && !state.equals(Thread.State.TERMINATED))) {

						++activeCount;
					}
				}
				System.out.println("Active Thread count=" + activeCount);
				System.out.println();
				ThreadUtil.sleep(logInterval);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (!shuttingDown && activeCount != 0);
	}
}

class ThreadComparator implements Comparator<Thread> {

	public int compare(Thread t1, Thread t2) {
		if (t1.getId() > t2.getId())
			return 1;
		else if (t1.getId() < t2.getId())
			return -1;
		else
			return 0;
	}
}
