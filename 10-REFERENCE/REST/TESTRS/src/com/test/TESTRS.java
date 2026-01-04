package com.test;

import java.util.ArrayList;
import java.util.List;

public class TESTRS {
	
	List<String> productCatalogs = new ArrayList<String>();
	
	public List<String> getProductCatalogs()
	{
		productCatalogs.add("Books");
		productCatalogs.add("Movies");
		productCatalogs.add("Music");
		return productCatalogs;
	}

}
