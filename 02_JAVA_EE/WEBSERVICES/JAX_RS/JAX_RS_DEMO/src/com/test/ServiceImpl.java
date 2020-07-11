package com.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

 
@Produces("application/xml")
@Path("myservices")
public class ServiceImpl {
	
	@GET
	@Produces("text/html")
	public String sayHello(@PathParam("name")String name)
	{
		return name;
	}
	
	 

}
