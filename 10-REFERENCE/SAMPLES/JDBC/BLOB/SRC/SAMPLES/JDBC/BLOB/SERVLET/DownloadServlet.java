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
import java.net.URLEncoder;

import samples.jdbc.blob.utils.SqlStatements;

/**
 * Provides the client with a list of files in the <code>BLOB_REPOSITORY</code> table to allow them to
 * choose to either list, download or delete the entries.
 */
public class DownloadServlet extends HttpServlet
{

	private final int DEFAULT_CHUNKSIZE = 1024;
	private final boolean DEFAULT_USE_BLOB  = false;

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
	 * Displays the contents of the <code>BLOB_REPOSITORY</code> table in the database.  The contents are
	 * listed in a table with details on the filename, file size and mimetype of the contents.  Each
	 * entry has two links allowing the file to either be fetched or deleted from the repository.
	 *
	 * @param request an HttpServletRequest object that contains the client's request to the servlet
	 * @param response an HttpServletReponse object that contains the servlet's response to the client
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 * @throws ServletException if the request for the POST could not be handled
	 */
    protected void doBlobList(HttpServletRequest request, HttpServletResponse response)
    	throws IOException, ServletException
    {
		PrintWriter out = response.getWriter();

		response.setContentType("text/html");
		out.println("<html><head><title>Download Servlet</title></head>");
		out.println("<body><h3><center><font face=\"Arial, Helvetica\">");
        out.println("Download Servlet</font></center></h3><hr>");
        out.println("<b><center>Repository Contents</center></b><br>");

		Connection dbConnection = getDbConnection(out);
		if (dbConnection == null)
		{
			out.println("Failed to get Database connection</body></html>");
			out.close();
			return;
		}

		try
		{
			Statement selectStmt = dbConnection.createStatement();
			ResultSet rset = selectStmt.executeQuery(
				(useBlob ? SqlStatements.BLOB_SELECT_ALL_STMT : SqlStatements.BIN_SELECT_ALL_STMT));

			boolean rowFound = false;
			while (rset.next())
			{
				if (!rowFound)
				{
					rowFound = true;
			        out.println("<center><table width=\"50%\" border=\"0\"><tr bgcolor=\"#808080\">");
			        out.println("<th>Filename</th><th>Size</th><th>Mimetype</th><th><pre> </pre></th></tr>");
				}
				String filename = rset.getString(1);
				out.println("<tr align=\"center\" bgcolor=\"#C0C0C0\">");
				String href1 = "../download/fetch?name=" + URLEncoder.encode(filename);
				out.println("<td><a href=\"" + href1 +
				             "\">" + filename + "</a></td>");
				out.println("<td>" + rset.getInt(2) + "</td>");
				out.println("<td>" + rset.getString(3) + "</td>");
				String href2 = "../download/delete?name=" + URLEncoder.encode(filename);
				out.println("<td><a href=\"" + href2 +
						          "\">Delete</a></td></tr>");
			}
			if (rowFound)
			{
				out.println("</table></center>");
			}
			else
			{
				out.println("<center>No entries found.</center><br>");
			}
			out.println("<center><a href=\"../UploadForm.html\">Upload Form</a><center>");
			rset.close();
			selectStmt.close();
			closeConnection(dbConnection);
			out.println("</body></html>");
			out.close();
		}
		catch (SQLException sqe)
		{
			out.println("Failed to select blob details from the database<br><pre>");
			sqe.printStackTrace(out);
			out.println("</pre></body></html>");
			closeConnection(dbConnection);
			return;
		}


	}

	/**
	 * Streams a blob from the <code>BLOB_REPOSITORY</code> table in the database.  The response includes
	 * the correct content-length for the blob.
	 *
	 * @param request an HttpServletRequest object that contains the client's request to the servlet
	 * @param response an HttpServletReponse object that contains the servlet's response to the client
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 * @throws ServletException if the request for the POST could not be handled
	 */
	protected void doBlobFetch(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		Connection dbConnection = getDbConnection();

		if (dbConnection == null)
		{
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
			                   "Failed to get a database connection");
			return;
		}

