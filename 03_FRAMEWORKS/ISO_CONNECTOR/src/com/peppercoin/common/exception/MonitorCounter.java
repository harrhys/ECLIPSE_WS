package com.peppercoin.common.exception;

import java.util.Map;
import java.util.TreeMap;

public class MonitorCounter {
	private int count = 1;
	private static Map counters = new TreeMap();

	public static void increment(String name) {
		MonitorCounter i = (MonitorCounter) counters.get(name);
		if (i == null) {
			counters.put(name, new MonitorCounter());
		} else {
			++i.count;
		}

	}

	public static void reset(String name) {
		counters.remove(name);
	}

	public static void resetAll() {
		counters.clear();
	}

	public static String[] getNames() {
		return (String[]) counters.keySet().toArray(new String[0]);
	}

	public static int get(String name) {
		MonitorCounter i = (MonitorCounter) counters.get(name);
		return i == null ? -1 : i.count;
	}
}