package com.farbig.business.services;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface ProductCatalogService {

	public List<String> getProductCatalogs();

	public List<String> addProduct(String a);;

	public List<String> removeProduct(String a);;

}
