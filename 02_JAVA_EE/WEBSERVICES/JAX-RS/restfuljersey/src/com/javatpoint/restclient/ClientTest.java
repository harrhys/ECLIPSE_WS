package com.javatpoint.restclient;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;

public class ClientTest {
	public static void main(String[] args) {
		/*
		 * 
		 * System.out.println(MediaType.TEXT_HTML); ClientConfig config = new
		 * ClientConfig(); Client client = ClientBuilder.newClient(config); WebTarget
		 * target = client.target(getBaseURI());
		 * 
		 * System.out.println(target.path("rest").path("hello").request().accept(
		 * MediaType.TEXT_PLAIN).get(String.class));
		 * System.out.println(target.path("rest").path("hello").request().accept(
		 * MediaType.TEXT_XML).get(String.class));
		 * System.out.println(target.path("rest").path("hello").request().accept(
		 * MediaType.TEXT_HTML).get(String.class));
		 */}

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:4444/restfuljersey").build();
  }
} 