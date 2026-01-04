package samples.corba.servletsample;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.NamingException;
import javax.naming.InitialContext;

import org.omg.CORBA.*;

import demos.simple.Simple.*;
//import com.iona.corba.util.SystemExceptionDisplayHelper;

import samples.corba.common.OrbSingleton;


/** Servlet accessing backend CORBA object.
 *
 * A simple servlet class file calling methods of a remote CORBA object based on Orbix as the underlying ORB.
 *
 */
public class CorbaClientServlet extends HttpServlet {

    private ORB orb;
    private String iorFile1 = null;
    private String iorFile2 = null;
    private org.omg.CORBA.Object objref1 = null;
    private org.omg.CORBA.Object objref2 = null;
    private SimpleObject simple1 = null;
    private SimpleObject simple2 = null;

    /** Called by the Servlet Container to set the Servlet Context and instantiate the ORB.
     * @param config A servlet configuration object used by a servlet container used to pass information to a servlet during initialization.
     * @throws ServletException  If error occurs in looking up the environment variables or in instantiation of the ORB or in the CORBA object binding.
     */    
    public void init (ServletConfig config) throws ServletException
    {
      String orbDomainName = null;
      String orbCfgDir = null;

      try {
	  InitialContext ic = new InitialContext();
	  orbDomainName = (String)ic.lookup("java:comp/env/orbDomainName");
	  orbCfgDir = (String)ic.lookup("java:comp/env/orbCfgDir");
	  iorFile1 = (String)ic.lookup("java:comp/env/simpleObjectIor1");
	  iorFile2 = (String)ic.lookup("java:comp/env/simpleObjectIor2");
      } catch (NamingException ex) {
        throw new ServletException("Could not access ORB and/or IOR environment variables.");
      }

      super.init(config);

      System.out.println("CorbaClientServlet: Initializing ORB");
      System.out.println("CorbaClientServlet: orbDomainName=" + orbDomainName);
      System.out.println("CorbaClientServlet: orbCfgDir=" + orbCfgDir);
      System.out.println("CorbaClientServlet: iorFile1=" + iorFile1);
      System.out.println("CorbaClientServlet: iorFile1=" + iorFile1);

      try {
		OrbSingleton orbSingleton = OrbSingleton.getInstance (orbDomainName, orbCfgDir);
		orb = orbSingleton.getOrb();
      }
      catch (SystemException ex) {
       //n System.out.println("Caught exception: " + SystemExceptionDisplayHelper.toString(ex));
        throw new ServletException("ORB.init() failed.");
      }

      try {
        initObjectRefs();
      }
      catch(Exception e)
    	{
        orb.shutdown(true);
        throw new ServletException("Error initializing object references.");
  	}
    }
    /** Used to shutdown the created ORB instance. Called only if using JDK1.3.x.
     */    
    public void destroy ()
    {
      orb.shutdown(true);
    }

