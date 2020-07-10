package com.farbig.practice.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.farbig.practice.util.PrintUtil;
import com.farbig.practice.util.ThreadUtil;

public class ThreadWaitNotify {

	public static void main(String[] args) throws InterruptedException {

		PrintUtil.LEVEL = 1;

		Inventory inv = new Inventory();

		inv.runSize = 5;
		inv.maxStockSize = 2;
		inv.maxPTime = 1000;
		inv.maxCTime = 5000;

		for (int i = 1; i <= 3; i++) {
			Thread producer = new Thread(new Producer(inv), "P" + i);
			producer.start();
		}
		for (int i = 1; i <= 3; i++) {
			Thread consumer = new Thread(new Consumer(inv), "C" + i);
			consumer.start();
		}
		
		
		  ThreadUtil.sleep(2000);
		  
		  ThreadUtil.logThreads(false);
		 

		for (Iterator<Product> iterator = inv.sales.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			System.out.println(
					product.getID() + " " + product.getName() + "  " + product.getpName() + "  " + product.getcName());
		}
		for (Iterator<String> iterator = inv.production.keySet().iterator(); iterator.hasNext();) {
			String producer = iterator.next();
			int count = inv.production.get(producer);
			System.out.println(producer + " " + count);

		}
		for (Iterator<String> iterator = inv.consumption.keySet().iterator(); iterator.hasNext();) {
			String consumer = iterator.next();
			int count = inv.consumption.get(consumer);
			System.out.println(consumer + " " + count);

		}
	}
}

class Inventory {

	int maxStockSize = 0;

	int runSize = 0;

	int pCounter = 0;

	int cCounter = 0;

	int maxPTime = 0;

	int maxCTime = 0;

	List<Product> stock = new ArrayList<Product>();

	List<Product> sales = new ArrayList<Product>();

	Map<String, Integer> production = new HashMap<String, Integer>();

	Map<String, Integer> consumption = new HashMap<String, Integer>();

	public synchronized void addProduct(String pName) throws InterruptedException {

		PrintUtil.println(0, pName + " trying to add Product " + "PR" + (pCounter + 1));

		if (pCounter < runSize) {

			if (stock.size() == maxStockSize) {

				PrintUtil.println(1, pName + " waiting for the consumption " + pCounter);
				wait();
				System.out.println(pName + " notified after wait");

			} else {

				Random r = new Random();
				int time = r.nextInt(maxPTime);
				PrintUtil.println(0, pName + " production time " + time);
				// Thread.sleep(1000);
				long t1 = System.currentTimeMillis();
				while (System.currentTimeMillis() - t1 < 1500) {

				}

				Product product = new Product();
				product.setID(++pCounter);
				product.setName("PR" + pCounter);
				product.setpName(pName);
				stock.add(product);
				if (production.get(pName) == null) {
					production.put(pName, 1);
				} else {
					production.put(pName, production.get(pName) + 1);
				}
				PrintUtil.println(1, pName + " Added Product            " + product.getName());
				notify();
			}
		} else {
			PrintUtil.println(2, pName + " Not adding as Run Size completed");
		}

	}

	public synchronized Product getProduct(String cName) throws InterruptedException {

		PrintUtil.println(0, cName + "--" + cCounter);

		Product product = null;
		PrintUtil.println(0, cName + " Inventory Size=" + stock.size());

		if (cCounter < runSize) {

			if (stock.size() == 0) {
				PrintUtil.println(1, cName + " waiting for the production " + (cCounter + 1));
				wait();
				System.out.println(cName + " notified after wait");
			} else {

				Random r = new Random();
				int time = r.nextInt(maxCTime);
				PrintUtil.println(0, cName + " consumption time " + time);
				// Thread.sleep(1000);
				long t1 = System.currentTimeMillis();
				while (System.currentTimeMillis() - t1 < 1500) {

				}
				product = stock.get(0);
				product.setcName(cName);
				sales.add(product);
				stock.remove(0);
				cCounter++;
				if (consumption.get(cName) == null) {
					consumption.put(cName, 1);
				} else {
					consumption.put(cName, consumption.get(cName) + 1);
				}
				PrintUtil.println(1, cName + " Consumed Product                  " + product.getName());
				notify();
			}
		} else {
			PrintUtil.println(2, cName + " Not Consuming as Run Size completed");
		}

		return product;
	}
}

class Producer implements Runnable {

	Inventory inv;
	ThreadLocal<String> name = new ThreadLocal<String>();

	Producer(Inventory inv) {
		this.inv = inv;
	}

	public void run() {

		name.set(Thread.currentThread().getName());

		while (inv.pCounter < inv.runSize) {
			try {
				Random r = new Random();
				int time = r.nextInt(inv.maxPTime);
				PrintUtil.println(0, name.get() + " sleeping for " + time);
				Thread.sleep(10);
				inv.addProduct(name.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}

class Consumer implements Runnable {

	Inventory inv;

	ThreadLocal<String> name = new ThreadLocal<String>();

	Consumer(Inventory inv) {
		this.inv = inv;

	}

	public void run() {

		name.set(Thread.currentThread().getName());

		while (inv.cCounter < inv.runSize || inv.stock.size() > 0) {

			try {
				Random r = new Random();
				int time = r.nextInt(2000);
				PrintUtil.println(0, name.get() + " sleeping for " + time);
				Thread.sleep(4000);
				inv.getProduct(name.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}

class Product {

	int ID;

	String name;

	String pName;

	String cName;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

}
