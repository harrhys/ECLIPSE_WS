package com.farbig.practice.exceptions;

import com.farbig.practice.util.ThreadUtil;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		Thread t = new Thread(new Runnable() {

			public void run() {
				int i = 0;
				while (i < 1000000000) {
					i++;
					if (i % 100 == 0) {
						//System.out.println("i= " + i);
						if (Thread.currentThread().isInterrupted()) {
							try {
								System.out.println("Interrupted 1-" + i);
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					if (i % 100 == 5) {
						//System.out.println("i= " + i);
						if (Thread.currentThread().isInterrupted()) {
							try {
								System.out.println("Interrupted 2-" + i);
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					if (Thread.currentThread().isInterrupted()) {
						try {
							System.out.println("Interrupted 3-" + i);
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}

		});
		t.start();
		ThreadUtil.sleep(200);
		t.suspend();
		System.out.println(t.getState() + "suspended");
		t.interrupt();
		ThreadUtil.sleep(2000);
		System.out.println("resuming");
		t.resume();
		ThreadUtil.sleep(2000);
		t.stop();
		System.out.println(t.getState() + "stopped");

		ThreadUtil.sleep(2000);
		System.out.println(t.getState() + "stopped");

	}

}
