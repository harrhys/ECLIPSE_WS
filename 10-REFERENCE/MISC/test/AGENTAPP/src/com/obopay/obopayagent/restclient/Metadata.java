package com.obopay.obopayagent.restclient;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.Context;

import com.obopay.obopayagent.utils.Base64decoder;
import com.obopay.obopayagent.utils.DateFormat;
import com.obopay.obopayagent.utils.StringFormat;

/**
 * Encapsulation of metadata info used in mobile websvc API output.
 * <b>This file should be in sync with <code>com.ewp.internal.mobile.web.services.data.Metadata</code> in the websvc source code.</b>
 * @author <a href="larry@obopay.com">Larry Wang</a>
 */
public class Metadata
{
	public Context context;
	/**
	 * Name of the metadata field; may be <code>null</code>.
	 */
	public String name;

	/**
	 * Label of the metadata field; may be <code>null</code>.
	 */
	public String label;

	/**
	 * Value of the metadata field. It may be either formatted or unformatted, depending on  
	 * whether <code>formatValue</code> is <code>true</code> or not in the input to the websvc API. 
	 * The class type of this variable depends on the value of <code>type</code>.
	 * @see #type
	 */
	public Object value;
	
	/**
	 * Data type of the metadata field.
	 * Provides a hint to the client on how to format <code>value</code> for display, 
	 * if <code>value</code> if unformatted.
	 * Possible values are:
	 * <ul>
	 * <li><code>null</code> - <code>value</code> is a <code>String</code>; the client app should display <code>value</code> as-is. 
	 * <li><code>FIELD_TYPE_DATE</code> - <code>value</code> is an ISO 8601 date string.</li>
	 * <li><code>FIELD_TYPE_LONG</code> - <code>value</code> is a <code>Long</code></li>
	 * <li><code>FIELD_TYPE_INT</code> - <code>value</code> is an <code>Integer</code></li>
	 * <li><code>FIELD_TYPE_CURRENCY</code> - <code>value</code> is a <code>Long</code>, and contains currency amount</li>
	 * <li><code>FIELD_TYPE_PHONE_NUMBER</code> - <code>value</code> is a <code>String</code>, and contains an unformatted phone number</li>
	 * <li><code>FIELD_TYPE_IMAGE_PNG</code> - <code>value</code> is a <code>String</code>, and contains a Base64-encoded PNG image stream</li>
	 * <li><code>FIELD_TYPE_COLOR</code> - <code>value</code> is a <code>String</code>, and contains a hexadecimal representation of a color</li>
	 * <li><code>FIELD_TYPE_TEXT</code> - <code>value</code> is a <code>String</code>, and contains custom text</li>
	 * <li><code>FIELD_TYPE_SCREEN</code> - <code>value</code> is a <code>String</code>, and contains a screen customization directive</li>
	 * </ul>
	 */
	public String type;
	
	private int typeID;
	
	/**
	 * Indication of usage in a UI context
	 */
	public String usage;
	private int usageID = 0;
	
	/**
	 * Indication of maximum length in a UI context
	 */
	private int maxLength;
	
	/**
	 * Value used in <code>type</code> to indicate <code>value</code> is an ISO 8601 date string. 
	 */
	public static final String FIELD_TYPE_DATE				= "Date";
	
	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is a <code>Long</code>, 
	 * and contains currency amount 
	 */
	public static final String FIELD_TYPE_CURRENCY			= "CURRENCY";


