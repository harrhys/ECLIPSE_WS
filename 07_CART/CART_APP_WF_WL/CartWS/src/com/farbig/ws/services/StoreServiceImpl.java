package com.farbig.ws.services;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.farbig.ws.objects.Product;
import com.farbig.ws.objects.Store;

@WebService(portName = "StoreServicePort", serviceName = "StoreServices", targetNamespace = "http://localhost:7001/Services/StoreServices")
//@HandlerChain(file = "com/farbig/ws/handlers/handler-chain.xml")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.BARE)
public class StoreServiceImpl {

	public Store CreateStoreEntity(Store store) {

		System.out.println("Created Store N Products Successfully");
		store.setName("Name:" + store.getName());
		return store;
	}

	public Product CreateProductEntity(Product product) {

		System.out.println("Created Product Successfully");
		product.setCode("Code:" + product.getCode());
		return product;
	}

	public String CreateTestProduct(String product) {

		System.out.println("Created Product Successfully");

		return product;

	}
}
