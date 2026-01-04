package com.weblogic.sample.wsapp;

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.WebMethod;
import javax.jws.WebResult;

import weblogic.jws.Binding;
import weblogic.jws.WLHttpTransport;

import com.weblogic.sample.common.FubarRequestData;
import com.weblogic.sample.common.FubarResponseData;
import com.weblogic.sample.common.LH;
import com.weblogic.sample.ejbapp.FubarBMTEJBHome;
import com.weblogic.sample.ejbapp.FubarBMTEJBInterface;

// See: http://e-docs.bea.com/wls/docs92/webserv/annotations.html#wp1042425
@WebService(
	serviceName="FubarWSApp",
	name="FubarWSAppPortType",
	targetNamespace="http://www.obopay.com")

// See: http://e-docs.bea.com/wls/docs92/webserv/annotations.html#wp1044381
@SOAPBinding(
	style=SOAPBinding.Style.DOCUMENT,
	use=SOAPBinding.Use.LITERAL,
	parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

// See: http://e-docs.bea.com/wls/docs92/webserv/annotations.html#wp1090743
// URI for this web service will be:
//		protocol://host:port/contextPath/serviceUri
@WLHttpTransport(
	contextPath="FubarWSApp",
	serviceUri="Series1")

// See: http://e-docs.bea.com/wls/docs92/webserv/annotations.html#wp1081014
@Binding(
	Binding.Type.SOAP11)

// NOTE: The following pre-9.2 servicegen attributes are no longer used:
//		expandMethods
//		generateTypes
// See: http://e-docs.bea.com/wls/docs92/webserv/upgrade.html#wp209725
	
public class FubarWSApp
{
	public final static String VERSION = "1.0";
	public final static String PACKAGE_AND_CLASS = "com.weblogic.sample.wsapp.FubarWSApp";
	private static final long serialVersionUID = 8220223957152006523L;
	private static FubarBMTEJBInterface fbejbi = null;

	public FubarWSApp() 
	{
		super();
		final String METHOD = "FubarWSApp";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}

	// See: http://e-docs.bea.com/wls/docs92/webserv/annotations.html#wp1042436
	// See: http://e-docs.bea.com/wls/docs92/webserv/annotations.html#wp1043094
	@WebMethod(
		operationName="sampleMethod")
	@WebResult(
		name="sampleMethodResponse", 
		targetNamespace="http://www.obopay.com/FubarResponseData")
	public FubarResponseData sampleMethod(
			FubarRequestData inputData) throws RemoteException
	{
		final String METHOD = "sampleMethod";
		FubarResponseData retval = new FubarResponseData();
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
		if (inputData != null)
		{
			try
			{
				if (fbejbi == null)
				{
					fbejbi = connectToFubarBMTEJB();
				}
				if (fbejbi != null)
				{
					LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
						"called with request: ["+inputData.dump()+"]");
					try
					{
						retval = fbejbi.sampleMethod(inputData);
	    			}
	    			catch (java.rmi.NoSuchObjectException e)
	    			{
						LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
							"detected stale BMTEJB connection attempting one-time reconnect/retry for request: ["+inputData.dump()+"]");
	        			fbejbi = connectToFubarBMTEJB();
	        			retval = fbejbi.sampleMethod(inputData);
	    			}
				}
				else
				{
        			retval.result = VERSION+"-"+PACKAGE_AND_CLASS+"."+METHOD+"():"+
						"->ERROR, unable to connect to BMTEJB";
					LH.error(VERSION, PACKAGE_AND_CLASS, METHOD,
						"unable to connect to BMTEJB");
				}
			}
			catch (Throwable e)
			{
    			retval.result = VERSION+"-"+PACKAGE_AND_CLASS+"."+METHOD+"():"+
					"->ERROR, exception occured";
				LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
					"exception occurred", e);
			}
		}
		else
		{
			retval.result = VERSION+"-"+PACKAGE_AND_CLASS+"."+METHOD+"():"+
				"->ERROR, null request";
			LH.error(VERSION, PACKAGE_AND_CLASS, METHOD,
				"null request");
		}
		return retval;
	}
	private static FubarBMTEJBInterface connectToFubarBMTEJB()
	{
		final String METHOD = "connectToFubarBMTEJB";
		FubarBMTEJBHome ssHome = null;
		FubarBMTEJBInterface tfbejbi = null;
		try
		{
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
			tfbejbi = ssHome.create();
		}
		catch (Throwable e)
		{
			LH.exception(VERSION, PACKAGE_AND_CLASS, METHOD,
				"exception occured", e);
			tfbejbi = null;
		}
		return tfbejbi;
	}
}
