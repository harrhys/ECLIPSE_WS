package com.obopay.obopayagent.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;



/**
 * Date formatting methods. This class is kept separate from <code>StringFormat</code> 
 * to reduce the runtime memory overhead while loading the <code>java.util.*</code> classes.
 * @author 		<a href=mailto:david@obopay.com>David Mitchell</a>
 * @author		<a href=mailto:larry@obopay.com>Larry Wang</a>
 */
public abstract class DateFormat
{
	/**
	 * Specifies that hours are to be included in the formatted date/time string.
	 */
	public static final int TF_HRS			= 1;

	/**
	 * Specifies that minutes are to be included in the formatted date/time string.
	 */
	public static final int TF_MINS			= 2;

	/**
	 * Specifies that seconds are to be included in the formatted date/time string.
	 */
	public static final int TF_SECS			= 4;

	/**
	 * Specifies that the month is to be included in the formatted date/time string.
	 */
	public static final int TF_MONTH		= 8;

	/**
	 * Specifies that the day of the month is to be included in the formatted date/time string.
	 */
	public static final int TF_DAY			= 16;

	/**
	 * Specifies that a four-digit year is to be included in the formatted date/time string.
	 */
	public static final int TF_YEAR			= 32;

	/**
	 * Specifies that a two-digit year is to be included in the formatted date/time string.
	 */
	public static final int TF_YEAR_SHORT	= 128;

	/**
	 * Specifies that the complete date is to be included in the formatted date/time string. This is equivalent to <code>TF_MONTH | TF_DAY | TF_YEAR</code>.
	 */
	public static final int TF_ALL_DATE		= TF_MONTH | TF_DAY | TF_YEAR;

	/**
	 * Specifies that all time elements, hours, minutes and seconds be included  in the formatted string. This is equivalent to <code>TF_HRS | TF_MINS | TF_SECS</code>. 
	 */
	public static final int TF_ALL_TIME		= TF_HRS | TF_MINS | TF_SECS;
	
	/**
	 * Specifies that the complete date and time fields are to be included in the formatted date/time string. This is equivalent to <code>TF_ALL_TIME | TF_ALL_DATE</code>.
	 */
	public static final int TF_ALL			= TF_ALL_TIME | TF_ALL_DATE;

	public static boolean isValidDate(String date, String format) {
		java.text.DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(true);
		try {
			dateFormat.parse(date);
			return true;
		} catch(ParseException ex) {
			return false;
		}
	}


	/**
	 * Converts a <code>Date</code> object into a human-readable <code>String</code> format.
	 * The date is assumed to be UTC and is converted to the local time zone.
	 * Moved from <code>com.ewp.j2me.core.app</code>
	 * @param date					the <code>Date</code> object.
	 * @param timeFormat			specifies which date/time resolution to include in the formatted string.
	 * 								The <code>TF_</code> defines are integers that may be combined (OR'd) to request each of the corresponding
	 * 								time fields. For example, to request hours and minutes, but not seconds, the method
	 * @return 						formatted date
	 */
	public static final String formatDate (Date date, int timeFormat)
	{
		Calendar calendar = Calendar.getInstance();
		
		// find the local time zone (if available)
		TimeZone localZone = TimeZone.getDefault();
		String id = localZone.getID();

		if (localZone != null && !"GMT".equals(id) && !"UTC".equals(id))
		{
			// If local time zone is not GMT or UTC, then we convert the date to local time zone.
			// (Assume that the given date is in GMT).
			calendar.setTime(date);
			int millis = 
				calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + 
				calendar.get(Calendar.MINUTE) * 60 * 1000 +
				calendar.get(Calendar.SECOND) * 1000 + 
				calendar.get(Calendar.MILLISECOND);

			// Note: the MIDP spec says TimeZone.getOffset should account for daylight saving time,
			// but some handsets (like Nokia 6102) do not.
			long offset = localZone.getOffset(
				1,	// AD (there is no pre-defined constant in CLDC) 
				calendar.get(Calendar.YEAR), 
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), 
				calendar.get(Calendar.DAY_OF_WEEK), 
				millis);
			date.setTime(date.getTime() + offset);
		}
		
