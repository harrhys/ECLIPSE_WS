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
import javax.servlet.*;
import javax.servlet.http.*;

import samples.webapps.bookstore.bookstore1.database.*;
import samples.webapps.bookstore.bookstore1.exception.*;
/**
 * An HTTP Servlet that overrides the service method to return a
 * simple web page.
 */
public class BookStoreServlet extends HttpServlet {

   private BookDB bookDB;

   public void init() throws ServletException {
      bookDB =
         (BookDB)getServletContext().getAttribute("bookDB");

      if (bookDB == null)
         throw new UnavailableException("Couldn't get database.");
   }

   public void destroy() {
      bookDB.remove();
      bookDB = null;
   }

      
   public void doGet (HttpServletRequest request,
                         HttpServletResponse response)
        throws ServletException, IOException {        
            
      HttpSession session = request.getSession();
      ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
      if (messages == null) {
         Locale locale=request.getLocale();
         messages = ResourceBundle.getBundle("samples.webapps.bookstore.bookstore1.messages.BookstoreMessages", locale); 
         session.setAttribute("messages", messages);
      }
      
      
      // set content-type header before accessing the Writer
        response.setContentType("text/html");
            response.setBufferSize(8192);
            PrintWriter out = response.getWriter();
        
        // then write the data of the response
        out.println("<html>" +
                    "<head><title>Duke's Bookstore</title></head>");

        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher("/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);
                                     
        try {      
           BookDetails bd = bookDB.getBookDetails("203");      

            
           //Left cell -- the "book of choice"
           out.println("<b>" + messages.getString("What") + "</b>" +
                       "<p>" + "<blockquote>" + 
                              "<em><a href=\"" +
                       response.encodeURL(request.getContextPath() + "/bookdetails?bookId=203") +
                       "\">" + bd.getTitle() + "</a></em>" + messages.getString("Talk") + "</blockquote>");
           
           //Right cell -- various navigation options
           out.println("<p><a href=\"" +
                       response.encodeURL(request.getContextPath() + "/catalog") +
                       "\"><b>" + messages.getString("Start") + "</b></a></font><br>" +
                       "<br> &nbsp;" +
                       "<br> &nbsp;" +
                       "<br> &nbsp;" +
                       "</body>" +
                       "</html>");
         } catch (BookNotFoundException ex) {
                  response.resetBuffer();
            throw new ServletException(ex);
         }
         out.close();
    }

    public String getServletInfo() {
        return "The BookStore servlet returns the main web page " +
               "for Duke's Bookstore.";
    }
}
