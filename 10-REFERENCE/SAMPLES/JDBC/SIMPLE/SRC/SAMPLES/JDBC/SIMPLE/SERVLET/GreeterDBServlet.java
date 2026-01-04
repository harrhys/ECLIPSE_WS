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
 * This servlet is responsible for generating the pages which asks for a String input
 * It will invoke GreeterDB EJB to generate a Greeting and save it in a database.
 */

public class GreeterDBServlet extends HttpServlet {  

  /** 
    * The doGet method of the servlet. Handles all http GET request.  
	* Required by the servlet specification.  
	* @exception throws ServletException and IOException.  
	*/

  public void doGet (HttpServletRequest request,HttpServletResponse response) 
   throws ServletException, IOException { 
    javax.ejb.Handle beanHandle; 
    GreeterDBHome myGreeterDBHome; 
    GreeterDB myGreeterDBRemote;
    InitialContext initContext = null; 
    Hashtable env = new java.util.Hashtable(1); 

    System.out.println("\nGreeterDBServlet is executing...");  

    System.out.println("Retrieving JNDI initial context..."); 
    try { 
      initContext = new javax.naming.InitialContext(); 
      System.out.println("- Retrieved initial context successfully");
    }  
    catch (Exception e) { 
      System.out.println("- Exception creating InitialContext: " + e.toString()); 
      return; 
    } 

    try { 
      System.out.println("Looking up dbGreeter bean home interface..."); 
      String JNDIName = "java:comp/env/ejb/jdbc-simple"; 
      System.out.println("- Looking up: " + JNDIName); 
      myGreeterDBHome = (GreeterDBHome)initContext.lookup(JNDIName);
      System.out.println("- Looked up the EJB successfully"); 
    }  
    catch(Exception e) { 
      System.out.println("- Greeter bean home not found - " +  
       "Is bean registered with JNDI?: " + e.toString()); 
      return; 
    } 
    try { 
      System.out.println("Creating the dbGreeter bean..."); 
      myGreeterDBRemote = myGreeterDBHome.create(); 
      System.out.println("- Created EJB successfully");  
    } 
    catch(CreateException e) { 
      System.out.println("- Could not create the dbGreeter bean: " + e.toString()); 
      return; 
    }  

    System.out.println("Getting the message from the dbGreeter bean..."); 
    String theMessage = myGreeterDBRemote.getGreeting();  
    System.out.println("- Got this message from greeter bean: " + theMessage);

    System.out.println("Getting the name input to this servlet...");
    String name = request.getParameter("name"); 
    System.out.println("- Got name: " + name); 

    System.out.println("Recording the greeting in the database...");
    StringBuffer timeStamp = new StringBuffer();
    Calendar rightNow = Calendar.getInstance();
    timeStamp.append(rightNow.get(Calendar.MONTH)+1); timeStamp.append("/");
    timeStamp.append(rightNow.get(Calendar.DAY_OF_MONTH)); timeStamp.append("/");
    timeStamp.append(rightNow.get(Calendar.YEAR)); timeStamp.append(" ");
    timeStamp.append(rightNow.get(Calendar.HOUR_OF_DAY)); timeStamp.append(":");
    timeStamp.append(rightNow.get(Calendar.MINUTE)); timeStamp.append(":");
    timeStamp.append(rightNow.get(Calendar.SECOND)); timeStamp.append(":");
    timeStamp.append(rightNow.get(Calendar.MILLISECOND));

    StringBuffer query = new StringBuffer("insert into Greeting (timeStamp,name,message) values ");
    query.append("('");        
    query.append(timeStamp.toString()); query.append("','");
    query.append(name); query.append("','");
    query.append("Good " + theMessage); query.append("')");  
    try {
      System.out.println("Getting datasource...");
      String dsName = "java:comp/env/jdbc/jdbc-simple";
      DataSource ds = (javax.sql.DataSource)initContext.lookup(dsName);
      System.out.println("- Got datasource successfully");
      
      System.out.println("Getting connection...");
      Connection conn = ds.getConnection();
      System.out.println("- Got connection successfully");
      
      System.out.println("Getting statement...");
      Statement stmt = conn.createStatement();
      System.out.println("- Got statement successfully");
      
      System.out.println("Executing query...");
      int nRows = stmt.executeUpdate(query.toString());
      System.out.println("- Executed query with result: " + nRows);
      
      System.out.println("Closing statement...");
      stmt.close();
      System.out.println("- Closed statement successfully");
      
      System.out.println("Closing connection...");
      conn.close();
      System.out.println("- Closed connection successfully");
    }
    catch (Exception ex) {
      System.out.println("- Could not interact with the database");
      System.out.println("Exception: " + ex.toString()); 
    }
    
    System.out.println("Storing the message in request object for the JSP..."); 
    request.setAttribute("message", theMessage); 
    System.out.println("- Stored message successfully");

    System.out.println("Dispatching JSP for output..."); 
    response.setContentType("text/html");  
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher 
     ("/GreeterDBView.jsp"); 
    dispatcher.include(request, response);
    System.out.println("- Dispatched JSP successfully"); 
    System.out.println("\nGreeterDBServlet is all done\n");
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
    return "Call a session bean from a servlet and deliver result via a JSP.  Log greeting to database."; 
  } 
} 
