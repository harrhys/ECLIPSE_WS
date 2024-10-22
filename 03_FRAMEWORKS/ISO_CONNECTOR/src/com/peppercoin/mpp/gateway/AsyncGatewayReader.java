package com.peppercoin.mpp.gateway;

import com.peppercoin.common.PpcnLogger;
import com.peppercoin.common.PpcnLogger.Level;

public abstract class AsyncGatewayReader extends Thread {
	private final PpcnLogger logger = new PpcnLogger(this.getClass().getName());

	public AsyncGatewayReader() {
		this.logger.log("<init>", "making a new Reader", Level.DEBUG);
		this.start();
	}

	synchronized void activate() {
		this.logger.log("activate", "calling notify()", Level.DEBUG);
		this.notify();
	}

	synchronized void deactivate() {
		this.logger.log("run", "going into wait state", Level.DEBUG);

		try {
			this.wait();
		} catch (InterruptedException var2) {
			this.logger.log("run", "I was interrupted from waiting", Level.DEBUG);
		}

		this.logger.log("run", "coming out of wait state", Level.DEBUG);
	}

	protected abstract AsyncGatewayConnector getConnection();

	public void run() {
		while (true) {
			AsyncGatewayConnector connector = this.getConnection();
			if (connector == null) {
				this.logger.log("run", "Got null AsyncGatewayConnector.  Exiting Thread.", Level.DEBUG);
				return;
			}

			this.logger.log("run", "about to read a message", Level.DEBUG);
			byte[] data;
			if ((data = connector.getMessage()) == null) {
				this.logger.log("run", "Message was null. Pausing. ", Level.DEBUG);
				AsyncGatewayConnector.pause();
			} else {
				this.logger.log("run", "processing response", Level.DEBUG);
				connector.processResponse(data);
			}

			if (connector.stopReading()) {
				this.deactivate();
			} else {
				this.logger.log("run", "should continue reading", Level.DEBUG);
			}
		}
	}
}