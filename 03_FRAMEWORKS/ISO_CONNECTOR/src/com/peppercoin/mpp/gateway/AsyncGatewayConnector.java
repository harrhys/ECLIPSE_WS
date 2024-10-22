package com.peppercoin.mpp.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.peppercoin.common.PpcnLogger;
import com.peppercoin.common.PpcnLogger.Level;
import com.peppercoin.common.exception.PpcnError;

public abstract class AsyncGatewayConnector {
	protected final PpcnLogger logger = new PpcnLogger(this.getClass().getName());
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private Map responses = Collections.synchronizedMap(new TreeMap());
	private Map watchers = Collections.synchronizedMap(new TreeMap());
	private GatewayTarget targetUsed;
	private AsyncGatewayReader readerThread = null;

	protected abstract GatewayTarget[] getConnectionTargets();

	protected abstract Socket getSocket(GatewayTarget var1) throws UnknownHostException, IOException;

	protected abstract boolean processPostConnect();

	protected abstract byte[] getGatewayMessage();

	protected abstract GatewayResponse createResponse(byte[] var1);

	protected abstract int getMaxReadRetries();

	protected abstract long getReadRetryDelay();

	protected abstract AsyncGatewayReader getReader();

	public final GatewayResponse send(byte[] packet, String responseKey) {
		if (this.watchers.keySet().contains(responseKey)) {
			this.logger.log("send",
					"responseKey " + quote(responseKey) + " already present as a watcher!  Returning null!",
					Level.WARN);
			return null;
		} else {
			this.logger.log("send",
					"adding watcher thread=" + Thread.currentThread() + " for responseKey: " + responseKey,
					Level.DEBUG);
			this.watchers.put(responseKey, Thread.currentThread());
			GatewayResponse answer = null;
			if (this.send(packet)) {
				answer = this.read(responseKey);
			}

			this.logger.log("send",
					"removing watcher thread=" + Thread.currentThread() + " for responseKey: " + responseKey,
					Level.DEBUG);
			this.watchers.remove(responseKey);
			return answer;
		}
	}

	public boolean send(byte[] packet) {
		try {
			if (!this.isConnected()) {
				return false;
			} else {
				this.logger.log("send", "to " + this.getConnectorName() + ": " + packet.length + " bytes", Level.DEBUG);
				OutputStream var2 = this.outputStream;
				synchronized (this.outputStream) {
					this.outputStream.write(packet);
				}

				this.logger.log("send", "successful", Level.DEBUG);
				return true;
			}
		} catch (IOException var5) {
			this.logger.log("send", "Can't send to " + this.getConnectorName() + ", error=" + var5.toString(),
					Level.WARN);
			this.disconnect();
			return false;
		}
	}

