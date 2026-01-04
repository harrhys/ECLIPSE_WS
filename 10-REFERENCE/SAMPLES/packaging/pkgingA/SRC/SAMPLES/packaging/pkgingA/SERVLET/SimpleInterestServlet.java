/**
 * Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
 */
package samples.packaging.pkgingA.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import java.util.*;

import samples.packaging.pkging.ejb.SimpleInterest;
import samples.packaging.pkging.ejb.SimpleInterestHome;
import samples.packaging.pkging.lib.DateLibrary;

/**
 * This servlet recives input from SimpleInterest_In.jsp, makes a call to the SimpleInterest bean
 * and forwards to the SimpleInterest_Out.jsp
 *
 * @author iAS Technical Marketing
 * @version 0.8
 */
public class SimpleInterestServlet extends HttpServlet
{
	SimpleInterest interestRemote = null;

	public String getServletInfo()
	{
		return "Servlet that makes call to SimpleInterest Bean";
	}

   /**
    * The doGet method of the servlet. Handles all http GET request.
    * Required by the servlet specification.
    * @exception throws ServletException and IOException.
    */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
	{
	    double principal= 0;
	    double time = 0;
	    double rate = 0;
	    double simpleinterest = 0;
	    double compoundinterest = 0;

	    // get data from form
	    String value=null;
	    try {
	        value = (String)req.getParameter("si_principal");
	        principal = Double.parseDouble(value);

	        value = (String)req.getParameter("si_time");
	        time = Double.parseDouble(value);

	        value = (String)req.getParameter("si_rate");
	        rate = Double.parseDouble(value);
	    }
	    catch (NumberFormatException e) {
	        System.out.println("Error getting numbers from form:"+e.getMessage());
	        e.printStackTrace();
	    }

	    // get bean context
       if (interestRemote == null) {
           interestRemote = this.getremoteEJB();
       }

       if (interestRemote == null) {
          System.out.println("Could not get remote interface to SimpleInterest");
          throw new java.rmi.RemoteException("Could not get interface to SimpleInterest");
       }

        // get simple and compound interest
        try {
            simpleinterest = interestRemote.getSimpleInterest(principal,time,rate);
            compoundinterest = interestRemote.getCompoundInterest(principal,time,rate);
        }
        catch (java.rmi.RemoteException e) {
            System.out.println("Remote Exception thrown : "+ e.getMessage());
            e.printStackTrace();
        }

        // get the number of days till Tax day
        // get today's date
        Calendar gDate = Calendar.getInstance();

        // get April 15, 2003
        GregorianCalendar gTaxDay = new GregorianCalendar();
        gTaxDay.set(2003,4,15);

        long noofdays = (new DateLibrary()).getDaysBetween(gDate, gTaxDay);


        System.out.println("Storing the simple interest in the request object");
        req.setAttribute("si_interest", Double.toString(simpleinterest));

        System.out.println("Storing the compound interest in the request object");
        req.setAttribute("ci_interest", Double.toString(compoundinterest));

        System.out.println("Storing the no of days till tax day, 2003");
        req.setAttribute("noofdays", Long.toString(noofdays));


        System.out.println("Dispatching JSP for output");
        resp.setContentType("text/html");
        RequestDispatcher dispatcher;
        dispatcher = getServletContext().getRequestDispatcher
         ("/SimpleInterest_Out.jsp");
        dispatcher.include(req, resp);
        return;



	}

   /**
    * The doPost method of the servlet. Handles all http POST request.
    * Required by the servlet specification.
    * @exception throws ServletException and IOException.
    */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
	{
		doGet(req,resp);

	}

	// ---------------------------------------------------------------
	// private methods
    private SimpleInterest getremoteEJB() {

         SimpleInterestHome myInterestHome = null;
         SimpleInterest myInterestRemote = null;

         InitialContext initContext = null;
         System.out.println("\nInside SimpleInterestServlet.getRemoteEJB...");

         System.out.println("Retrieving JNDI initial context");
         try    {
            initContext = new javax.naming.InitialContext();
         }
         catch (Exception e) {
            System.out.println("Exception creating InitialContext: " + e.toString());
            return null;
         }
         if (initContext == null) {
            System.out.println("Null Pointer - JNDI context");
            return null;
         }

        try {
          System.out.println("Looking up SimpleInterest bean home interface");
          String JNDIName = "java:comp/env/ejb/SunONE.pkging.pkgingEJB.SimpleInterest";
          System.out.println("Looking up: " + JNDIName);
          myInterestHome = (SimpleInterestHome)initContext.lookup(JNDIName);
        }
        catch(Exception e) {
           System.out.println("SimpleInterest bean home not found - " +  "Is bean registered with JNDI?: " + e.toString());
            e.printStackTrace();
        return null;
        }
        try {
          System.out.println("Creating the SimpleInterest bean");
          myInterestRemote = myInterestHome.create();
        }
        catch(javax.ejb.CreateException e) {
          System.out.println("Could not create the SimpleInterest bean: "+
           e.toString());
          e.printStackTrace();
          return null;
        }
        catch(java.rmi.RemoteException e1) {
          System.out.println("Could not create the SimpleInterest bean: "+e1.toString());
          e1.printStackTrace();
          return null;
        }

        return myInterestRemote;

    }


}
