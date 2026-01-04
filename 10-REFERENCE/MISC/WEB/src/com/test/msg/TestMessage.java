package com.test.msg;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestMessage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ResourceBundle resource = ResourceBundle.getBundle("com.test.msg.test",Locale.FRENCH);
			Enumeration keys= resource.getKeys();
			while(keys.hasMoreElements())
			{
				String key = (String) keys.nextElement();
				String value = resource.getString(key);
				System.out.println(key + value);
			}
	}

}
