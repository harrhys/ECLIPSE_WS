/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package com.sun.j2ee.blueprints.petstore.tools.populate;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import javax.naming.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.j2ee.blueprints.catalog.util.JNDINames;


public class PopulateValidatorServlet extends HttpServlet {
  private static final String POPULATE_SERVLET_PARAM = "do_populate";
  private static final String DONT_POPULATE_PAGE_URL_PARAM = "dont_populate_page";
  private static final String TABLE_NAME_PARAM = "table_to_check";

  protected Connection getConnection() throws Exception {
    InitialContext context = new InitialContext();
    return ((DataSource) context.lookup(JNDINames.CATALOG_DATASOURCE)).getConnection();
  }

  private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException {
    System.err.println("Forwarding to: " + path);
    getServletConfig().getServletContext().getRequestDispatcher(path).forward(request, response);
    return;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException {
      doPost(request, response);
      return;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String populateServletPath = request.getParameter(POPULATE_SERVLET_PARAM);
    String dontPopulatePageURL = request.getParameter(DONT_POPULATE_PAGE_URL_PARAM);
    String tableName = request.getParameter(TABLE_NAME_PARAM);
    Connection connection = null;
    try {
      // Check for the tables
      connection = getConnection();
      Statement statement =  connection.createStatement();
      ResultSet resultSet = statement.executeQuery("select * from " + tableName);
      System.out.println("Executing query: select * from " + tableName);
      if (!resultSet.next()) {
        forward(request, response, populateServletPath);
        return;
      }
    } catch(SQLException exception) {
      // The tables have not been installed - forward to populator
      forward(request, response, populateServletPath);
      return;
    }  catch(Exception exception) {
      // The database is not up
      throw new ServletException("Can't check database population.", exception);
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (Exception exception) {}
    }
    forward(request, response, dontPopulatePageURL);
    return;
  }
}

