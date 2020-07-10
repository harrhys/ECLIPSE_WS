package com.farbig.practice.resource.bundle;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			ResourceBundle resource = ResourceBundle.getBundle("com.farbig.practice.resource.bundle.test",Locale.ENGLISH);
			Enumeration keys= resource.getKeys();
			while(keys.hasMoreElements())
			{
				String key = (String) keys.nextElement();
				String value = resource.getString(key);
				System.out.println(key + value);
			}
			resource = ResourceBundle.getBundle("com.farbig.practice.resource.bundle.test",Locale.FRANCE);
			keys= resource.getKeys();
			while(keys.hasMoreElements())
			{
				String key = (String) keys.nextElement();
				String value = resource.getString(key);
				System.out.println(key + value);
			}
	}

}