	public static final String FIELD_TYPE_AMOUNT		= "AMOUNT";
	public static final String FIELD_TYPE_CARD_NUM		= "CARDNUM";
	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is a <code>String</code>, 
	 * and contains an unformatted phone number 
	 */
	public static final String FIELD_TYPE_PHONE_NUMBER		= "PHONE_NUMBER";

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is a <code>String</code>, 
	 * and contains numeric characters that must be masked when displayed.
	 */
	public static final String FIELD_TYPE_PIN				= "PIN";

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is a <code>Long</code>
	 */
	public static final String FIELD_TYPE_LONG				= "long"; 

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is an <code>Integer</code>
	 */
	public static final String FIELD_TYPE_INT				= "int"; 

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is Base64-encoded PNG image stream
	 */
	public static final String FIELD_TYPE_IMAGE_PNG			= "IMAGE_PNG"; 

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is the URL of an image stream
	 */
	public static final String FIELD_TYPE_IMAGE_URL			= "IMAGE_URL"; 

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is a hexadecimal representation of a color
	 */
	public static final String FIELD_TYPE_COLOR				= "COLOR"; 

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is a custom text insert
	 */
	public static final String FIELD_TYPE_TEXT				= "TEXT"; 

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> is an email address
	 */
	public static final String FIELD_TYPE_EMAIL_ADDR				= "EMAIL_ADDR"; 

	/**
	 * Value used in <code>type</code> to indicate that <code>value</code> a screen customization directive
	 */
	public static final String FIELD_TYPE_SCREEN			= "SCREEN"; 

	/**
	 * Value used in <code>usage</code> to indicate that <code>value</code> is a value to be displayed
	 * with normal highlighting.
	 */
	public static final String USAGE_DISPLAY_NORMAL			= "DISPLAY_NORMAL";
	/**
	 * Value used in <code>usage</code> to indicate that <code>value</code> is a value to be displayed
	 * with bold highlighting. 
	 */
	public static final String USAGE_DISPLAY_BOLD			= "DISPLAY_BOLD";
	
	/**
	 * Value used in <code>usage</code> to indicate that <code>value</code> is a value to be displayed
	 * with bold highlighting and top and bottom spacing.
	 */
	public static final String USAGE_DISPLAY_HEADING		= "DISPLAY_HEADING";

	/**
	 * Value used in <code>usage</code> to indicate that the label and formatting information is to
	 * be used to construct an input text field.
	 */
	public static final String USAGE_INPUT					= "INPUT";

	public static final int FIELD_TYPE_ID_RAW			= 0;
	public static final int FIELD_TYPE_ID_PHONE_NUMBER 	= 1;
	public static final int FIELD_TYPE_ID_LONG 			= 2;
	public static final int FIELD_TYPE_ID_INT			= 3;
	public static final int FIELD_TYPE_ID_IMAGE_PNG		= 4;
	public static final int FIELD_TYPE_ID_COLOR			= 5;
	public static final int FIELD_TYPE_ID_TEXT			= 6;
	public static final int FIELD_TYPE_ID_DATE			= 7;
	public static final int FIELD_TYPE_ID_CURRENCY		= 8;
	public static final int FIELD_TYPE_ID_SCREEN		= 9;
	public static final int FIELD_TYPE_ID_PIN			=10;
	public static final int FIELD_TYPE_ID_EMAIL_ADDR	=11;
	public static final int FIELD_TYPE_ID_IMAGE_URL		=12;
	public static final int FIELD_TYPE_ID_AMOUNT		=13;
	
	
	public static final int USAGE_ID_DEFAULT			= 0;
	public static final int USAGE_ID_DISPLAY_NORMAL		= 1;
	public static final int USAGE_ID_DISPLAY_BOLD 		= 2;
	public static final int USAGE_ID_DISPLAY_HEADING	= 3;
	public static final int USAGE_ID_INPUT				= 4;
	public static final int USAGE_ID_NONDISPLAY			= 5;

	public static final String FIELD_SPACER_NAME		= "$SPACER";
	// TODO Remove field names
	
	/**
	 * Field name for transaction date/time.
	 */
	public static final String FIELD_NAME_DATE_TIME			= "DATE_TIME"; 

	/**
	 * Field name for the To/From field in P2P transaction.
	 */
	public static final String FIELD_NAME_P2P				= "P2P";

	/**
	 * Field name for the Name field.
	 */
	public static final String FIELD_NAME_NAME				= "NAME";
	
	/**
	 * Field name for the Merchant Name field.
	 */
	public static final String FIELD_NAME_MERCHANT_NAME		= "MERCHANT_NAME";
	
