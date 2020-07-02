package com.farbig.rs.client;

import com.farbig.rs.services.transferobjects.Product;
import com.farbig.rs.util.JsonHelper;
import com.farbig.rs.util.RestUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ProductClient {

	public static void main(String[] args) throws Exception {

		testGet();

		testPost();

	}

	public static void testGet() throws Exception {

		RestUtil util = new RestUtil();

		util.baseURL("http://localhost:7001/RESTSERVICES");
		util.path("product/get");
		String response = util.getApplicationJson();
		util.disconnect();
		System.out.println("Get Response - " + response);

		JsonHelper helper = new JsonHelper();
		System.out.println(helper.stringToBean("Bean - " + response));
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
		JsonElement jsonElemenet = parser.parse(response);
		System.out.println("Json Element - " + jsonElemenet);

	}

	public static void testPost() throws Exception {

		RestUtil util = new RestUtil();

		util.baseURL("http://localhost:7001/RESTSERVICES");
		util.path("product/postProduct");

		Product p = new Product();
		p.setName("iPad");
		p.setQty(100);
		String request = util.JsonString(p);
		System.out.println("Request - " + request);
		String response = util.postNGetApplicationJson(request);
		util.disconnect();
		System.out.println("Post Response - " + response);

		p = util.JsonObject(response, Product.class);
		System.out.println("product - " + p);
		
		JsonObject jsonObject = (JsonObject) new JsonParser().parse(response);
		System.out.println("Json Object - " + jsonObject);
		
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
		JsonElement jsonElement = parser.parse(response);
		System.out.println("Json Element - " + jsonElement);

	}
}
