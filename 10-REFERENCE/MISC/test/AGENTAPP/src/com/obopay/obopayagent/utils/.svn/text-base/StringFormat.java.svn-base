package com.obopay.obopayagent.utils;

import android.content.Context;

import com.obopay.obopayagent.R;

/**
 * String formatting methods.
 * @author		<a href=mailto:larry@obopay.com>Larry Wang</a>
 */
public abstract class StringFormat
{
	/**
	 * Formats a numeric string into a phone number (U.S. only).
	 * @param str					the numeric string to format.
	 * @return						the phone number string. The format is <code>(___) ___-____</code>.
	 */
	public static final String formatPhoneNumber (Context context, String str)
	{
		int[] phoneNumberIndices = getPhoneFormatIndices(context);
		String phoneNumberDisplay = getPhoneNumberFormat(context);
		
		String countryCode = context.getString(R.string.country_code);
		if(str.startsWith(countryCode)&&str.length()==12)
		{
			str = str.substring(countryCode.length());
		}
		
		// Assumes that str contains only digits
		StringBuffer buf = new StringBuffer(phoneNumberDisplay);
		try
		{
			int len = phoneNumberIndices.length;
			for(int i=0; i<len; i++)
			{
				buf.setCharAt(phoneNumberIndices[i], str.charAt(i));
			}			
		}
		catch (Exception e)
		{
//#ifdef build.debug
//#			e.printStackTrace();
//#endif
		}
		return buf.toString();
	}

	/**
	 * Formats a numeric string into a currency value.
	 * @param str					the numeric string to format.
	 * @return						the currency value. 
	 * 								For now, The method simply adds a dollar sign to a given numeric string.
	 */
	public static final String formatCurrency (Context context, String str)
	{
		if (null == str)
		{
			return null;
		}

		if(str.equals("Free"))
		{
			return str;
		}
		
		StringBuffer buf = new StringBuffer(8);
		if (str.startsWith("-"))
		{
			buf.append('-');
			str = str.substring(1);
		}

		String currencySymbol = context.getString(R.string.currency);
		
		if (!str.startsWith(currencySymbol))
		{			
			buf.append(currencySymbol);
			buf.append(" ");
		}
		buf.append(str);

		// pad the amount string if necessary.
		int i = str.indexOf('.');
		if (-1 == i)
		{
			buf.append(".00");
		}
		else if (i == str.length() - 2)
		{
			buf.append('0');
		}
		return buf.toString();
	}
	/**
	 * Masks all characters in a string with asterisks
	 * @param str The string to mask
	 * @return String of asterisks, same length as input string
	 */
	public static String mask(String str)
	{
		String outString = "";
		if (str == null) return outString;
		for (int i = 0; i < str.length(); i++)
		{
			outString += "*";
		}
		return outString;
	}

	/**
	 * Formats a string, replacing whitespace tokens (<code>'\n'</code>, <code>'\r'</code>)
	 * with their ascii value.
	 * @param str				the string to format
	 * @return					the formatted string; <code>null</code> if <code>str</code> is <code>null</code>.
	 */
	public static final String formatString (String str)
	{
		return formatString(str, (String[])null);
	}
	
	/**
	 * Convenience method for <code>format</code> with one argment.
	 * @param fmt				the format string.
	 * 							The method replaces <code>%#</code> (<code>%1</code>, <code>%2</code>, <code>%3</code>, etc.) 
	 * 							in the format string with the supplied string values
	 * @param str				the string argument
	 * @return					the formatted string; <code>null</code> if <code>fmt</code> is <code>null</code>.
	 * @see #format(String, String[])
	 */
	public static final String formatString (String fmt, String str)
	{
		String[] arr = 
		{
			str
		};
		
		return formatString(fmt, arr);
	}
	
	/**
	 * String token replacement. Basically a poor man's version of <code>printf</code>.
	 * The assumption is that it's used infrequent enough, the strings are small enough, and
	 * it runs fast enough for our purposes.
	 * @param fmt				the format string.
	 * 							The method replaces <code>%0</code>, <code>%1</code>, <code>%2</code>, etc. 
	 * 							in the format string with the supplied string values. The same parameter may be reused in multiple substitutions.
	 * 							It replaces <code>%%</code> with <code>%</code>. If there is no argument that corresponds 
	 * 							to a token, the that token is unchanged.
	 * 							It also replaces escaped whitepspace values ('\r', '\n', '\t') with their corresponding whitespace characters. 
	 * @param args				the string arguments
	 * @return					the formatted string; <code>null</code> if <code>fmt</code> is <code>null</code>.
	 */
	public static final String formatString (String fmt, String[] args)
	{
		if (null == fmt)
		{
//#ifdef build.debug
			System.out.println("formatString: fmt is null!");
			System.out.println("");
//#endif
			return null;
		}
		StringBuffer buf = new StringBuffer(fmt.length() + 512); // assume the formatted string is longer
		for (int i = 0; i < fmt.length(); i++)
		{
			char c = fmt.charAt(i);			
			if ('%' == c)
			{
				try
				{
					StringBuffer bufNum = new StringBuffer(2);
					int j = i + 1;
					for (;
						 j < fmt.length() && Character.isDigit(fmt.charAt(j)); 
						 j++)
					{
						bufNum.append(fmt.charAt(j));
					}
					int index = Integer.parseInt(bufNum.toString());
					if (args[index] != null)
					{
						buf.append(args[index]);
					}
					i = j - 1;
				}
				catch (Exception e)
				{
					buf.append('%');
				}
			}
			else if ('\\' == c)
			{
				i++;
				if (i < fmt.length())
				{
					switch (fmt.charAt(i))
					{
					case 'r':
						buf.append('\r');
						break;
					case 'n':
						buf.append('\n');
						break;
					case 't':
						buf.append('\t');
						break;
					}
				}
			}
			else
			{
				buf.append(c);
			}
		}		
		return buf.toString();
	}
	
	/**
	 * Formats the phone number display and stores the indices of the digits
	 */
	private static int[] getPhoneFormatIndices(Context context) {
		String format = context.getString(R.string.phone_number);
		
		int length = format.length();
		char[] s = format.toCharArray();
		int[] indices = new int[length+1];
		
		int j = 0;
		
		for(int i=0; i<length; i++)
		{
			if(s[i] == 'x') {
				indices[j] = i; j++;
			}
		}
		indices[j] = length;
		int[] phoneFormatIndices = new int[j+1];
		System.arraycopy(indices, 0, phoneFormatIndices, 0, j+1);
		indices = null;
		return phoneFormatIndices;
	}
	
	/**
	 * Formats the phone number display and stores the indices of the digits
	 */
	private static String getPhoneNumberFormat(Context context)
	{
		String format = context.getString(R.string.phone_number);
		int length = format.length();
		return format.replace('x', ' ');
	}
	

}
