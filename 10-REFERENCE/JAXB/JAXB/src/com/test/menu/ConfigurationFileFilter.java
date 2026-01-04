package com.test.menu;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class ConfigurationFileFilter implements FileFilter{
	public final static String SUFFIX_XML = ".xml";
	private ArrayList suffix_list = null;
	
	public ConfigurationFileFilter() {
		suffix_list = new ArrayList();
		suffix_list.add(SUFFIX_XML);
	}
	
	public ConfigurationFileFilter(ArrayList list) {
		suffix_list = list;
	}
	
	public boolean accept(File pathname) {
		boolean retval = false;
		String name = null;
		if (pathname!=null && (name=pathname.getName())!=null) {
			if( suffix_list!=null && suffix_list.size()!=0 ) 
				for ( int i=0; i<suffix_list.size(); i++ ) {
					retval = name.endsWith((String)suffix_list.get(i));
					if (retval) break;
				}
		}
		
		return retval;
	}
}
