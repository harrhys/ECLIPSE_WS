package com.farbig.services.ws.product;

import java.util.List;

import javax.jws.WebService;

@WebService(serviceName="catalogservices",portName="catalogport",targetNamespace="http://farbig.com/services/product",
endpointInterface="com.farbig.services.ws.product.ProductCatalogService")
public class MySOAPService implements ProductCatalogService{
	
	ProductCatalogService service = new ProductCatalogServiceImpl();

	public List<String> getProductCatalogs() {
		
		return service.getProductCatalogs();
	}

	public List<String> addProductCatalog(String a) {

		return service.addProductCatalog(a);
	}

	public List<String> removeProductCatalog(String a) {
		
		return service.removeProductCatalog(a);
	} 

}
