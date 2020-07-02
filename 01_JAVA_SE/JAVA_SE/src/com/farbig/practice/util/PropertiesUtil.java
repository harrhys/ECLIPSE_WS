package com.farbig.practice.util;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtil {

	public static void main(String[] args) throws Exception {

		FileReader reader = new FileReader("system.properties");

		Properties p = new Properties();
		p.load(reader);

		System.out.println(p.getProperty("user"));
		System.out.println(p.getProperty("password"));

		Set<Entry<Object, Object>> set = p.entrySet();
		Iterator<Entry<Object, Object>> itr = set.iterator();
		
		while (itr.hasNext()) {
			Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) itr.next();
			System.out.println(entry.getKey() + " = " + entry.getValue());

		}
		
	}
}
