package com.farbig.web.services.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;

import com.farbig.web.services.ProductService;

public class DynamicProxy {

	public static void main(String[] args) throws Exception {

		try {

			String UrlString = "http://localhost:80/services/product?WSDL";
			String nameSpaceUri = "productNameSpace";
			String serviceName = "ProductServices";
			String portName = "ProductServicePort";

			URL wsldUrl = new URL(UrlString);

			ServiceFactory serviceFactory = ServiceFactory.newInstance();

			Service productService = serviceFactory.createService(wsldUrl, new QName(nameSpaceUri, serviceName));

			ProductService myProxy = (ProductService) productService.getPort(new QName(nameSpaceUri, portName),
					ProductService.class);

			System.out.println(myProxy.addProduct("DP Product"));

			// System.out.println(myProxy.addProduct("my test product").toString());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