    /** Called by the server to allow the servlet to handle a HTTP request.
     * @param request an HttpServletRequest object that contains the request the client has made of the servlet.
     * @param response an HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws IOException if an input or output error is detected when the servlet handles the request.
     * @throws ServletException if the request could not be handled.
     */    
    public void service (HttpServletRequest  request,
                         HttpServletResponse response)
        throws IOException, ServletException
    {
      response.setContentType("text/html");

      String title = "Servlet accessing backend CORBA server";

      PrintWriter out = response.getWriter();
      out.println("<html>");
      out.println("<body bgcolor=\"white\">");
      out.println("<head>");

      out.println("<title>" + title + "</title>");
      out.println("</head>");
      out.println("<body>");

      out.println("<h3>" + title + "</h3>");

    	// Invoke method on first object
    	//
      System.out.println("CorbaClientServlet: Invoking method on first object.");
	out.println("<BR>Invoking method on first object.");

      try {
    	  simple1.call_me();
      }
      catch (org.omg.CORBA.COMM_FAILURE e) {
        System.out.println("Caught org.omg.CORBA.COMM_FAILURE exception." + e.toString());
        out.println("<BR>Caught org.omg.CORBA.COMM_FAILURE exception." + e.toString());
        attemptInitObjectRefs();
      }
      catch (org.omg.CORBA.TRANSIENT e) {
        System.out.println("Caught org.omg.CORBA.TRANSIENT exception." + e.toString());
    	out.println("<BR>Caught org.omg.CORBA.TRANSIENT exception." + e.toString());
        attemptInitObjectRefs();
      }
      catch (SystemException e) {
      //  System.out.println("Caught exception: " + SystemExceptionDisplayHelper.toString(e));
      //  out.println("<BR>Caught exception: " + SystemExceptionDisplayHelper.toString(e));
        out.println("</body>");
        out.println("</html>");
        out.close();
        return;
      }
      catch (Exception e) {
        System.out.println("Caught unexpected exception." + e.toString());
        out.println("<BR>Caught unexpected exception." + e.toString());
        out.println("</body>");
        out.println("</html>");
        out.close();
        return;
      }

    	// Invoke method on second object
    	//
      System.out.println("CorbaClientServlet: Invoking method on second object.\n");
      out.println("<BR>Invoking method on second object.");

      try {
    	  simple2.call_me();
      }
      catch (org.omg.CORBA.COMM_FAILURE e) {
        System.out.println("Caught org.omg.CORBA.COMM_FAILURE exception.");
    	out.println("<BR>Caught org.omg.CORBA.COMM_FAILURE exception.");
        attemptInitObjectRefs();
      }
      catch (org.omg.CORBA.TRANSIENT e) {
        System.out.println("Caught org.omg.CORBA.TRANSIENT exception.\n");
    	out.println("<BR>Caught org.omg.CORBA.TRANSIENT exception.");
        attemptInitObjectRefs();
      }
      catch (SystemException e) {
      //  System.out.println("Caught exception: " + SystemExceptionDisplayHelper.toString(e));
       // out.println("<BR>Caught exception: " + SystemExceptionDisplayHelper.toString(e));
        out.println("</body>");
        out.println("</html>");
        out.close();
        return;
      }
      catch (Exception e) {
        System.out.println("Caught unexpected exception." + e.toString());
        out.println("<BR>Caught unexpected exception." + e.toString());
        out.println("</body>");
        out.println("</html>");
        out.close();
        return;
      }

      out.println("<BR>Done");

      out.println("</body>");
      out.println("</html>");
      out.close();
    }

    private static org.omg.CORBA.Object import_object(ORB orb, String filename)
      throws FileNotFoundException, IOException
    {
    	String ior = null;
    	RandomAccessFile FileStream = null;

    	System.out.println("CorbaClientServlet: Reading object reference from " + filename);

      FileStream = new RandomAccessFile(filename,"r");
      ior = FileStream.readLine();
      return orb.string_to_object(ior);
    }

    private void initObjectRefs()
      throws Exception
    {
      try {
     	System.out.println("CorbaClientServlet: Invoking import_object() for first object");
        objref1 = import_object(orb, iorFile1);
     	System.out.println("Invoking import_object() for second object");
        objref2 = import_object(orb, iorFile2);
      }
      catch(Exception e)
      {
        throw new Exception("Error accessing IOR file.");
  	  }

      System.out.println("CorbaClientServlet: Invoking narrow on first object.");
    	simple1 = SimpleObjectHelper.narrow(objref1);

      System.out.println("CorbaClientServlet: Invoking narrow on second object.\n");
    	simple2 = SimpleObjectHelper.narrow(objref2);
    }

    private void attemptInitObjectRefs()

    {
      try {
        System.out.println("CorbaClientServlet: Trying to reinitialize object refs.\n");
     	  initObjectRefs();
      }
      catch(Exception e)
      {
        System.out.println("Could not reinitialize object refs.\n");
  	  }
    }
}

