package com.farbig.web.services;

import java.rmi.Remote;
import java.util.List;

public interface ProductService extends Remote{

	public List<String> getProducts();

	public List<String> addProduct(String a);

	public List<String> removeProduct(String a);

	public List<String> removeOldProduct();
	
}