		calendar.setTime(date);
		
		// The date format is "mm/dd/yyyy hh:mm:ss [AM/PM]"
		StringBuffer buf = new StringBuffer(22);
			
		if ((timeFormat & TF_DAY) > 0)
		{			
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			String dd = padWithZero(String.valueOf(day));
			buf.append(dd);
		}
		
		if ((timeFormat & TF_MONTH) > 0)
		{
			if (buf.length() > 0)
			{
				buf.append('/');
			}
			int month = calendar.get(Calendar.MONTH) + 1;
			String mm = padWithZero(String.valueOf(month));
			buf.append(mm);
		}
			
		if (((timeFormat & TF_YEAR) > 0) ||
			((timeFormat & TF_YEAR_SHORT) > 0))
		{
			if (buf.length() > 0)
			{
				buf.append('/');
			}
			int year = calendar.get(Calendar.YEAR);
			String yyyy = String.valueOf(year);
			buf.append(
				((timeFormat & TF_YEAR_SHORT) > 0) ? 
					yyyy.substring(2) : 
					yyyy); 
		}
	
		String ampm = null;
		if ((timeFormat & TF_HRS) > 0)
		{
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
	
			// If noon, it's PM
			ampm = (hour < 12) ? "AM" : "PM";
	
			if (hour > 12)
			{
				hour -= 12;
			}
			else if (hour == 0)
			{
				// This is Midnight - don't show 00:mm:ss, show 12:mm:ss
				hour = 12;
			}
	
			String hr = String.valueOf(hour);
			if (buf.length() > 0)
			{
				buf.append(' ');
			}
			buf.append(hr);
		}
	
		if ((timeFormat & TF_MINS) > 0)
		{
			if ((timeFormat & TF_HRS) > 0)
			{
				buf.append(':');
			}
			else if (buf.length() > 0)
			{
				buf.append(' ');
			}
			int minutes = calendar.get(Calendar.MINUTE);
			String min = padWithZero(String.valueOf(minutes));
			buf.append(min);
		}
	
		if ((timeFormat & TF_SECS) > 0)
		{
			if (((timeFormat & TF_HRS) > 0) ||
				((timeFormat & TF_MINS) > 0))
			{
				buf.append(':');
			}
			else if (buf.length() > 0)
			{
				buf.append(' ');
			}
			int seconds = calendar.get(Calendar.SECOND);
			String sec = padWithZero(String.valueOf(seconds));
			buf.append(sec);
		}
		
		if (ampm != null)
		{
			buf.append(ampm);
		}
	
