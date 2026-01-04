package com.farbig;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalogServiceImpl implements ProductCatalogService{
	
	List<String> productCatalogs = new ArrayList<String>();
	
	public List<String> getProductCatalogs()
	{
		productCatalogs.add("Books");
		productCatalogs.add("Movies");
		productCatalogs.add("Music");
		return productCatalogs;
	}
	
	public List<String> addProduct(String a)
	{
		productCatalogs.add(a);
		
		return productCatalogs;
	}
	
	public List<String> removeProduct(String a)
	{
		productCatalogs.remove(a);
		
		return productCatalogs;
	}

}
