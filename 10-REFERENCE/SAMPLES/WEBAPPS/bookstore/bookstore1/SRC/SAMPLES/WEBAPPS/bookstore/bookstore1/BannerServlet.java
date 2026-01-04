/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
package samples.webapps.bookstore.bookstore1;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import samples.webapps.bookstore.bookstore1.database.*;
import samples.webapps.bookstore.bookstore1.cart.*;

/**
 * This is a simple example of an HTTP Servlet.  It responds to the GET
 * method of the HTTP protocol.
 */
public class BannerServlet extends HttpServlet { 

    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<body  bgcolor=\"#ffffff\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"#CC0066\">Duke's </font> <img src=\"" + request.getContextPath() + "/duke.books.gif\">" + 
                    "<font size=\"+3\" color=\"black\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> ");
      }
    public void doPost (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<body  bgcolor=\"#ffffff\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"#CC0066\">Duke's </font>  <img src=\"" + request.getContextPath() + "/duke.books.gif\">" + 
                              "<font size=\"+3\" color=\"black\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> ");
      }
}
