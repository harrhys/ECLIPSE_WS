package com.farbig.web.services;

import java.util.List;

import javax.jws.WebService;

@WebService(portName = "ProductService", 
name = "ProductWebService", serviceName = "ProductServices")
public class ProductWebService implements ProductService {

	ProductBusinessService service;

	public ProductWebService() {

		service = new ProductBusinessService();
	}

	public List<String> getProducts() {
		return service.getProducts();
	}

	public List<String> addProduct(String a) {
		return service.addProduct(a);
	}

	public List<String> removeProduct(String a) {
		return service.removeProduct(a);
	}

	public List<String> removeOldProduct() {

		return service.removeOldProduct();
	}

}
