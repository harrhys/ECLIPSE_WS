package samples.corba.ejbsample.servlet;

import java.io.*; 
import java.util.*; 
import javax.servlet.*; 
import javax.naming.*; 
import javax.rmi.PortableRemoteObject; 
import javax.servlet.http.*; 
import javax.ejb.*; 

import samples.corba.ejbsample.ejb.*;

/** A simple servlet used to access the CorbaClient EJB through WEB/HTTP path.
 */
public class CorbaServlet extends HttpServlet {  

    /** Called by the server to allow the servlet to handle a HTTP GET request contaning parameter.
     */    
   public void doGet (HttpServletRequest request,HttpServletResponse response) 
        throws ServletException, IOException { 

      CorbaClient myCorbaClientBean; 
      CorbaClientHome myCorbaClientHome; 
      CorbaClient myCorbaClientRemote;


      response.setContentType("text/html");

      String title = "Servlet calling EJB to access backend CORBA server";

      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<body bgcolor=\"white\">");
      out.println("<head>");

      out.println("<title>" + title + "</title>");
      out.println("</head>");
      out.println("<body>");

      out.println("<h3>" + title + "</h3>");
 
      InitialContext initContext = null; 

      System.out.println("\nCorbaClientEjb Servlet is executing ...");  

      System.out.println("Retrieving JNDI initial context"); 
      out.println("<BR>Retrieving JNDI initial context");
      try { 
        initContext = new javax.naming.InitialContext(); 
      }  
      catch (Exception e) { 
        System.out.println("Exception creating InitialContext: " + e.toString()); 
        return; 
      } 

      try { 
        System.out.println("Looking up CorbaClient bean home interface"); 
        out.println("<BR>Looking up CorbaClient bean home interface"); 
        String JNDIName = "java:comp/env/ejb/TheCorbaClient"; 
        System.out.println("Looking up: " + JNDIName); 
        out.println("<BR>Looking up: " + JNDIName); 
        Object objref = initContext.lookup(JNDIName);
        out.println("<BR>after Looking up: " + JNDIName); 
        myCorbaClientHome = (CorbaClientHome)PortableRemoteObject.narrow(objref,CorbaClientHome.class);
      }  
      catch(Exception e) { 
        e.printStackTrace();
        System.out.println("CorbaClient bean home not found - " +  
         "Is bean registered with JNDI?: " + e.toString()); 
        return; 
      } 
      try { 
        System.out.println("Creating the CorbaClient bean"); 
        out.println("<BR>Creating the CorbaClient bean"); 
        myCorbaClientRemote = myCorbaClientHome.create();  
      } 
      catch(CreateException e) { 
        System.out.println("Could not create the CorbaClient bean: "+ e.toString()); 
        return; 
      }  

      System.out.println("Calling CorbaClient bean to do some work.");
      out.println("<BR>Calling CorbaClient bean to do some work.");
      String result = "success";

      try { 
        result = myCorbaClientRemote.doWork();  
      }
      catch(java.rmi.RemoteException e) { 
        System.out.println("Caught RemoteException while calling EJB business method: "+ e.toString()); 
        out.println("<BR>Caught RemoteException while calling EJB business method: "+ e.toString()); 
        result = "failure";
      }  
      catch(Exception e) { 
        System.out.println("Caught unexpected exception while calling EJB business method: "+ e.toString()); 
        out.println("<BR>Caught unexpected exception while calling EJB business method: "+ e.toString()); 
        result = "failure";
      }  

      System.out.println("Result of calling CorbaClient bean: " + result); 
      out.println("<BR>Result of calling CorbaClient bean: " + result); 

      out.println("<BR>Done");

      out.println("</body>");
      out.println("</html>");
      out.close();

      return;  
    }  

   /** Called by the server to allow the servlet to handle a HTTP GET request contaning parameter.
    */   
    public void doPost (HttpServletRequest request,HttpServletResponse response) 
        throws ServletException, IOException { 
      doGet(request,response); 
    } 

} 