	/**
	 * Field name for the transaction amount.
	 */
	public static final String FIELD_NAME_TX_AMOUNT			= "TX_AMOUNT";

	/**
	 * Field name for the location (for POS).
	 */
	public static final String FIELD_NAME_LOCATION			= "LOCATION";

	/**
	 * Field name for the transaction amount.
	 */
	public static final String FIELD_NAME_TX_NUM			= "TX_NUM";

	/**
	 * Field name for the transaction status.
	 */
	public static final String FIELD_NAME_STATUS			= "STATUS";

	/**
	 * Field name for the fee amount.
	 */
	public static final String FIELD_NAME_FEE				= "FEE";
	
	/**
	 * Field name for the amount available.
	 */
	public static final String FIELD_NAME_FUNDS_AVAILABLE	= "FUNDS_AVAILABLE";

	/**
	 * Field name for the amount needed to complete a transaction (for example, for load-send).
	 */
	public static final String FIELD_NAME_FUNDS_NEEDED		= "FUNDS_NEEDED";
	
	// CONSTRUCTORS
	
	/**
	 * Create and initialize with all attributes
	 * 
	 * @param name Name of metadata element
	 * @param type Type of element
	 * @param label Label for display in UI
	 * @param usage Indicator of type of display element
	 * @param value Value for element
	 * 
	 */
	public Metadata(Context context,
		String name, String type, String label, String usage, Object value )
		throws MetadataException
	{
		setContext(context);
		setName(name);
		setType(type);
		setLabel(label);
		setUsage(usage);
		setValue(value);
	}
	
	/**
	 * Create and initialize with label and usage
	 * 
	 * @param name Name of metadata element
	 * @param type Type of element
	 * @param label Label for display in UI
	 * @param usage Indicator of type of display element
	 * 
	 */
	public Metadata(Context context, String name, String type, String label, String usage )
		throws MetadataException
	{
		this(context, name, type, label, usage, null);
		setLabel(label);
	}
	
	/**
	 * Create and initialize with label
	 * 
	 * @param name Name of metadata element
	 * @param type Type of element
	 * @param label Label for display in UI
	 * 
	 */
	public Metadata(Context context, String name, String type, String label)
		throws MetadataException
	{
		this(context, name, type, label, null, null);
	}
	
	/**
	 * Create and initialize
	 * 
	 * @param name Name of metadata element
	 * @param type Type of element
	 * 
	 */
	public Metadata(Context context, String name, String type)
		throws MetadataException
	{
		this(context, name, type, null, null, null);
	}
	
	// SETTERS
	public void setContext(Context context)
	{
		this.context = context;	
	}
	public void setName(String name)
	{
		this.name = name;	
	}
	public void setType(String type)
		throws MetadataException
	{
		this.type = type;
		this.typeID = mapType(type);
	}
	public void setValue(Object value)
		throws MetadataException
	{
		this.value = value;	
	}
	public void setValueFromString(String stringValue)
	throws MetadataException
	{
		try
		{
			switch(typeID)
			{
			case FIELD_TYPE_ID_DATE:
				value = new Long(parseISO8601Date(stringValue));
				break;
			case FIELD_TYPE_ID_LONG:
				value = new Long(Long.parseLong(stringValue));
				break;
			case FIELD_TYPE_ID_INT:
				value = new Integer(Integer.parseInt(stringValue));
				break;
			case FIELD_TYPE_ID_IMAGE_PNG:
				value = Base64decoder.decode(stringValue);
				break;
			case FIELD_TYPE_ID_COLOR:
				if (stringValue.startsWith("#"))
				{
					stringValue = stringValue.substring(1);
				}
				value = stringValue;
				break;
			default:
				value = stringValue;
			}
		}
		catch(Exception e)
		{
			throw new MetadataException("Failed to convert value of type : " + type + " to a string.");
		}		
	}
	
	public void setLabel(String label)
	{
		this.label = label;	
	}
	