	public static void pause() {
		try {
			Thread.sleep(500L + (long) (new Random()).nextInt(1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final byte[] getMessage() {
		InputStream var1 = this.inputStream;
		synchronized (this.inputStream) {
			byte[] message = this.getGatewayMessage();
			if (message == null) {
				this.disconnect();
			}

			return message;
		}
	}

	public final void processResponse(byte[] data) {
		GatewayResponse response = this.createResponse(data);
		if (response != null) {
			String responseKey = response.getResponseKey();
			this.logger.log("processResponse", "response found for key: " + responseKey, Level.DEBUG);
			Thread t = (Thread) this.watchers.get(responseKey);
			if (t == null) {
				this.logger.log("processResponse", "couldn't find a watcher for response key: " + responseKey
						+ ", discarding response: " + quote(new String(data)), Level.WARN);
			} else {
				this.responses.put(responseKey, response);
				t.interrupt();
			}

		}
	}

	public final boolean stopReading() {
		return this.watchers.size() == 0;
	}

	protected static final String quote(String s) {
		return "\"" + s + "\"";
	}

	protected final synchronized boolean connect() {
		if (this.isConnected()) {
			return true;
		} else {
			GatewayTarget[] targets = this.getConnectionTargets();

			for (int i = 0; i < targets.length; ++i) {
				if (this.connect(targets[i])) {
					return true;
				}
			}

			this.logger.log("connect", "Cannot connect to " + this.getConnectorName(), Level.WARN);
			return false;
		}
	}

	protected final String readSocket(int num) {
		byte[] readBytes = this.readSocketBytes(num);
		if (readBytes != null && readBytes.length != 0) {
			String s = new String(readBytes);
			if (s.length() == num) {
				return s;
			} else {
				this.logger.log("readSocket",
						"Only read " + s.length() + " of " + num + " bytes from " + this.getConnectorName(),
						Level.WARN);
				return null;
			}
		} else {
			return null;
		}
	}

	protected final byte[] readSocketBytes(int num) {
		if (!this.isConnected()) {
			return null;
		} else {
			try {
				byte[] b = new byte[num];
				int numRead = this.inputStream.read(b, 0, num);
				this.logger.log("readSocketBytes", "" + numRead + " bytes from " + this.getConnectorName(),
						Level.DEBUG);
				if (numRead < 1) {
					return null;
				} else {
					byte[] returnValue = new byte[numRead];
					System.arraycopy(b, 0, returnValue, 0, numRead);
					return returnValue;
				}
			} catch (IOException var5) {
				this.logger.log("readSocketBytes", "Couldn't read " + num + " bytes from " + this.getConnectorName()
						+ ", error=" + var5.toString(), Level.WARN);
				this.disconnect();
				return null;
			}
		}
	}

	protected final synchronized void close() {
		try {
			if (this.socket != null) {
				this.socket.close();
			}
		} catch (IOException var2) {
			this.logger.log("close", this.getConnectorName() + ", error=" + var2.toString(), Level.WARN);
		}

		this.socket = null;
	}

	protected final String getConnectorName() {
		return this.targetUsed.getName();
	}

	private synchronized boolean connect(GatewayTarget target) {
		this.targetUsed = target;
		this.logger.log("connect", "Attempting to connect to " + this.getConnectorName(), Level.DEBUG);
		Object exception = null;

		try {
			this.socket = this.getSocket(target);
			this.outputStream = this.socket.getOutputStream();
			this.inputStream = this.socket.getInputStream();
		} catch (UnknownHostException var4) {
			exception = var4;
		} catch (IOException var5) {
			exception = var5;
		} catch (SecurityException var6) {
			exception = var6;
		}

		if (exception != null) {
			this.logger.log("connect",
					"Can't connect to " + this.getConnectorName() + ", error=" + ((Exception) exception).toString(),
					Level.WARN);
			return false;
		} else if (!this.processPostConnect()) {
			this.logger.log("connect", "Could not process ACK", Level.ERROR);
			this.disconnect();
			return false;
		} else {
			this.logger.log("connect", "connected to target=" + quote(this.getConnectorName()), Level.DEBUG);
			return true;
		}
	}

	private final GatewayResponse read(String responseKey) {
		this.activateReaderThread();

		for (int j = 1; j <= this.getMaxReadRetries(); ++j) {
			try {
				Thread.sleep(this.getReadRetryDelay() * 1000L);
				this.logger.log("read", "responseKey=" + responseKey + ": timed out", Level.WARN);
			} catch (InterruptedException var4) {
				this.logger.log("read", "responseKey=" + responseKey + ": interrupted", Level.DEBUG);
			}

			GatewayResponse response = (GatewayResponse) this.responses.get(responseKey);
			if (response != null) {
				this.responses.remove(responseKey);
				return response;
			}

			this.logger.log("read", "responseKey=" + responseKey + ": didn't find response", Level.WARN);
		}

		this.logger.log("read", "responseKey=" + responseKey + ": timed out.", Level.WARN);
		return null;
	}

	private final synchronized void disconnect() {
		this.close();
		Map var1 = this.watchers;
		synchronized (this.watchers) {
			Iterator it = this.watchers.values().iterator();

			while (it.hasNext()) {
				((Thread) it.next()).interrupt();
			}

			this.watchers.clear();
		}
	}

	protected final synchronized void forceDisconnectForTesting() {
		try {
			if (this.socket != null) {
				this.socket.close();
			}

		} catch (IOException var2) {
			throw new PpcnError("Could not force socket closed", var2);
		}
	}

	private synchronized void activateReaderThread() {
		if (this.readerThread != null && !this.readerThread.isAlive()) {
			this.readerThread = null;
		}

		if (this.readerThread == null) {
			this.readerThread = this.getReader();
		} else {
			this.readerThread.activate();
		}

	}

	protected final boolean isConnected() {
		return this.socket != null && this.socket.isConnected();
	}
}