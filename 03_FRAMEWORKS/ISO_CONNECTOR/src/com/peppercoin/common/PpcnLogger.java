package com.peppercoin.common;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class PpcnLogger {
	public static final class Level implements Serializable {

		public static Level lookup(int value) {
			return (Level) intMapping.get(new Integer(value));
		}

		public static Level lookup(String value) {
			return (Level) stringMapping.get(value);
		}

		public final int intValue() {
			return value;
		}

		public final String toString() {
			return String.valueOf(label);
		}

		public int hashCode() {
			return intValue();
		}

		public boolean equals(Object obj) {
			if (obj instanceof Level)
				return intValue() == ((Level) obj).intValue();
			else
				return false;
		}

		public static final int DEBUG_ID;
		public static final int INFO_ID;
		public static final int WARN_ID;
		public static final int ERROR_ID;
		public static final int FATAL_ID;
		public static final Level DEBUG;
		public static final Level INFO;
		public static final Level WARN;
		public static final Level ERROR;
		public static final Level FATAL;
		private static HashMap intMapping;
		private static HashMap stringMapping;
		private final int value;
		private final String label;

		static {
			DEBUG_ID = org.apache.log4j.Level.DEBUG.toInt();
			INFO_ID = org.apache.log4j.Level.INFO.toInt();
			WARN_ID = org.apache.log4j.Level.WARN.toInt();
			ERROR_ID = org.apache.log4j.Level.ERROR.toInt();
			FATAL_ID = org.apache.log4j.Level.FATAL.toInt();
			DEBUG = new Level(DEBUG_ID, "DEBUG");
			INFO = new Level(INFO_ID, "INFO");
			WARN = new Level(WARN_ID, "WARN");
			ERROR = new Level(ERROR_ID, "ERROR");
			FATAL = new Level(FATAL_ID, "FATAL");
		}

		private Level(int iValue, String iLabel) {
			if (intMapping == null)
				intMapping = new HashMap();
			if (stringMapping == null)
				stringMapping = new HashMap();
			value = iValue;
			label = iLabel;
			intMapping.put(new Integer(value), this);
			stringMapping.put(label, this);
		}
	}

	private Logger logger;

	public PpcnLogger(String className) {
		this.logger = Logger.getLogger(className);
	}

	public void log(String method, String msg, Level level) {
		if (this.isEnabledFor(level)) {
			StringBuffer buf = new StringBuffer();
			buf.append(method);
			buf.append(" ");
			buf.append(msg);
			this.logger.log(Priority.toPriority(level.intValue()), buf.toString());
		}
	}

	public void log(String method, Object[] args, String msg, Level level) {
		if (this.isEnabledFor(level)) {
			StringBuffer buf = new StringBuffer();
			buf.append(method);
			buf.append(" ");
			buf.append(msg);
			buf.append(", ");
			buf.append(writeArray(args));
			this.logger.log(Priority.toPriority(level.intValue()), buf.toString());
		}
	}

	public void log(String method, Object[] args, Level level) {
		if (this.isEnabledFor(level)) {
			StringBuffer buf = new StringBuffer();
			buf.append(method);
			buf.append(" ");
			buf.append(writeArray(args));
			this.logger.log(Priority.toPriority(level.intValue()), buf.toString());
		}
	}

	public void log(String method, Throwable t, String msg, Level level) {
		if (this.isEnabledFor(level)) {
			StringWriter strWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(strWriter);
			t.printStackTrace(writer);
			StringBuffer buf = new StringBuffer();
			buf.append(method);
			buf.append(" ");
			buf.append(msg);
			buf.append(":\n");
			buf.append(strWriter);
			this.logger.log(Priority.toPriority(level.intValue()), buf.toString());
		}
	}

	public void log(String method, Object[] args, Throwable thrown, String msg, Level level) {
		if (this.isEnabledFor(level)) {
			StringWriter strWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(strWriter);
			thrown.printStackTrace(writer);
			StringBuffer buf = new StringBuffer();
			buf.append(method);
			buf.append(" ");
			buf.append(msg);
			buf.append(", ");
			buf.append(writeArray(args));
			buf.append(":\n");
			buf.append(strWriter);
			this.logger.log(Priority.toPriority(level.intValue()), buf.toString());
		}
	}

	public boolean isEnabledFor(Level level) {
		return this.logger.isEnabledFor(Priority.toPriority(level.intValue()));
	}

	public static String writeArray(Object[] args) {
		if (args == null) {
			return "null";
		} else {
			StringBuffer buf = new StringBuffer();
			buf.append("[");

			for (int i = 0; i < args.length; ++i) {
				if (args[i] instanceof Object[]) {
					buf.append(writeArray((Object[]) args[i]));
				} else {
					buf.append(args[i]);
				}

				if (i < args.length - 1) {
					buf.append(", ");
				}
			}

			buf.append("]");
			return buf.toString();
		}
	}
}