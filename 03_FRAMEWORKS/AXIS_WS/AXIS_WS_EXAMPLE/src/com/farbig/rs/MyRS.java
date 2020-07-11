package com.farbig.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/myrs")
public class MyRS {
	
	@GET
	public String getResponse()
	{
		return "Hello";
	}

}