	public void setUsage(String usage)
		throws MetadataException
	{
		this.usageID = mapUsage(usage);
	}
	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
	}
	
	// GETTERS
	public int getTypeID()
	{
		return typeID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public int getUsageID()
	{
		return usageID;
	}
	
	public int getMaxLength()
	{
		return maxLength;
	}

	public String getRawValueString()
	{
		return value == null? null: value.toString();
	}
	public String getValueAsString()
		throws MetadataException
	{
		try
		{
			switch(typeID)
			{
			case FIELD_TYPE_ID_PHONE_NUMBER:
					// format the phone number
					return StringFormat.formatPhoneNumber(context, value.toString());
			case FIELD_TYPE_ID_DATE:
					// format the date/time
					return DateFormat.formatDate(
						((Long)value).longValue(), 
						DateFormat.TF_ALL_DATE | DateFormat.TF_HRS | DateFormat.TF_MINS);
			case FIELD_TYPE_ID_CURRENCY:
					return StringFormat.formatCurrency(context, value.toString());
			case FIELD_TYPE_ID_PIN:
				return StringFormat.mask(value.toString());
			default:
					return value.toString();
			}
		}
		catch(Exception e)
		{
			throw new MetadataException("Failed to convert value of type : " + type + " to a string. Value: " + value);
		}
		
	}
	public byte[] getValueAsByteArray()
		throws MetadataException
	{
		try
		{
			switch(typeID)
			{
			case FIELD_TYPE_ID_IMAGE_PNG:
				return (byte[])value;
			default:
				return value.toString().getBytes();
			}
		}
		catch(Exception e)
		{
			throw new MetadataException("Failed to convert value of type : " + type + " to a byte array. Value: " + value);
		}
	}
	
	// PRIVATE
	private int mapType(String type)
		throws MetadataException
	{
		int typeID =
			type == null? FIELD_TYPE_ID_RAW :
			FIELD_TYPE_PHONE_NUMBER.equals(type)? FIELD_TYPE_ID_PHONE_NUMBER : 
				FIELD_TYPE_AMOUNT.equals(type)? FIELD_TYPE_ID_AMOUNT : 				
					FIELD_TYPE_LONG.equals(type)? FIELD_TYPE_ID_LONG : 
						FIELD_TYPE_INT.equals(type)? FIELD_TYPE_ID_INT : 
							FIELD_TYPE_IMAGE_PNG.equals(type)? FIELD_TYPE_ID_IMAGE_PNG : 
								FIELD_TYPE_COLOR.equals(type)? FIELD_TYPE_ID_COLOR : 
									FIELD_TYPE_TEXT.equals(type)? FIELD_TYPE_ID_TEXT : 
										FIELD_TYPE_SCREEN.equals(type)? FIELD_TYPE_ID_SCREEN : 
											FIELD_TYPE_DATE.equals(type)? FIELD_TYPE_ID_DATE : 
												FIELD_TYPE_CURRENCY.equals(type)? FIELD_TYPE_ID_CURRENCY :
													FIELD_TYPE_PIN.equals(type)? FIELD_TYPE_ID_PIN :
														FIELD_TYPE_EMAIL_ADDR.equals(type)? FIELD_TYPE_ID_EMAIL_ADDR :
															FIELD_TYPE_IMAGE_URL.equals(type)? FIELD_TYPE_ID_IMAGE_URL : -1;
		
		if (typeID == -1) throw new MetadataException("Invalid MetadataType: " + type);
		return typeID;
	}
	private int mapUsage(String usage)
	throws MetadataException
{
	int usageID =
		usage == null? USAGE_ID_DEFAULT :
		USAGE_DISPLAY_NORMAL.equals(usage)? USAGE_ID_DISPLAY_NORMAL : 
			USAGE_DISPLAY_BOLD.equals(usage)? USAGE_ID_DISPLAY_BOLD : 
				USAGE_DISPLAY_HEADING.equals(usage)? USAGE_ID_DISPLAY_HEADING : 
					USAGE_INPUT.equals(usage)? USAGE_ID_INPUT : USAGE_ID_DEFAULT;
	
	if (usageID == -1) throw new MetadataException("Invalid MetadataType: " + usage);
	return usageID;
}
	
	/**
	 * Parses an ISO 8601 date string into a <code>long</code> (Java time value).
	 * (SOAP web services return a Date value in ISO 8601 format).
	 * @param str								the string with the ISO 8601 date.
	 * @return									a <code>long</code> with the converted Java time value.
	 * @throws IllegalArgumentException			If <code>date</code> is not in the correct format.		
	 */
	private static long parseISO8601Date (String str) throws IllegalArgumentException
	{
		/*
		 * From http://www.w3.org/TR/NOTE-datetime:
		 * Year:
		 * 	YYYY (eg 1997)
		 * Year and month:
		 * 	YYYY-MM (eg 1997-07)
		 * Complete date:
		 * 	YYYY-MM-DD (eg 1997-07-16)
		 * 	Complete date plus hours and minutes:
		 * YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
		 * 	Complete date plus hours, minutes and seconds:
		 * YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)
		 * 	Complete date plus hours, minutes, seconds and a decimal fraction of a second
		 * YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)
		 * where:
		 * YYYY = four-digit year
		 * MM   = two-digit month (01=January, etc.)
		 * DD   = two-digit day of month (01 through 31)
		 * hh   = two digits of hour (00 through 23) (am/pm NOT allowed)
		 * mm   = two digits of minute (00 through 59)
		 * ss   = two digits of second (00 through 59)
		 * s    = one or more digits representing a decimal fraction of a second
		 * TZD  = time zone designator (Z or +hh:mm or -hh:mm)
		 */
		String YYYY		= null;
		String MM		= null;
		String DD		= null;
		String hh		= null;
		String mm		= null;
		String ss		= null;
		String s		= null;
		long tzOffset	= 0;
		
		int len			= str.length();

		try
		{
			// parse the year
			YYYY = str.substring(0, 4);

			if (len > 4)
			{
				// parse the month
				MM = str.substring(5, 7);

				if (len > 7)
				{
					// parse the date
					DD = str.substring(8, 10);
			
					if (len > 10)
					{
						// parse the hour
						hh = str.substring(11, 13);
			
						if (len > 13)
						{
							// parse the minute
							mm = str.substring(14, 16);
							
							if (':' == str.charAt(16))
							{
								// parse the second
								ss = str.substring(17, 19);
								
								if ('.' == str.charAt(19))
								{
									int j = 20;
									for (char c = str.charAt(j); Character.isDigit(c) && j < len; j++)
									{
										if (null == s)
										{
											s = String.valueOf(c);
										}
										else
										{
											s += c;
										}
									}

									// parse the time zone
									String tzd = str.substring(j, len);
									tzOffset = parseTimeZoneOffset(tzd);
								}
								else
								{
									// parse the time zone
									String tzd = str.substring(19, len);
									tzOffset = parseTimeZoneOffset(tzd);
								}
							}
							else
							{
								// parse the time zone
								String tzd = str.substring(16, len);
								tzOffset = parseTimeZoneOffset(tzd);
							}
						}
					}
				}
			}
			
			int year			= Integer.parseInt(YYYY);
            int month			= (MM != null) ? Integer.parseInt(MM) - 1: 0;
            int date			= (DD != null) ? Integer.parseInt(DD) : 0;
            int hour			= (hh != null) ? Integer.parseInt(hh) : 0;
            int minute			= (mm != null) ? Integer.parseInt(mm) : 0;
            int second			= (ss != null) ? Integer.parseInt(ss) : 0;
            int millisecond		= (s != null) ? Integer.parseInt(s) * 10 : 0;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONDAY, month);
			cal.set(Calendar.DATE, date);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			cal.set(Calendar.SECOND, second);
			cal.set(Calendar.MILLISECOND, millisecond);
			
			return cal.getTime().getTime() + tzOffset;
		}
		catch (IndexOutOfBoundsException ioobe)
		{
			throw new IllegalArgumentException();
		}
		catch (NumberFormatException nfe)
		{
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Convenience method to parse the time value offset 
	 * for a given time zone designator.
	 * @param tzd								The time zone designator, per ISO 8601. 
	 * @return									The number of milliseconds to offset from GMT for <code>tzd</code>.
	 */
	private static long parseTimeZoneOffset (String tzd)
	{
		// If the default time zone is GMT or UTC, then don't offset the time.
		// This is a hack until we properly support setting time zones on handsets that do not have time zone support).
		// The hack ensures that the displayed time matches what's displayed in the web app and WAP
		// (which don't offset the time).
		TimeZone localZone = TimeZone.getDefault();
		
		String id = localZone.getID();
		if (localZone != null && !"GMT".equals(id) && !"UTC".equals(id))
		{
			// The time zone designator has the format Z or +hh:mm or -hh:mm 
			int i = tzd.indexOf(':');
			if (i > 0)
			{
				try
				{
					String hh = tzd.substring(1, 3);
					String mm = tzd.substring(4, 6);
					
					int hours = Integer.parseInt(hh);
					int minutes = Integer.parseInt(mm);
					
					long offset = hours * 3600000 + minutes * 60000;
					return (tzd.charAt(0) == '-') ? offset : -offset;	// if we are behind GMT, then add the time offset to convert to GMT.
				}
				catch (Exception e)
				{
//#ifdef build.debug
					e.printStackTrace();
//#endif
				}
			}
		}

		return 0;
	}
	
	/**
	 * Convenience factory method for creating a heading
	 * metadata element.
	 * @param text	Text for heading
	 * @return
	 */
	public static Metadata createHeading(String text)
	{
		try
		{
			Metadata metadata = new Metadata(
					null, 
					FIELD_TYPE_TEXT, 
					null,
					USAGE_DISPLAY_HEADING); 
			metadata.setValue(text);
			return metadata;
		}
		catch(MetadataException e)
		{
			return null;
		}
	}
	/**
	 * Convenience factory method for creating a text element
	 * with normal display attribute
	 * metadata element.
	 * @param text	Text to be displayed
	 * @param label Optional label text
	 * @return
	 */
	public static Metadata createText(String text, String label)
	{
		try
		{
			Metadata metadata = new Metadata(
					null, 
					FIELD_TYPE_TEXT, 
					label,
					USAGE_DISPLAY_NORMAL); 
			metadata.setValue(text);
			return metadata;
		}
		catch(MetadataException e)
		{
			return null;
		}
	}
	/**
	 * Convenience factory method for creating a spacer
	 * metadata element.
	 * @return
	 */
	public static Metadata createSpacer(Context context)
	{
		try
		{
			Metadata metadata = new Metadata(context,
					FIELD_SPACER_NAME, 
					FIELD_TYPE_TEXT, 
					null,
					USAGE_DISPLAY_NORMAL); 
			metadata.setValue("");
			return metadata;
		}
		catch(MetadataException e)
		{
			return null;
		}
	}
	/**
	 * Convenience factory method for creating a text element
	 * with normal display attribute. Format type can be
	 * specified
	 * @param text	Text to be displayed
	 * @param label Optional label text
	 * @param type  Format type
	 * @param usage Usage type
	 * @return
	 */
	public static Metadata createText(String text, String label, String type, String usage)
	{
		try
		{
			Metadata metadata = new Metadata(
					null, 
					type, 
					label,
					usage); 
			metadata.setValue(text);
			return metadata;
		}
		catch(MetadataException e)
		{
			return null;
		}
	}
	/**
	 * Convenience factory method for creating an input metadata
	 * element.
	 * @param text Text for pre-filling
	 * @param label Optional label text
	 * @param type Optional typeID 
	 * @param maxLength Optional maximum length (-1 for no max)
	 * @return
	 */
	public static Metadata createInput(String text, String label, String type, int maxLength)
	{
		try
		{
			Metadata metadata = new Metadata(
					null, 
					type == null? FIELD_TYPE_TEXT : type, 
					label,
					USAGE_INPUT,
					text); 
			metadata.setMaxLength(maxLength);
			return metadata;
		}
		catch(MetadataException e)
		{
			return null;
		}
	}
}

