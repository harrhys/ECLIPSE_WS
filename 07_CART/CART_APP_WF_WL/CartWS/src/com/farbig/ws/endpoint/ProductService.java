package com.farbig.ws.endpoint;

import javax.xml.ws.Holder;

import com.farbig.ws.objects.Product;

public interface ProductService {

	
	public Product CreateTestProduct(Holder<String> partnerId, Product product);

}
