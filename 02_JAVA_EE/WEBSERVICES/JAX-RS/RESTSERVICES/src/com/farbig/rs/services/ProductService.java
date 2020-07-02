package com.farbig.rs.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.farbig.rs.services.transferobjects.Product;

@Path("/product")
public class ProductService {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProductInJSON() {

		Product product = new Product();
		product.setName("iPad 3");
		product.setQty(999);

		return product;

	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProduct(Product product) {

		String result = "Product created : " + product;

		System.out.println(result);

		return Response.status(Status.CREATED).entity(result).build();
	}

	@POST
	@Path("/postProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Product createProductInJSON(Product product) {

		String result = "Product created : " + product;

		System.out.println(result);

		return product;
	}

	@PUT
	@Path("/put")
	@Consumes("application/json")
	public Response updateProduct(Product product) {

		String result = "Product updated : " + product;
		
		System.out.println(result);
		
		return Response.status(Status.CREATED).entity(result).build();
	}
	
	@POST
	@Path("/put")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Product updateProductInJSON(Product product) {

		String result = "Product updated : " + product;
		
		System.out.println(result);
		
		return product;

	}

}