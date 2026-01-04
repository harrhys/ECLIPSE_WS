package com.weblogic.sample.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weblogic.sample.common.FubarRequestData;
import com.weblogic.sample.common.FubarResponseData;
import com.weblogic.sample.common.LH;
import com.weblogic.sample.ejbapp.FubarBMTEJBHome;
import com.weblogic.sample.ejbapp.FubarBMTEJBInterface;

public class FubarServlet extends HttpServlet
{
	public final static String VERSION = "1.0";
	public final static String PACKAGE_AND_CLASS = "com.weblogic.sample.webapp.FubarServlet";
	private static final long serialVersionUID = 2718435154451312167L;
	private FubarBMTEJBInterface ss = null;
	public FubarServlet()
	{
		super();
		final String METHOD = "FubarServlet";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		topLevelHttpRequestHandler(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		topLevelHttpRequestHandler(req, res);
	}
	private void topLevelHttpRequestHandler(HttpServletRequest req, HttpServletResponse res)
	{
		final String METHOD = "topLevelHttpRequestHandler";
		String message = null;
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
    	try
		{
    		FubarRequestData rd = null;
			rd = new FubarRequestData();
			rd.parseFromHTTPRequest(req);
    		
    		if (ss == null)
    		{
    			ss = connectToFubarBMTEJB();
    		}
    		
    		if (ss != null)
    		{
    			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
    				"called with request: ["+rd.dump()+"]");
    			try
    			{
    				FubarResponseData r = ss.sampleMethod(rd);
    				message = r.result;
    			}
    			catch (java.rmi.NoSuchObjectException e)
    			{
    				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
    					"detected stale BMTEJB connection attempting one-time reconnect/retry for request: ["+rd.dump()+"]");
        			ss = connectToFubarBMTEJB();
        			FubarResponseData r = ss.sampleMethod(rd);
    				message = r.result;
    			}
    		}
			else
			{
    			message = VERSION+"-"+PACKAGE_AND_CLASS+"."+METHOD+"():"+
					"->ERROR, unable to connect to BMTEJB";
				LH.error(VERSION, PACKAGE_AND_CLASS, METHOD,
					"unable to connect to BMTEJB");
			}
		}
		catch (Throwable e)
		{
			message = VERSION+"-"+PACKAGE_AND_CLASS+"."+METHOD+"():"+
				"->ERROR, exception occured";
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured", e);
		}
		
    	try
		{
			PrintWriter out = res.getWriter();
	        res.setContentType("text/html");
			out.println("<HTML>");
			out.println("<HEAD><TITLE>FubarServlet</TITLE></HEAD>");
			out.println("<BODY>"+message+"</BODY>");
		}
		catch (Throwable e)
		{
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured", e);
		}
	}
	private static FubarBMTEJBInterface connectToFubarBMTEJB()
	{
		final String METHOD = "connectToFubarBMTEJB";
		FubarBMTEJBHome ssHome = null;
		FubarBMTEJBInterface ss = null;
		try
		{
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Initial connection information ["+
				System.getProperty(javax.naming.Context.PROVIDER_URL)+
				"]/["+
				System.getProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY)+
				"]");
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Establish initial context");
			Hashtable<String,String> h = new Hashtable<String,String>();
			// Setting the following JNDI lookup property tells JNDI
			// that this application understands that it is looking up a
			// versioned object and that this application understands that
			// it needs to reconnect/retry if it encounters a stale handle.
			// Without this, you will get a warning from the JNDI server. 
			// See: http://e-docs.bea.com/wls/docs92/programming/versioning.html#wp1021565
			h.put(weblogic.jndi.WLContext.ALLOW_EXTERNAL_APP_LOOKUP, "true");
			javax.naming.InitialContext initialContext = 
				new javax.naming.InitialContext(h);

			String lun = "com.weblogic.sample.ejbapp.FubarBMTEJBHome";
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Performing lookup on ejb name ["+lun+"]");
			java.lang.Object lookupObject =
				initialContext.lookup(lun);

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Connecting step 1...");
			ssHome = (FubarBMTEJBHome)
				javax.rmi.PortableRemoteObject.narrow(
					lookupObject,
					FubarBMTEJBHome.class);

			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"Connecting step 2...");
			ss = ssHome.create();
		}
		catch (Throwable e)
		{
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occurred", e);
			ss = null;
		}
		return ss;
	}	
	
}
