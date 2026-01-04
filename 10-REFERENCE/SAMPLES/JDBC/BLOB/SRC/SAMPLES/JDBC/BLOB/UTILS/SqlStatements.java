/**
  Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
*/

package samples.jdbc.blob.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 *
 */
public class SqlStatements extends HttpServlet
{

	public final static String BLOB_SELECT_STMT =
		"SELECT FILENAME, FILESIZE, MIMETYPE, BLOBDATA FROM BLOB_REPOSITORY WHERE FILENAME=?";

	public final static String BLOB_SELECT_ALL_STMT =
		"SELECT FILENAME, FILESIZE, MIMETYPE FROM BLOB_REPOSITORY";

	public final static String BLOB_UPDATE_STMT =
		"UPDATE BLOB_REPOSITORY SET MIMETYPE=?, FILESIZE=?, BLOBDATA=? WHERE FILENAME=?";

	public final static String BLOB_INSERT_STMT =
		"INSERT INTO BLOB_REPOSITORY (FILENAME, FILESIZE, MIMETYPE, BLOBDATA) VALUES (?, ?, ?, ?)";

	public final static String BLOB_DELETE_STMT =
		"DELETE FROM BLOB_REPOSITORY WHERE FILENAME = ?";

	public final static String BIN_SELECT_STMT =
		"SELECT FILENAME, FILESIZE, MIMETYPE, BINDATA FROM BIN_REPOSITORY WHERE FILENAME=?";

	public final static String BIN_SELECT_ALL_STMT =
		"SELECT FILENAME, FILESIZE, MIMETYPE FROM BIN_REPOSITORY";

	public final static String BIN_UPDATE_STMT =
		"UPDATE BIN_REPOSITORY SET MIMETYPE=?, FILESIZE=?, BINDATA=? WHERE FILENAME=?";

	public final static String BIN_INSERT_STMT =
		"INSERT INTO BIN_REPOSITORY (FILENAME, FILESIZE, MIMETYPE, BINDATA) VALUES (?, ?, ?, ?)";

	public final static String BIN_DELETE_STMT =
		"DELETE FROM BIN_REPOSITORY WHERE FILENAME = ?";
}
