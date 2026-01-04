/**
  Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
*/

package samples.jdbc.blob.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

import samples.jdbc.blob.utils.*;

/**
 * The servlet accepts a <code>multipart/formdata</code> encoded <code>POST</code> request from
 * the client and stores the uploaded file as a blob in the <code>BLOB_REPOSITORY</code> table.
 */
public class UploadServlet extends HttpServlet
{
	private final int DEFAULT_CHUNKSIZE = 1024;
	private final boolean DEFAULT_USE_BLOB  = false;

	/**
	 * The boundary string used to enclose the multipart data being posted.
	 */
    protected String boundary = null;

    /**
     * The total size of the information being posted to the servlet.
     */
    protected int contentLength = 0;

    /**
     * The running total of how many bytes have been read in by the servlet.
     */
    protected int totalBytesRead = 0;

    /**
     * The uploaded file's content or mimetype.  This defaults to <code>text/html</code>.
     */
    protected String fileContentType = "text/html";	// Default

    /**
     * Holder for the name of the file being uploaded.
     */
    protected String filename = null;

    /**
	 * The internal buffer size used for streaming data between the database, servlet and client.
	 * The buffer size can be set via the <code>chunkSize</code> initialization parameter.
	 */
	protected int chunkSize = DEFAULT_CHUNKSIZE;

	/**
	 *
	 */
	protected boolean useBlob = DEFAULT_USE_BLOB;


