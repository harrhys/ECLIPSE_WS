 
package com.test;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("rs")
@Produces({"text/html", "text/plain" })
public class MYRS {
	/**
     * Default constructor. 
     */
    public MYRS() {
        // TODO Auto-generated constructor stub
    }


    /**
     * Retrieves representation of an instance of MYRS
     * @return an instance of String
     */
	@GET
	@Produces("text/plain")
	public String resourceMethodGET() { 
		// TODO Auto-generated method stub
		return "success0";
	}

	/**
     * PUT method for updating or creating an instance of MYRS
     * @content content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
	@PUT
	@Consumes("text/plain")
	public void resourceMethodPUT(String content) { 
		// TODO Auto-generated method stub
		
	}
}