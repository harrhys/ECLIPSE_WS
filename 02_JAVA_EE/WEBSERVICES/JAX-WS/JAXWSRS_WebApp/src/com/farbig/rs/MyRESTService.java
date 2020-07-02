 
package com.farbig.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("MyRESTService")
public class MyRESTService {
	/**
     * Default constructor. 
     */
    public MyRESTService() {
        // TODO Auto-generated constructor stub
    }


    /**
     * Retrieves representation of an instance of MyRESTService
     * @return an instance of String
     */
	@GET
	@Path("/myget")
	@Produces({"text/plain", "text/html" })
	public String resourceMethodGET() { 
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/**
     * PUT method for updating or creating an instance of MyRESTService
     * @content content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
	@PUT
	@Path("/myput")
	@Consumes("text/plain")
	public void resourceMethodPUT(String content) { 
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}