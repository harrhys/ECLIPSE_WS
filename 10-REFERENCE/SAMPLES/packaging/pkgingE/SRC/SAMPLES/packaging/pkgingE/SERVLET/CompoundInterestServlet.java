//Copyright © 2002 Sun Microsystems, Inc. All rights reserved.
package samples.packaging.pkgingE.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import java.util.*;

import samples.packaging.pkging.ejb.CompoundInterest;
import samples.packaging.pkging.ejb.CompoundInterestHome;
import samples.packaging.pkging.lib.DateLibrary;
import samples.packaging.pkgingE.lib.Props;


/**
 * This servlet recives input from CompoundInterest_In.jsp, make a call to the CompoundInterest bean
 * and forwards to the CompoundInterest_Out.jsp
 *
 * @author iAS Technical Marketing
 * @version 0.5
 */
public class CompoundInterestServlet extends HttpServlet
{
	CompoundInterest interestRemote = null;
	Properties props = null;

 /*
 * The getServletInfo method of the servlet returns a String
 * that describes the function of this servlet.
 * @return String with information about servlet.
 */
	public String getServletInfo()
	{
		return "Servlet that makes call to CompoundInterest Bean";
	}

    /**
     *. Handles all http GET request.
     *  Required by the servlet specification.
     *  @exception throws ServletException and IOException.
     */

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException
	{
	    double principal= 0;
	    double time = 0;
	    double rate = 0;
	    double compoundinterest = 0;
	    double simpleinterest = 0;

	    // get data from form
	    String value=null;
	    try {
	        value = (String)req.getParameter("ci_principal");
	        principal = Double.parseDouble(value);

	        value = (String)req.getParameter("ci_time");
	        time = Double.parseDouble(value);

	        value = (String)req.getParameter("ci_rate");
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
          System.out.println("Could not get remote interface to CompoundInterest");
          throw new java.rmi.RemoteException("Could not get interface to CompoundInterest");
       }

        // get simple and compound interest
        try {
            compoundinterest = interestRemote.getCompoundInterest(principal,time,rate);
            simpleinterest = interestRemote.getSimpleInterest(principal,time,rate);
        }
        catch (java.rmi.RemoteException e) {
            System.out.println("Remote Exception thrown : "+ e.getMessage());
            e.printStackTrace();
        }

        // get the number of days till Tax day
        // get today's date
        Calendar gDate = Calendar.getInstance();

        // get April 15, 2001
        GregorianCalendar gTaxDay = new GregorianCalendar();
        gTaxDay.set(2001,4,15);

        long noofdays = (new DateLibrary()).getDaysBetween(gDate, gTaxDay);


        req.setAttribute("ci_interest", Double.toString(compoundinterest));

        req.setAttribute("si_interest", Double.toString(simpleinterest));


        req.setAttribute("noofdays", Long.toString(noofdays));

        // get recommended values from the interest.properties file
        String strFile = getServletContext().getResource("/WEB-INF/interest.properties").toString();

        if ((props = Props.getProperties()) == null) {
            Props.loadProperties(strFile);
            props = Props.getProperties();
        }

        // store suggested values for later use
        String strValues = new String();

        if (props == null) {
            System.out.println("No suggested values");
            strValues = "No suggested values";
        } else {
            props.list(System.out);
            String ci_principal = props.getProperty("ci_principal");
            String ci_rate = props.getProperty("ci_rate");
            String ci_time = props.getProperty("ci_time");
            strValues = "<br>Principal : $"+ci_principal+
                        "<br>Rate : $"+ci_rate+
                        "<br>Time : "+ci_time;
        }
        req.setAttribute("strValues",strValues);


        resp.setContentType("text/html");
        RequestDispatcher dispatcher;
        dispatcher = getServletContext().getRequestDispatcher
         ("/CompoundInterest_Out.jsp");
        dispatcher.include(req, resp);
        return;



	}

/**
  * . Handles all http POST request.
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
    private CompoundInterest getremoteEJB() {

         CompoundInterestHome myInterestHome = null;
         CompoundInterest myInterestRemote = null;

         InitialContext initContext = null;

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
          String JNDIName = "java:comp/env/ejb/SunONE.pkging.pkgingEJB.CompoundInterest";
          myInterestHome = (CompoundInterestHome)initContext.lookup(JNDIName);
        }
        catch(Exception e) {
           System.out.println("CompoundInterest bean home not found - " +  "Is bean registered with JNDI?: " + e.toString());
            e.printStackTrace();
        return null;
        }
        try {
          myInterestRemote = myInterestHome.create();
        }
        catch(javax.ejb.CreateException e) {
          System.out.println("Could not create the CompoundInterest bean: "+
           e.toString());
          e.printStackTrace();
          return null;
        }
        catch(java.rmi.RemoteException e1) {
          System.out.println("Could not create the CompoundInterest bean: "+e1.toString());
          e1.printStackTrace();
          return null;
        }

        return myInterestRemote;

    }


}
