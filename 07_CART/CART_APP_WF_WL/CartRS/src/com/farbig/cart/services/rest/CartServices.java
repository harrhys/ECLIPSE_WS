package com.farbig.cart.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/cart")
public class CartServices {


	@GET
	@Path("/plain")
	@Produces("text/html")
	public String getProductCategories() {
		return "Hello..";
	}

	@GET
	@Path("/query")
	public String sayHello2(@QueryParam("name") String name, @QueryParam("age") String age) throws Exception {

		return "Hello.." + name + age;
	}
	
	@GET
	@Path("/{name}/{age}")
	public String sayHello4(@PathParam("name") String name, @PathParam("age") String age) {
		return "Hello.." + name + age ;
	}

	@GET
	@Path("/matrix")
	public String sayHello3(@MatrixParam("name") String name, @MatrixParam("age") String age) {
		return "Hello.." + name + age;
	}

	

}
