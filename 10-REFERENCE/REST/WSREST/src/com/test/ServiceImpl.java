package com.test;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.sun.jersey.spi.resource.Singleton;


 
@Produces("application/xml")
@Path("myservices")
@Singleton
public class ServiceImpl {
	
	private static ArrayList names = new ArrayList();
	
	
 
	 
 
	@GET
	public String sayHello(@PathParam("name")String name)
	{
		/*names.add(name);
		return names;*/
		
		return name;
	}
	
	 

}
