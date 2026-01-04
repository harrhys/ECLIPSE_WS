package com.web.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
 
@Path("/product")
public class JSONProductService {
 
	@GET
	@Path("/get")
	@Produces("application/json")
	public Product getProductInJSON() {
 
		Product product = new Product();
		product.setName("iPad 3");
		product.setQty(999);
 
		return product; 
 
	}
 
	@POST
	@Path("/post")
	@Consumes("application/json")
	public Response createProductInJSON(Product product) {
 
		String result = "Product created : " + product;
		return Response.status(201).entity(result).build();
 
	}
	
	@PUT
	@Path("/put")
	@Consumes("application/json")
	public Response updateProductInJSON(Product product) {
 
		String result = "Product updated : " + product;
		return Response.status(201).entity(result).build();
 
	}
 
 
}