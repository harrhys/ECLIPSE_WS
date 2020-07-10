package com.farbig.rs.services;
import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
 
@Path("/file/download")
public class FileDownloadService {
 	private static final String FILE_PATH = "D:\\TUTORIALS\\JAVA\\ejb.pdf";
 
	@GET
	@Path("/pdf")
	@Produces("application/pdf")
	public Response getFile() {
 
		File file = new File(FILE_PATH);
 
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition","attachment; filename=\"javatpoint_pdf.pdf\"");
		return response.build();
 
	}
 }