	/**
	 * Initialises the servlet and checks for the <code>chunkSize</code> initialization parameter.
	 *
	 * @param config servlet configuration object used by a servlet container to pass information
	 *               to a servlet during initialization.
	 */
    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);

        String chunkStr = config.getInitParameter("chunkSize");
        try
        {
        	this.chunkSize = Integer.parseInt(chunkStr);
		}
		catch (Exception ex)
		{
			this.chunkSize = DEFAULT_CHUNKSIZE;
		}

		if (chunkSize <= 0)
		{
			this.chunkSize = DEFAULT_CHUNKSIZE;
		}

		useBlob = Boolean.valueOf(config.getInitParameter("useBlob")).booleanValue();
    }


	/**
	 * Accepts the <code>multipart/formdata</code> and stores the uploaded file as a blob in the
	 * <code>BLOB_REPOSITORY</code> table.  Note that this method is limited in that it cannot handle
	 * arbitrary multipart uploads.  That is it expects the only content of the request to be the file
	 * and its associated information.  Adding addition fields to the <code>POST</code> data will either
	 * break the Servlet or be ignored depending on whether the new fields come before or after the file
	 * information.
	 *
	 * @param request an HttpServletRequest object that contains the client's request to the servlet
	 * @param response an HttpServletReponse object that contains the servlet's response to the client
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 * @throws ServletException if the request for the <code>POST</code> could not be handled
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, java.io.IOException
    {
		reset();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Upload Result</title></head>");
        out.println("<body><h3><center><font face=\"Arial, Helvetica\">");
        out.println("Upload Result</font></center></h3><hr>");

		// Start parsing the request.
        readHeaderData(request);

        ServletInputStream sis = request.getInputStream();

		// Parse the multipart information about the file being uploaded
        readPartHeader(sis);

        out.println("<b>Filename being uploaded:</b>"+filename+"<br>");
        out.println("<b>File mimetype:</b>"+fileContentType+"<br>");

		// Work this out now as we need the total size before we read all the data
		// Content Length includes the following after the file data:
		// \r\nboundarystring--\r\n
		// i.e. the closing boundary string has an additional two hypens
		// We can use this to read to the precise end of the file data and discard the rest.
        int fileSize = contentLength - totalBytesRead - 6 - boundary.length();

        out.println("<b>File Size:</b>" + fileSize + " bytes<br>");

		DataSource dataSourceObj = null;

        // Lookup the datasource
        try
        {
        	InitialContext ctx = new javax.naming.InitialContext();
 	    	dataSourceObj = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/jdbc-blob");
		}
		catch (NamingException ne)
		{
			out.println("Failed to get the DataSource:<br><pre>");
			ne.printStackTrace(out);
			out.println("</pre></body></html>");
			return;
		}

		// Get the connection
		Connection dbConnection = null;
		try
		{
			dbConnection = dataSourceObj.getConnection();
			dbConnection.setAutoCommit(false);
		}
		catch (SQLException sqe)
		{
			out.println("Failed to get DB Connection and statement:<br><pre>");
			sqe.printStackTrace(out);
			out.println("</pre></body></html>");
		}


		// Check Database support.
		// This sample can not run against a Oracle Database prior to 8.1.6.
		// The driver must be 8.1.6 as that is the certified version with iAS
		// Oracle JDBC data states that both driver and database must be at least
		// 8.1.6 or higher or data corruption could result as the driver writes directly
		// to the underlying table data structure.
		DatabaseMetaData dbMetaData = null;
		String dbProdName = null;
		String dbProdVersion = null;
		String dbDriverVersion = null;

		try
		{
			dbMetaData = dbConnection.getMetaData();
			dbProdName = dbMetaData.getDatabaseProductName();
			dbProdVersion = dbMetaData.getDatabaseProductVersion();
			dbDriverVersion = dbMetaData.getDriverVersion();

			out.println("<b>Database Product Name:</b>"+dbProdName+"<br>");

			if ("Oracle".equalsIgnoreCase(dbProdName))
			{
				out.println("<b>DB Version:</b> " + dbProdVersion +" <br>");
			}
		}
		catch (SQLException sqe)
		{
			out.println("Failed to access DB Meta Data:<br><pre>");
			sqe.printStackTrace(out);
			out.println("</pre></body></html>");
		}

		// Determine if the blob already exists and prepare to insert it or update it on that basis
		try
		{
			PreparedStatement selectForUpdate;

			selectForUpdate = dbConnection.prepareStatement(
									(useBlob ? SqlStatements.BLOB_SELECT_STMT : SqlStatements.BIN_SELECT_STMT) +
									(dbMetaData.supportsSelectForUpdate() ? " FOR UPDATE" : ""),
									 ResultSet.TYPE_FORWARD_ONLY,
									 ResultSet.CONCUR_READ_ONLY);

			selectForUpdate.setString(1, filename);

			ResultSet rset = selectForUpdate.executeQuery();

			// The PartFilterStream ensures that only the specified number of bytes from the
			// stream.  To update a Blob the JDBC API requires an InputStream for it to read
			// the blob data from.  Since the file in the InputStream is trailed by the terminating
			// boundary stream this filter prevents the API from reading past the end of the file
			// and including the boundary in the blob contents
			PartFilterStream pfs = new PartFilterStream(sis, fileSize);

			out.println("<b>Preparing to upload blob to the database...</b><br>");
			if (rset.next())
			{
				out.println("Filename already exists, updating entry...<br>");
				PreparedStatement updateStmt = dbConnection.prepareStatement(
							 	   (useBlob ? SqlStatements.BLOB_UPDATE_STMT : SqlStatements.BIN_UPDATE_STMT));
				updateStmt.setString(1, fileContentType);
				updateStmt.setInt(2, fileSize);
				updateStmt.setBinaryStream(3, pfs, fileSize);
				updateStmt.setString(4, filename);
				int rows = updateStmt.executeUpdate();
				updateStmt.close();
				out.println("Updated entry<br>");
			}
			else
			{
				out.println("No entry exists for this filename, inserting entry...<br>");
				PreparedStatement insertStmt = dbConnection.prepareStatement(
								   (useBlob ? SqlStatements.BLOB_INSERT_STMT : SqlStatements.BIN_INSERT_STMT));
				insertStmt.setString(1, filename);
				insertStmt.setInt(2, fileSize);
				insertStmt.setString(3, fileContentType);
				insertStmt.setBinaryStream(4, pfs, fileSize);
				int rows = insertStmt.executeUpdate();
				insertStmt.close();
				out.println("Inserted Blob<br>");
			}
			dbConnection.commit();
			selectForUpdate.close();
			dbConnection.close();

        	//pfs.close();

			out.println("<b>Blob uploaded to the database</b><br>");
			out.println("<a href=\"download/list\">List Repository Contents</a>");
			out.println("<a href=\"UploadForm.html\">Upload Form</a>");
		}
		catch (SQLException sqe)
		{
			out.println("Failed to load the file to the database:<br><pre>");
			sqe.printStackTrace(out);
			out.println("</pre></body></html>");
		}

        out.println("</body></html>");
        out.close();
        sis.close();
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo()
    {
        return "Sample Upload Servlet";
    }

    /** PRIVATE FUNCTIONS
     */

    /** Resets Per-Post values
     */
    private void reset()
    {
        boundary = null;
        contentLength = 0;
        totalBytesRead = 0;
        fileContentType = "text/html";
        filename = null;
    }

    /** Private function which uses getByteLine() to retrieve a line as an array of bytes
     *  and then converts this to return a String
     */

	private String readStringLine(ServletInputStream in) throws IOException, UnsupportedEncodingException
	{
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream(chunkSize);
		byte[] buffer = new byte[chunkSize];
		int bytesRead, bytesToRead;

    	do
    	{
			bytesToRead = Math.min(chunkSize, contentLength - totalBytesRead);
      		bytesRead = in.readLine(buffer, 0, bytesToRead);

      		if (bytesToRead == 0)
      			break;

      		if (bytesRead != -1)
      		{
				byteBuffer.write(buffer, 0, bytesRead);
				totalBytesRead += bytesRead;
      		}
    	} while (bytesRead == bytesToRead);

    	if (byteBuffer.size() == 0)
    	{
      		return null;
    	}

    	return byteBuffer.toString("ISO8859-1").trim();
    }


    /** Private function to read in the information from the posted data
     *  Note that this routine is currently tailored to read from the form
     *  supplied with the same, i.e the addition of other fields will break the
     *  demo.
     */
    private void readHeaderData(HttpServletRequest request)
        throws java.io.IOException
    {
        // Get the Content Length of the the Posted Data
        contentLength = request.getContentLength();

        // Get the Content-type header
        String contentType = request.getHeader("Content-Type");

        // Extract the boundary string from the Content-type string
        int index = contentType.lastIndexOf("boundary=");
        if (index == -1)
        {
            throw new IOException("Malformed Content-Type Header, missing boundary information");
        }
        // Skip the leading boundary= part
        boundary = contentType.substring(index+9);

        // Remove quotes if the boundary string is enclosed in them
        if (boundary.charAt(0) == '"')
        {
            boundary = boundary.substring(1, boundary.lastIndexOf('"'));
        }
        // Actual boundary is preceeded by two extra hypens
        boundary = "--"+boundary;
	}

	private void readPartHeader(ServletInputStream in)
		throws IOException, UnsupportedEncodingException
	{
        //Read the first line, which should be the boundary.
        String line = readStringLine(in);

        if (!line.startsWith(boundary))
        {
            throw new IOException("Boundary mismatch, read: "+line+", expecting: "+boundary);
        }

		// Keep reading header lines until we hit a blank line
        while ( (line = readStringLine(in)) != null && line.length() > 0)
        {
			// Get the filename information from the content-disposition header
			if (line.toLowerCase().startsWith("content-disposition:"))
			{
				int index = line.lastIndexOf("filename=");
                if (index == -1)
                {
            		throw new IOException("Malformed Content-Disposition line, no filename component");
        		}
        		// Skip the leading filename= part
        		filename = line.substring(index+9);

        		// Remove quotes if the filename string is enclosed in them
        		if (filename.charAt(0) == '"')
        		{
            		filename = filename.substring(1, filename.lastIndexOf('"'));

            		// If the filename is infact a path grab just the filename
            		int index1 = filename.lastIndexOf("/");
            		int index2 = filename.lastIndexOf("\\");
            		if (index1 != -1 || index2 != -1)
            		{
             		   filename = filename.substring(Math.max(index1, index2)+1);
            		}
        		}
			}
			// Get the file's mimetype
			else if (line.toLowerCase().startsWith("content-type:"))
			{
            	// Find the space, i.e. Content-type: the-mime-type
            	//-----------------------------------^
            	int index = line.indexOf(" ");
            	if (index == -1)
            	{
             	   throw new IOException("Malformed Content-Type header");
            	}
            	fileContentType = line.substring(index+1);
			}
		}
    }

}
//END
