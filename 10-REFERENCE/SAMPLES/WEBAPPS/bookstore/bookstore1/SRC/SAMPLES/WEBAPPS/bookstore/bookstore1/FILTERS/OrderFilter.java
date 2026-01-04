/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package samples.webapps.bookstore.bookstore1.filters;

import java.io.*;
import java.sql.Timestamp;
import java.util.Iterator;
import javax.servlet.*;
import javax.servlet.http.*;

import samples.webapps.bookstore.bookstore1.database.BookDetails;
import samples.webapps.bookstore.bookstore1.cart.*;
import samples.webapps.bookstore.bookstore1.util.*;

public final class OrderFilter implements Filter {

      private FilterConfig filterConfig = null;

      public void init(FilterConfig filterConfig) throws ServletException {
          this.filterConfig = filterConfig;
      }
    
      public void destroy() {
          this.filterConfig = null;
      }
      public void doFilter(ServletRequest request, ServletResponse response,
                               FilterChain chain) throws IOException, ServletException {

      if (filterConfig == null)
          return;

      // Render the generic servlet request properties
      StringWriter sw = new StringWriter();
      PrintWriter writer = new PrintWriter(sw);
      ServletContext context = filterConfig.getServletContext();
      Counter counter = (Counter)context.getAttribute("orderCounter");
      HttpServletRequest hsr = (HttpServletRequest)request;
      HttpSession session = hsr.getSession();
      ShoppingCart cart =
          (ShoppingCart)session.getAttribute("cart");
      Currency c = (Currency)session.getAttribute("currency");
      c.setAmount(cart.getTotal());
      writer.println();
      writer.println("=======================================================");
      writer.println("The total number of orders is: " + counter.incCounter());
      writer.println("This order Received at " +
          (new Timestamp(System.currentTimeMillis())));
      writer.println();
      writer.print("Purchased by: " + request.getParameter("cardname"));
      writer.println();
      writer.print("Total: "  + c.getFormat());
      writer.println();
            
            int num = cart.getNumberOfItems();
      if (num > 0) {
            Iterator i = cart.getItems().iterator();
            while (i.hasNext()) {
                  ShoppingCartItem item = (ShoppingCartItem) i.next();
                  BookDetails bookDetails = (BookDetails) item.getItem();
                  writer.print("ISBN: "  + bookDetails.getBookId());
                  writer.print("   Title: "  + bookDetails.getTitle());
                  writer.print("   Quantity: " + item.getQuantity());
                  writer.println();       
            }
      }


      writer.println("=======================================================");

      // Log the resulting string
      writer.flush();
      context.log(sw.getBuffer().toString());
      chain.doFilter(request, response);
    }

    public String toString() {
      if (filterConfig == null)
            return ("OrderFilter()");
      StringBuffer sb = new StringBuffer("OrderFilter(");
      sb.append(filterConfig);
      sb.append(")");
      return (sb.toString());

    }


}

