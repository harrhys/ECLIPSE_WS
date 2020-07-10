package com.farbig.web.services;

import java.util.ArrayList;
import java.util.List;

public class ProductBusinessService implements ProductService {

	List<String> products;

	public ProductBusinessService() {

		products = new ArrayList<String>();
		addProduct("Book");
		addProduct("Movie");
		addProduct("Music");
	}

	public List<String> getProducts() {
		
		return products;
	}

	public List<String> addProduct(String a) {
		
		System.out.println("Adding Product : "+a);
		products.add(a);
		return products;
	}

	public List<String> removeProduct(String a) {
		
		System.out.println("Removing Product : "+a);
		products.remove(a);
		return products;
	}
	
	@Override
	public List<String> removeOldProduct() {
		
		System.out.println("Removing Old Product : "+products.get(0));
		products.remove(0);
		return products;
	}

}
