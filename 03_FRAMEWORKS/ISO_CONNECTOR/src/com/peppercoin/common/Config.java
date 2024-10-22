package com.peppercoin.common;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.peppercoin.common.PpcnLogger.Level;
import com.peppercoin.common.persistence.DbAction;
import com.peppercoin.common.persistence.DbUtil;
import com.peppercoin.common.persistence.PpcnTransaction;
import com.peppercoin.common.platform.PpcnSession;
import com.peppercoin.common.util.PpcnTime;

public class Config {
	private static String myHost = null;
	private static Map params = null;
	private static boolean isLocal = false;
	private static final PpcnLogger logger;

	public static void reset() {
		params = null;
		init();
	}

	public static Iterator iterator() {
		return params.values().iterator();
	}

	public static String get(String keyword) {
		return get(keyword, (String) null);
	}

	public static String get(String keyword, String defaultValue) {
		init();
		ConfigParam v = (ConfigParam) params.get(Util.getHost() + "." + keyword);
		if (v != null) {
			return v.getValue();
		} else {
			v = (ConfigParam) params.get("*." + keyword);
			return v != null ? v.getValue() : defaultValue;
		}
	}

	public static int getInt(String keyword) {
		return getInt(keyword, 0);
	}

	public static int getInt(String keyword, int defaultValue) {
		try {
			return Integer.parseInt(get(keyword));
		} catch (NumberFormatException var3) {
			return defaultValue;
		}
	}

	public static boolean getBoolean(String keyword) {
		return getBoolean(keyword, false);
	}

	public static boolean getBoolean(String keyword, boolean def) {
		String s = get(keyword);
		return s == null ? def : Boolean.valueOf(s);
	}

	public static Date getDate(String keyword, String format, Date def) {
		String s = get(keyword);
		Date returnValue = def;
		if (s != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);

			try {
				returnValue = formatter.parse(s);
			} catch (ParseException var7) {
				var7.printStackTrace();
			}
		}

		return returnValue;
	}

	public static void setWithTransaction(String keyword, String value, String desc) {
		DbUtil.exec(new DbAction() {

			public void exec(Connection c) {
				Config.set(keyword, value, desc);
			}

		});
	}

	public static void set(String keyword, String value, String desc) {
		init();
		if (isLocal) {
			ConfigParam p = new ConfigParam(true, keyword, value, desc);
			params.put(p.getKey(), p);
		} else {
			ConfigParamCompId id = new ConfigParamCompId("", keyword);
			ConfigParam p = (ConfigParam) PpcnSession.getDb().get(com.peppercoin.common.ConfigParam.class, id);
			if (p == null) {
				logger.log("set", keyword + "=" + value + ", new value.", Level.DEBUG);
				p = new ConfigParam(keyword, value, desc);
				params.put(p.getKey(), p);
			} else {
				logger.log("set", keyword + "=" + value + ", changed value.", Level.DEBUG);
				p.setValue(value);
				params.put(p.getKey(), p);
			}

		}
	}

	public static void initLocal() {
		params = new TreeMap();
		isLocal = true;
	}

	private static void init() {
		if (params == null) {
			params = new TreeMap();
			PpcnTransaction tx = null;

			try {
				tx = new PpcnTransaction();
				PpcnSession.push();
				List lst = PpcnSession.getDb().getAll("from ConfigParam");
				Iterator it = lst.iterator();

				while (true) {
					if (!it.hasNext()) {
						tx.commit();
						if (!getBoolean("dev", false)) {
							break;
						}

						String d = get("ppcn-time");
						if (d != null) {
							PpcnTime t = new PpcnTime(d);
							logger.log("init", "(re-)initializing PpcnTime to " + t, Level.INFO);
							PpcnTime.setBaseTime(t.getUTC());
						}
						break;
					}

					ConfigParam p = (ConfigParam) it.next();
					logger.log("init", p.getKey() + "=" + p.getValue(), Level.DEBUG);
					params.put(p.getKey(), p);
				}
			} finally {
				PpcnSession.pop();
				if (tx != null && tx.isOpen()) {
					logger.log("close", "Rolling back uncommitted transaction.", Level.WARN);
					tx.rollback();
				}

			}

		}
	}

	public static boolean isEdge() {
		return get("module", "FOO").equals("EDGE");
	}

	public static Collection getAllParameters() {
		Vector returnValue = new Vector();
		Iterator it = params.values().iterator();

		while (it.hasNext()) {
			returnValue.add(it.next());
		}

		return returnValue;
	}

	public static String getEdgeHostId() {
		if (myHost != null) {
			return myHost;
		} else if (!isEdge()) {
			myHost = "NA";
			return myHost;
		} else {
			if (getBoolean("multi-host", false)) {
				myHost = "http://" + getIPAddress() + ":18080";
			} else {
				myHost = "http://edge.int.peppercoin.com:18080";
			}

			return myHost;
		}
	}

	public static String getIPAddress() {
		String valueFromDatabase = get("host-address");
		return valueFromDatabase == null ? Util.getIPAddress() : valueFromDatabase;
	}

	static {
		logger = new PpcnLogger(com.peppercoin.common.Config.class.getName());
	}
}