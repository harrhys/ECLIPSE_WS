package com.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sun.jersey.spi.resource.Singleton;


 
@Produces("application/xml")
@Path("myservices")
@Singleton
public class ServiceImpl {
	
	@GET
	@Produces("text/html")
	public String sayHello(@PathParam("name")String name)
	{
		return name;
	}
	
	 

}
