package com.farbig.rest.services;

import java.util.List;

import javax.ws.rs.Path;

@Path("product")
public class MyRestService {
	
	ProductCatalogService service = new ProductCatalogServiceImpl();

	public List<String> getProductCatalogs() {
		
		return service.getProductCatalogs();
	}

	public List<String> addProduct(String a) {

		return service.addProduct(a);
	}

	public List<String> removeProduct(String a) {
		
		return service.removeProduct(a);
	} 

}
