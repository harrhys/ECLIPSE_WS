package com.farbig.practice.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.farbig.practice.util.ThreadUtil;

public class ThreadBlockingQueue extends Thread {

	public static void main(String arg[]) throws Exception {

		ThreadBlockingQueue logger = ThreadBlockingQueue.getLogger();
		logger.log("test");
		ThreadUtil.logThreads(false);
		ThreadUtil.sleep(2000);
		logger.shutDown();
		ThreadUtil.sleep(2000);
		ThreadUtil.shutdown();
	}

	private static final String SHUTDOWN_REQ = "SHUTDOWN";
	private volatile boolean shuttingDown, loggerTerminated;

	private static final ThreadBlockingQueue instance = new ThreadBlockingQueue(
			"LoggerThread");

	private BlockingQueue<String> itemsToLog = new ArrayBlockingQueue<String>(
			100);
	
	private ThreadBlockingQueue(String arg0) {
		super(arg0);
		start();
	}

	public static ThreadBlockingQueue getLogger() {
		return instance;
	}

	public void shutDown() throws InterruptedException {
		shuttingDown = true;
		itemsToLog.put(SHUTDOWN_REQ);
	}

	public void log(String str) {
		if (shuttingDown || loggerTerminated)
			return;
		try {
			itemsToLog.put(str);
		} catch (InterruptedException iex) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Unexpected interruption");
		}
	}

	public void run() {
		try {
			String item;
			while ((item = itemsToLog.take()) != SHUTDOWN_REQ) {
				System.out.println(item);
			}
			System.err.println("Logger is Shutdown");

		} catch (InterruptedException iex) {
		} finally {
			loggerTerminated = true;
		}
	}

}