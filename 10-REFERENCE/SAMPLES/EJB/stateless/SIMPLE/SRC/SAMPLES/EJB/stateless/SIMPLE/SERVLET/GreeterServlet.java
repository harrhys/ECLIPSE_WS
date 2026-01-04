/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */

package samples.ejb.stateless.simple.servlet;

import java.io.*; 
import java.util.*; 
import javax.servlet.*; 
import javax.naming.*; 
import javax.servlet.http.*; 
import javax.rmi.PortableRemoteObject;
import javax.ejb.*; 

import samples.ejb.stateless.simple.ejb.*; 

/**
 * This servlet is responsible for throwing the html pages for the HelloWorld application.
 */
public class GreeterServlet extends HttpServlet {  

   /**
    * The doGet method of the servlet. Handles all http GET request.
    * Required by the servlet specification.
    * @exception throws ServletException and IOException.
    */
   public void doGet (HttpServletRequest request,HttpServletResponse response) 
        throws ServletException, IOException { 

        javax.ejb.Handle beanHandle; 
        Greeter myGreeterBean; 
        GreeterHome myGreeterHome; 
        Greeter myGreeterRemote;
 
        InitialContext initContext = null; 
        Hashtable env = new java.util.Hashtable(1); 

        System.out.println("\nGreeterServlet is executing ...");  

        System.out.println("Retrieving JNDI initial context"); 
        try { 
            initContext = new javax.naming.InitialContext(); 
        }  
        catch (Exception e) { 
          System.out.println("Exception creating InitialContext: " + e.toString()); 
          return; 
        } 

        try { 
            System.out.println("Looking up greeter bean home interface"); 
            String JNDIName = "java:comp/env/ejb/greeter"; 
            System.out.println("Looking up: " + JNDIName); 
            Object objref = initContext.lookup(JNDIName); 
            myGreeterHome = (GreeterHome)PortableRemoteObject.narrow(objref,
                                            GreeterHome.class);
        }  
        catch(Exception e) { 
          System.out.println("Greeter bean home not found - " +  
           "Is bean registered with JNDI?: " + e.toString()); 
        return; 
        } 
        try { 
            System.out.println("Creating the greeter bean"); 
            myGreeterRemote = myGreeterHome.create();  
        } 
        catch(CreateException e) { 
            System.out.println("Could not create the greeter bean: "+  
            e.toString()); 
            return; 
        }  

        System.out.println("Getting the message from the greeter bean"); 
        String theMessage = myGreeterRemote.getGreeting();  
        System.out.println("Got this message from greeter bean: " + theMessage); 

        System.out.println("Storing the message in request object"); 
        request.setAttribute("message", theMessage); 

        System.out.println("Dispatching JSP for output"); 
        response.setContentType("text/html"); 
        RequestDispatcher dispatcher; 
        dispatcher = getServletContext().getRequestDispatcher 
         ("/GreeterView.jsp"); 
        dispatcher.include(request, response); 
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
        return "Call a session bean from a servlet and deliver result via a JSP."; 
    } 
} 
