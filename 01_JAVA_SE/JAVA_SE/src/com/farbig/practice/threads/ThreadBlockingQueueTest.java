package com.farbig.practice.threads;

import com.farbig.practice.util.ThreadUtil;

public class ThreadBlockingQueueTest {

	public static void main(String[] args) throws Exception {

		ThreadBlockingQueue logger = ThreadBlockingQueue.getLogger();
		logger.log("test");
		ThreadUtil.logThreads(false);
		ThreadUtil.sleep(2000);
		logger.shutDown();
		ThreadUtil.sleep(2000);
		ThreadUtil.shutdown();
		
	}

}
