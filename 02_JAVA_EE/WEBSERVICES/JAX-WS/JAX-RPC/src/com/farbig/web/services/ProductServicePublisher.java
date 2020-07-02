package com.farbig.web.services;

import javax.xml.ws.Endpoint;

public class ProductServicePublisher {

	public static void main(String[] args) {

		Endpoint.publish("http://localhost:80/services/product", new ProductWebService());

	}

}
