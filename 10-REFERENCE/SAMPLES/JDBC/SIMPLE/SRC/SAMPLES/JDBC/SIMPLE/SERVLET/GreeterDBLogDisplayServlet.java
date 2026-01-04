/**
  Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
  */

package samples.jdbc.simple.servlet; 

import java.io.*; 
import java.util.*; 
import javax.servlet.*; 
import javax.naming.*; 
import javax.servlet.http.*; 
import javax.ejb.*; 
import java.sql.*;
import javax.sql.*;

import samples.jdbc.simple.ejb.*; 

/**
  * This servlet is responsible for displaying the greetings stored in 
  * the database .
  */
  
public class GreeterDBLogDisplayServlet extends HttpServlet {  

  /** 
    * The doGet method of the servlet. Handles all http GET request.  
	* Required by the servlet specification.  
	* @exception throws ServletException and IOException.  
	*/
  public void doGet (HttpServletRequest request,HttpServletResponse response) 
   throws ServletException, IOException { 
    java.sql.ResultSet rs = null;
    java.sql.Connection conn = null;
    java.sql.Statement stmt = null;  
    System.out.println("\nGreeterDBLogDisplayServlet is executing...");
    try {
      System.out.println("Getting initial context...");
      InitialContext ctx = new InitialContext();
      System.out.println("- Got initial context successfully");
          
      System.out.println("Getting datasource...");
      String dsName = "java:comp/env/jdbc/jdbc-simple";
      DataSource ds = (javax.sql.DataSource)ctx.lookup(dsName);
      System.out.println("- Got datasource successfully");

      System.out.println("Getting connection...");
      conn = ds.getConnection();
      System.out.println("- Got connection successfully");
      
      System.out.println("Getting statement...");
      stmt = conn.createStatement();
      System.out.println("- Got statement successfully");
         
      System.out.println("Executing query...");
      StringBuffer query = new StringBuffer("select * from Greeting");
      rs = stmt.executeQuery(query.toString());
      System.out.println("- Executed query successfully");
    }
    catch (Exception ex) {
      System.out.println("- Could not interact with the database");
      System.out.println("- Exception: " + ex.toString()); 
    }
    System.out.println("Storing the data base resuts in request object for JSP..."); 
    request.setAttribute("dbResults", rs); 
    System.out.println("Results stored successfully");
        
    System.out.println("Dispatching JSP for output..."); 
    response.setContentType("text/html"); 
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher 
     ("/GreeterDBLogView.jsp"); 
        dispatcher.include(request, response); 
    System.out.println("- Dispatched JSP successfully"); 
    try {
      System.out.println("Closing statement...");
      stmt.close();
      System.out.println("- Closed statement successfully");
        
      System.out.println("Closing connection...");
      conn.close();
      System.out.println("- Closed statement successfully");
    }
    catch (Exception ex) {
      System.out.println("- Could not close the statement and connection");
    }
    System.out.println("\nGreeterDBLogDisplayServlet is all done\n");
    return;  
  }  

  /** 
    * The doPost method of the servlet. Handles all http POST request.  
    * Required by the servlet specification.  
    * @exception throws ServletException and IOException.  
    */
  public void doPost (HttpServletRequest request,HttpServletResponse response) 
   throws ServletException, IOException { 
    doGet(request,response); 
  } 

  /** 
    * Returns the servlet info as a String.  
	* @return returns the servlet info as a String.  
	*/

  public String getServletInfo() { 
    return "Retrieve greetings from database and display via a JSP."; 
  } 
} 
