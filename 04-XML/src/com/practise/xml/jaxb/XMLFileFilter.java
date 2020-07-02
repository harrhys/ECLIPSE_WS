package com.practise.xml.jaxb;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class XMLFileFilter implements FileFilter{
	
	public final static String SUFFIX_XML = ".xml";
	
	private ArrayList<String> suffix_list = null;
	
	public XMLFileFilter() {
		suffix_list = new ArrayList<String>();
		suffix_list.add(SUFFIX_XML);
	}
	
	public XMLFileFilter(ArrayList<String> list) {
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
