package com.farbig.soap.services;

import java.util.List;

import javax.jws.WebService;

import com.farbig.business.services.ProductCatalogService;
import com.farbig.business.services.ProductCatalogServiceImpl;

@WebService(serviceName="catalogservices",portName="catalogport",targetNamespace="farbig.com.soap.services",
endpointInterface="com.farbig.business.services.ProductCatalogService")
public class MySOAPService implements ProductCatalogService{
	
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