		try
		{

			PreparedStatement selectStmt = dbConnection.prepareStatement(
				(useBlob ? SqlStatements.BLOB_SELECT_STMT : SqlStatements.BIN_SELECT_STMT));

			String filename = request.getParameter("name");
			selectStmt.setString(1, filename);

			ResultSet rset = selectStmt.executeQuery();
			rset.next();

			ServletOutputStream sos = response.getOutputStream();
			response.setContentLength(rset.getInt(2));
			response.setContentType(rset.getString(3));

			BufferedInputStream bis = null;
			if (useBlob)
			{
				Blob theBlob = rset.getBlob(4);
				bis = new BufferedInputStream(theBlob.getBinaryStream());
			}
			else
			{
				bis  = new BufferedInputStream(rset.getBinaryStream(4));
			}
			BufferedOutputStream bos = new BufferedOutputStream(sos);


			byte[] buffer = new byte[chunkSize];
			int bytesRead;

			while ((bytesRead = bis.read(buffer, 0, chunkSize)) != -1)
			{
				bos.write(buffer, 0, bytesRead);
			}

			bos.flush();
			bos.close();
			bis.close();
			rset.close();
			selectStmt.close();
			closeConnection(dbConnection);
		}
		catch (SQLException sqe)
		{
			response.reset();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
							   "Encountered a problem with the database");

			sqe.printStackTrace(System.out);
			closeConnection(dbConnection);
			return;
		}

	}

	/**
	 * Delete a blob from the <code>BLOB_REPOSITORY</code> table in the database.  The method then
	 * invokes {@link #doBlobList(HttpServletRequest,HttpServletResponse) doBlobList} to display the
	 * updated table contents.
	 *
	 * @param request an HttpServletRequest object that contains the client's request to the servlet
	 * @param response an HttpServletReponse object that contains the servlet's response to the client
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 * @throws ServletException if the request for the POST could not be handled
	 */
	protected void doBlobDelete(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		Connection dbConnection = getDbConnection();
		if (dbConnection == null)
		{
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
			                   "Failed to get a database connection");
			return;
		}

		String filename = request.getParameter("name");

		try
		{
			PreparedStatement deleteStmt = dbConnection.prepareStatement(
				(useBlob ? SqlStatements.BLOB_DELETE_STMT : SqlStatements.BIN_DELETE_STMT));
			deleteStmt.setString(1, filename);

			int rows = deleteStmt.executeUpdate();

			dbConnection.commit();

			doBlobList(request, response);
		}
		catch (SQLException sqe)
		{
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
			                   "Encountered a DB error deleting the file");
			sqe.printStackTrace(System.out);
			closeConnection(dbConnection);
			return;
		}

	}

	/**
	 * Dispatches the request to either
	 * {@link #doBlobList(HttpServletRequest, HttpServletResponse) doBlobList},
	 * {@link #doBlobFetch(HttpServletRequest, HttpServletResponse) doBlobFetch} or
	 * {@link #doBlobDelete(HttpServletRequest, HttpServletResponse) doBlobDelete}
	 * The choice is determine by the <code>PATH_INFO</code>
	 * contents, <code>/list</code>, <code>/fetch</code> or <code>/delete</code>.  If no match is made
	 * then an <code>IOException</code> is thrown.
	 *
	 * @param request an HttpServletRequest object that contains the client's request to the servlet
	 * @param response an HttpServletReponse object that contains the servlet's response to the client
	 * @throws IOException if an input or output error is detected when the servlet handles the request
	 * @throws ServletException if the request for the <code>GET</code> could not be handled
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {

		// Get our path info, this contains the operation we are being asked to perform
		String pathInfo = request.getPathInfo();

		if (pathInfo == null  || "/list".equals(pathInfo))
		{
			doBlobList(request, response);
		}
		else if ("/fetch".equals(pathInfo))
		{
			doBlobFetch(request, response);
		}
		else if ("/delete".equals(pathInfo))
		{
			doBlobDelete(request, response);
		}
		else
		{
			throw new IOException("Invalid PATH_INFO, cannot dispatch request");
		}
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo()
    {
        return "iPlanet Sample Database Blob Download Servlet";
    }

    /** PRIVATE FUNCTIONS
     */

    private Connection getDbConnection()
    {
		return getDbConnection(new PrintWriter(System.err, true));
	}

    private Connection getDbConnection(PrintWriter out)
    {
		DataSource dataSourceObj = null;

        // Lookup the datasource
        try
        {
        	InitialContext ctx = new javax.naming.InitialContext();
 	    	dataSourceObj = (javax.sql.DataSource) ctx.lookup("java:comp/env/jdbc/jdbc-blob");
		}
		catch (NamingException ne)
		{
			ne.printStackTrace(out);
			return null;
		}

		// Get the connection
		Connection dbConnection = null;

		try
		{
			dbConnection = dataSourceObj.getConnection();
			dbConnection.setAutoCommit(false);
			return dbConnection;
		}
		catch (SQLException sqe)
		{
			sqe.printStackTrace(out);
			return null;
		}
	}

    private void closeConnection(Connection conn)
    {
		try
		{
			conn.close();
		}
		catch (SQLException sqe)
		{
		}
	}

}
//END
