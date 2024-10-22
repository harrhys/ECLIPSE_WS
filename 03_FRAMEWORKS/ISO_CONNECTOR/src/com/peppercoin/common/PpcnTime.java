package com.peppercoin.common;

import com.peppercoin.common.config.Config;
import com.peppercoin.common.exception.PpcnError;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class PpcnTime implements Comparable {
	private GregorianCalendar time;
	private static long timeDelta = 0L;
	public static final Integer timeChangeLock = new Integer(0);
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private static final SimpleDateFormat debugFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sszzz");
	private static final SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
	private static final SimpleDateFormat uiFormatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa zzz");
	private static final SimpleDateFormat uiDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	private static final SimpleDateFormat dbFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat queryFormatter = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''");
	public static final TimeZone GMTTimeZone = TimeZone.getTimeZone("GMT");
	private static List watchers;

	public PpcnTime() {
		this.time = new GregorianCalendar(GMTTimeZone);
		if (timeDelta != 0L) {
			this.time.setTimeInMillis(this.time.getTimeInMillis() - timeDelta);
		}

	}

	public PpcnTime(Date d) {
		this();
		this.time.setTimeInMillis(d.getTime());
	}

	public PpcnTime(Calendar d) {
		this();
		this.time.setTimeZone(d.getTimeZone());
		this.time.setTimeInMillis(d.getTimeInMillis());
	}

	public PpcnTime(String str) {
		this();
		SimpleDateFormat var2 = sFormatter;
		synchronized (sFormatter) {
			try {
				this.time.setTime(sFormatter.parse(str));
			} catch (ParseException var5) {
				throw new PpcnError("Invalid date: " + str);
			}

		}
	}

	public static PpcnTime parseQueryDateString(String date) {
		SimpleDateFormat var1 = queryFormatter;
		synchronized (queryFormatter) {
			PpcnTime var10000;
			try {
				GregorianCalendar c = new GregorianCalendar();
				c.setTime(queryFormatter.parse(date));
				var10000 = new PpcnTime(c);
			} catch (ParseException var4) {
				throw new PpcnError("Invalid date: " + date);
			}

			return var10000;
		}
	}

	public static void setBaseTime(Date d) {
		if (!Config.getBoolean("dev", false)) {
			System.err.println("Change Time called outside of development - ignoring it.");
		}

		Integer var1 = timeChangeLock;
		synchronized (timeChangeLock) {
			long newTimeDelta = (new Date()).getTime() - d.getTime();
			if (timeDelta != 0L && newTimeDelta > timeDelta) {
				System.err.println("Can't change time delta backwards (from " + timeDelta + " to " + newTimeDelta
						+ "), ignoring change.");
			} else {
				PpcnTime oldTime = new PpcnTime();
				timeDelta = newTimeDelta;
				PpcnTime newTime = new PpcnTime();
				Config.setWithTransaction("ppcn-time", newTime.toString(), "PpcnTime seed value");

				for (int j = 0; j < watchers.size(); ++j) {
					TimeWatcher tw = (TimeWatcher) watchers.get(j);

					try {
						tw.changed(oldTime, newTime);
					} catch (Exception var10) {
						System.out.println("TimeWatcher Exception from watcher: " + tw.getName() + ". Exception was "
								+ var10.toString());
					} catch (PpcnError var11) {
						System.out.println("TimeWatcher PpcnError from watcher: " + tw.getName() + ". Error was "
								+ var11.toString());
					}
				}

			}
		}
	}

	public static void register(TimeWatcher tw) {
		watchers.add(tw);
	}

	public PpcnTime(int year, int month, int day, int hour, int minute, int second) {
		if (year >= 2000 && month >= 0 && month <= 11 && day >= 1 && day <= 31 && hour >= 0 && hour <= 23 && minute >= 0
				&& minute <= 59 && second >= 0 && second <= 59) {
			this.time = new GregorianCalendar(GMTTimeZone);
			this.time.set(year, month, day, hour, minute, second);
			this.time.set(14, 0);
		} else {
			throw new PpcnError("Invalid time values (" + year + "," + month + "," + day + "," + hour + "," + minute
					+ "," + second + ")");
		}
	}

	private PpcnTime(int year, int month, int day, int hour, int minute, int second, TimeZone zone) {
		this.time = new GregorianCalendar(zone);
		this.time.set(year, month, day, hour, minute, second);
	}

	public PpcnTime set(int code, int value) {
		PpcnTime other = this.addSeconds(0);
		other.time.set(code, value);
		return other;
	}

	public PpcnTime setTimeZone(TimeZone newZone) {
		PpcnTime other = this.addSeconds(0);
		other.time.setTimeZone(newZone);
		long myTime = this.time.getTimeInMillis();
		long oldOffset = (long) this.time.getTimeZone().getOffset(myTime);
		long newOffset = (long) other.time.getTimeZone().getOffset(myTime);
		other.time.setTimeInMillis(this.time.getTimeInMillis() - oldOffset + newOffset);
		return other;
	}

	public static synchronized Date parse(String str) {
		SimpleDateFormat var1 = sFormatter;
		synchronized (sFormatter) {
			Date var10000;
			try {
				var10000 = sFormatter.parse(str);
			} catch (ParseException var4) {
				return null;
			}

			return var10000;
		}
	}

	public String format(SimpleDateFormat f) {
		return this.format(f, this.time);
	}

	private String format(SimpleDateFormat f, GregorianCalendar c) {
		synchronized (f) {
			f.setTimeZone(c.getTimeZone());
			return f.format(c.getTime());
		}
	}

	public Date getUTC() {
		if (!this.time.getTimeZone().equals(GMTTimeZone)) {
			throw new PpcnError("Trying to store non-GMT date (" + this.time.getTimeZone().getDisplayName() + ")");
		} else {
			return this.time.getTime();
		}
	}

	public void setUTC(Date date) {
		this.time.setTime(date);
	}

	public GregorianCalendar getGregorianCalendar() {
		return this.time;
	}

	public void setGregorianCalendar(GregorianCalendar gregorianCalendar) {
		this.time.setTimeInMillis(gregorianCalendar.getTimeInMillis());
	}

	public int get(int field) {
		return this.time.get(field);
	}

	public PpcnTime addMonths(int months) {
		return new PpcnTime(this.time.get(1), this.time.get(2) + months, this.time.get(5), this.time.get(11),
				this.time.get(12), this.time.get(13), this.time.getTimeZone());
	}

	public PpcnTime addDays(int days) {
		return new PpcnTime(this.time.get(1), this.time.get(2), this.time.get(5) + days, this.time.get(11),
				this.time.get(12), this.time.get(13), this.time.getTimeZone());
	}

	public PpcnTime addHours(int hours) {
		return new PpcnTime(this.time.get(1), this.time.get(2), this.time.get(5), this.time.get(11) + hours,
				this.time.get(12), this.time.get(13), this.time.getTimeZone());
	}

	public PpcnTime addMinutes(int min) {
		return new PpcnTime(this.time.get(1), this.time.get(2), this.time.get(5), this.time.get(11),
				this.time.get(12) + min, this.time.get(13), this.time.getTimeZone());
	}

	public PpcnTime addSeconds(int seconds) {
		return new PpcnTime(this.time.get(1), this.time.get(2), this.time.get(5), this.time.get(11), this.time.get(12),
				this.time.get(13) + seconds, this.time.getTimeZone());
	}

	public String toString() {
		return this.format(formatter);
	}

	public static String toUIString(PpcnTime ppcnTime) {
		return ppcnTime == null ? "" : ppcnTime.toUIString();
	}

	private String toUIString() {
		return this.format(uiFormatter);
	}

	public static String toUIDateString(PpcnTime ppcnTime) {
		return ppcnTime == null ? "" : ppcnTime.toUIDateString();
	}

	private String toUIDateString() {
		return this.format(uiDateFormatter);
	}

	public static String toDbString(PpcnTime ppcnTime) {
		return ppcnTime == null ? "" : ppcnTime.toDbString();
	}

	public String toDbString() {
		return this.format(dbFormatter);
	}

	public static String toQueryString(PpcnTime ppcnTime) {
		return ppcnTime == null ? "" : ppcnTime.toQueryString();
	}

	public String toQueryString() {
		return this.format(queryFormatter);
	}

	public int compareTo(Object obj) {
		if (obj != null && obj instanceof PpcnTime) {
			PpcnTime other = (PpcnTime) obj;
			return this.time.getTime().compareTo(other.time.getTime());
		} else {
			return -1;
		}
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PpcnTime)) {
			return false;
		} else {
			PpcnTime other = (PpcnTime) obj;
			return this.time.equals(other.time);
		}
	}

	public int hashCode() {
		return this.time.hashCode();
	}

	public static long getTimeDelta() {
		return timeDelta;
	}

	static {
		TimeZone.setDefault(GMTTimeZone);
		watchers = new ArrayList();
		queryFormatter.setTimeZone(GMTTimeZone);
	}
}