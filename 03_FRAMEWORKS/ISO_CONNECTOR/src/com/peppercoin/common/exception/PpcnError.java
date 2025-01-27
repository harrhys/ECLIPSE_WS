package com.peppercoin.common.exception;

import com.peppercoin.common.PpcnLogger;
import com.peppercoin.common.PpcnLogger.Level;

public class PpcnError extends Error {
	private boolean didStackTrace;
	private static final PpcnLogger logger;

	public PpcnError(String msg) {
		this(msg, (Throwable) null, true);
	}

	public PpcnError(String msg, boolean printStackTrace) {
		this(msg, (Throwable) null, printStackTrace);
	}

	public PpcnError(String msg, Throwable cause) {
		this(msg, cause, true);
	}

	public PpcnError(String msg, Throwable cause, boolean printStackTrace) {
		super(msg, cause);
		this.didStackTrace = false;
		MonitorCounter.increment("ppcn-errors");
		if (printStackTrace) {
			String extras = cause == null ? "" : ": " + cause.getMessage();
			logger.log("PpcnError", msg + extras, Level.ERROR);
			if (cause != null && !this.didStackTrace(cause)) {
				StackTraceElement[] trace = cause.getStackTrace();
				StringBuffer st = new StringBuffer();

				for (int j = 0; j < trace.length; ++j) {
					st.append(trace[j].toString() + "\n");
				}

				logger.log("PpcnError", st.toString(), Level.ERROR);
				this.didStackTrace = true;
			}
		}

	}

	public boolean stackTraceOn() {
		return this.didStackTrace;
	}

	private boolean didStackTrace(Throwable previous) {
		while (previous != null) {
			if (previous instanceof PpcnError) {
				return ((PpcnError) previous).stackTraceOn();
			}

			previous = previous.getCause();
		}

		return false;
	}

	static {
		logger = new PpcnLogger(com.peppercoin.common.exception.PpcnError.class.getName());
	}
}