package com.farbig.practice.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableFuture {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService service = Executors.newFixedThreadPool(3);

		testFutureCancel(service);

		testTimoutFutureCancel(service);

		service.shutdown();

	}

	static void testFutureCancel(ExecutorService service) {

		Future<Integer> future = null;

		for (int i = 0; i < 10; i++) {

			future = service.submit(new CallableTask());
			int result = 0;
			try {
				Thread.sleep(1500);
				future.cancel(true);
				result = future.get();
				System.out.println("RabinKarp Method - " + result + "\n");
			} catch (Exception e) {
				if (e instanceof CancellationException)
					System.out.println("RabinKarp Method - Result Cancelled - "
							+ (i + 1));
				System.out.println();
			}
		}
	}

	static void testTimoutFutureCancel(ExecutorService service) {

		Future<Integer> future = null;

		for (int i = 0; i < 10; i++) {

			future = service.submit(new CallableTask());
			int result = 0;
			try {
				//only for timeout
				//result = future.get(2, TimeUnit.SECONDS);

				Thread.sleep(1500);
				future.cancel(true);
				result = future.get(2, TimeUnit.SECONDS);

				System.out.println("RabinKarp Method - " + result + "\n");
			}
			catch (Exception e) {
				if (e instanceof CancellationException)
					System.out.println("RabinKarp Method - Result Cancelled - "
							+ (i + 1));
				else if (e instanceof TimeoutException)
					System.out.println("RabinKarp Method - Result Timedout - "
							+ (i + 1));
				System.out.println();
			}
		}
	}
}

class CallableTask implements Callable<Integer> {

	static Integer counter = 0;

	public Integer call() throws Exception {

		synchronized (counter) {
			counter++;
			String name = Thread.currentThread().getName();
			System.out.println(name + " Entered Callable Task " + counter);

			while (!Thread.currentThread().isInterrupted()) {

				System.out.println(name + " Inside Callable Task " + counter);
				try {
					Thread.sleep(counter * 500);
				} catch (InterruptedException e) {
					System.out.println(name
							+ " Interrupted inside Callable Task " + counter);
					throw e;
				}

				System.out.println(name + " returning---" + counter);
				break;
			}
			System.out.println(name + " Exiting Callable Task " + counter);
		}
		return counter;
	}

}