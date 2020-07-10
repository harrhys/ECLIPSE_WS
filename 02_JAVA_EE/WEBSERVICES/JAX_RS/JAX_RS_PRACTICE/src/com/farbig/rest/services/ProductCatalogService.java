package com.farbig.rest.services;

import java.util.List;

public interface ProductCatalogService {

	public List<String> getProductCatalogs();

	public List<String> addProduct(String a);;

	public List<String> removeProduct(String a);;

}
