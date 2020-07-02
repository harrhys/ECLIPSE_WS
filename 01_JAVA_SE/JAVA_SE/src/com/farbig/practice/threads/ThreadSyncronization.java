package com.farbig.practice.threads;

import java.util.ArrayList;

// Inter Thread Communications
// Covers Syncronisation and Interruption
// With the below 3 ways both the threads run simultaneously
// 1st Method pm1.join
// 2nd Method make printDocument() syncronised
// 3rd Method Create syncronized block for printer object in run()

public class ThreadSyncronization {

	/**
	 * @param args
	 * @throws Exception
	 */
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("RabinKarp Application Started");

		Printer p = new Printer();
		PrintManager pm1 = new PrintManager(p, "English", 10);
		PrintManager pm2 = new PrintManager(p, "Hindi", 10);

		pm1.start();

		Thread.sleep(500);
		pm1.interrupt();
		// pm1.join();
		System.out.println("Joined with RabinKarp Thread");

		pm2.start();
		Thread.sleep(300);
		 pm2.interrupt();

		System.out.println("RabinKarp Application Ended");
	}
}

class Printer {

	ArrayList pms = new ArrayList();

	// void printDocument(String name, long pages) throws Exception {

	synchronized void printDocument(String name, long pages) throws Exception {

		for (long page = 1; page <= pages; page++) {
			
			
			Thread.sleep(100);

			System.out.println("Printing " + name + " page-" + page);

			if (Thread.interrupted()) {
				System.out.println("***********Printing Interrupted for "
						+ name + " ***********");
				throw new Exception();
			}

		}

		System.out.println("***********Printing Completed for " + name
				+ " ***********");
	}
}

class PrintManager extends Thread {

	Printer pntr;
	String doc;
	long pages;

	PrintManager(Printer p, String name, long pages) {
		pntr = p;
		doc = name;
		this.pages = pages;
	}

	@Override
	public void run() {

		try {
			/*
			 * synchronized (pntr) { pntr.printDocument(doc, pages); }
			 */
			pntr.printDocument(doc, pages);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
