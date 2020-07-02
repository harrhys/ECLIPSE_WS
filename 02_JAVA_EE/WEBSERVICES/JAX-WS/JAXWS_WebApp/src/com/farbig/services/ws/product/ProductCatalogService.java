package com.farbig.services.ws.product;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface ProductCatalogService {

	public List<String> getProductCatalogs();

	public List<String> addProductCatalog(String a);;

	public List<String> removeProductCatalog(String a);;

}
