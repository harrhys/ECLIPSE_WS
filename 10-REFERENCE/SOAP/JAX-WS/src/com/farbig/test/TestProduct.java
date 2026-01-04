package com.farbig.test;

import javax.xml.ws.Endpoint;

import com.farbig.ProductCatalog;

public class TestProduct {

	public static void main(String[] args) {

		Endpoint.publish("http://localhost:1234/testproduct",
				new ProductCatalog());

	}

}