		return buf.toString();
	}
	
	//
	//Version Upgrade---Pooja
	public static final String formatConfirmScreenDate (Date date, int timeFormat)
	{
		Calendar calendar = Calendar.getInstance();
		
		// find the local time zone (if available)
		//TimeZone localZone = TimeZone.getDefault();
		//String id = localZone.getID();
		calendar.setTime(date);
		
		// The date format is "mm/dd/yyyy hh:mm:ss [AM/PM]"
		StringBuffer buf = new StringBuffer(22);
			
		if ((timeFormat & TF_DAY) > 0)
		{			
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			String dd = padWithZero(String.valueOf(day));
			buf.append(dd);
		}
		
		if ((timeFormat & TF_MONTH) > 0)
		{
			if (buf.length() > 0)
			{
				buf.append('/');
			}
			int month = calendar.get(Calendar.MONTH) + 1;
			String mm = padWithZero(String.valueOf(month));
			buf.append(mm);
		}
			
		if (((timeFormat & TF_YEAR) > 0) ||
			((timeFormat & TF_YEAR_SHORT) > 0))
		{
			if (buf.length() > 0)
			{
				buf.append('/');
			}
			int year = calendar.get(Calendar.YEAR);
			String yyyy = String.valueOf(year);
			buf.append(
				((timeFormat & TF_YEAR_SHORT) > 0) ? 
					yyyy.substring(2) : 
					yyyy); 
		}
	
		String ampm = null;
		if ((timeFormat & TF_HRS) > 0)
		{
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
	
			// If noon, it's PM
			ampm = (hour < 12) ? "AM" : "PM";
	
			if (hour > 12)
			{
				hour -= 12;
			}
			else if (hour == 0)
			{
				// This is Midnight - don't show 00:mm:ss, show 12:mm:ss
				hour = 12;
			}
	
			String hr = String.valueOf(hour);
			if (buf.length() > 0)
			{
				buf.append(' ');
			}
			buf.append(hr);
		}
	
		if ((timeFormat & TF_MINS) > 0)
		{
			if ((timeFormat & TF_HRS) > 0)
			{
				buf.append(':');
			}
			else if (buf.length() > 0)
			{
				buf.append(' ');
			}
			int minutes = calendar.get(Calendar.MINUTE);
			String min = padWithZero(String.valueOf(minutes));
			buf.append(min);
		}
	
		if ((timeFormat & TF_SECS) > 0)
		{
			if (((timeFormat & TF_HRS) > 0) ||
				((timeFormat & TF_MINS) > 0))
			{
				buf.append(':');
			}
			else if (buf.length() > 0)
			{
				buf.append(' ');
			}
			int seconds = calendar.get(Calendar.SECOND);
			String sec = padWithZero(String.valueOf(seconds));
			buf.append(sec);
		}
		
		if (ampm != null)
		{
			buf.append(ampm);
		}
	
		return buf.toString();
	}
	
	/**
	 * Gets the current system time in UTC.
	 * @return						The current system time, offset by the local time zone.
	 * 								If the local time zone is GMT or UTC, then this method is
	 * 								equivalent to <code>System.currentTimeMillis</code>.
	 */
	public static final long currentTimeMillisUTC ()
	{
		TimeZone localZone = TimeZone.getDefault();
	
		Calendar calendar = Calendar.getInstance();		
		Date date = new Date(System.currentTimeMillis());
		calendar.setTime(date);
		int millis = 
			calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 + 
			calendar.get(Calendar.MINUTE) * 60 * 1000 +
			calendar.get(Calendar.SECOND) * 1000 + 
			calendar.get(Calendar.MILLISECOND);

		// Note: the MIDP spec says TimeZone.getOffset should account for daylight saving time,
		// but some handsets (like Nokia 6102) do not.
		long offset = localZone.getOffset(
			1,	// AD (there is no pre-defined constant in CLDC) 
			calendar.get(Calendar.YEAR), 
			calendar.get(Calendar.MONTH), 
			calendar.get(Calendar.DAY_OF_MONTH), 
			calendar.get(Calendar.DAY_OF_WEEK), 
			millis);

		return date.getTime() - offset;
	}
	
	/**
	 * Converts the <code>long</code> into a human-readable <code>String</code> format.
	 * Moved from <code>com.ewp.j2me.core.app</code>
	 * @param utcDateTime			the UTC date-time
	 * @param timeFormat			specifies which date/time resolution to include in the formatted string.
	 * 								The <code>TF_</code> defines are integers that may be combined (OR'd) to request each of the corresponding
	 * 								time fields. For example, to request hours and minutes, but not seconds, the method
	 * @return 						formatted date
	 */
	public static final String formatDate (long utcDateTime, int timeFormat)
	{
		String strDate = null;
	
		Date date = new Date(utcDateTime);
		strDate = formatDate(date, timeFormat);
		
		return strDate;
	}
//Version Upgrade---Pooja
	public static final String formatConfirmScreenDate (long utcDateTime, int timeFormat)
	{
		String strDate = null;
	
		Date date = new Date(utcDateTime);
		strDate = formatConfirmScreenDate(date, timeFormat);	
		return strDate;
	}
	/*
	 * Prepend a single digit with a '0', if necessary.
	 * @param val					the numeric string.
	 * @return						the padded string. 
	 */
	private static String padWithZero (String val)
	{
		if (val != null && val.length() == 1)
		{
			val = "0" + val;
		}
		
		return val;
	}
}
