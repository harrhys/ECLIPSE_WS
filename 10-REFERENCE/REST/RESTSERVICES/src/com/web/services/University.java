package com.web.services;


import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/university")
public class University {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getInfo()
	{
		return "</b>THIS IS BOLD<b>";
	}
	
	@PUT
	@Path("/rollnos/{rollno1}-{rollno2}")
	@Produces(MediaType.TEXT_HTML)
	public String updateStudentInfo(@PathParam("rollno1") String rollno1)
	{
		return "<b>STUDENT INFO IS "+rollno1;
	}
	


}
