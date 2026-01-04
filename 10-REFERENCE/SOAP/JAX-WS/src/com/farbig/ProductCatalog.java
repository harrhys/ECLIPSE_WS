package com.farbig;

import java.util.List;

import javax.jws.WebService;

@WebService
public class ProductCatalog {

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
