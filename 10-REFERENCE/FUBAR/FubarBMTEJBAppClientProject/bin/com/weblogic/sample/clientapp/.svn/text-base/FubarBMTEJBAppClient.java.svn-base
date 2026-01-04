package com.weblogic.sample.clientapp;

import java.util.Hashtable;

import com.weblogic.sample.common.FubarRequestData;
import com.weblogic.sample.common.FubarResponseData;
import com.weblogic.sample.ejbapp.FubarBMTEJBHome;
import com.weblogic.sample.ejbapp.FubarBMTEJBInterface;

//Usage: FubarBMTEJBAppClient [filler] [iterations] [client sleep time] [client id] [async flag] [publish flag] [server sleep time]

public class FubarBMTEJBAppClient extends FubarAppClientBaseClass
{
	public FubarBMTEJBInterface ss = null;

	public static void main(String[] args)
	{
		FubarBMTEJBAppClient oneofme = new FubarBMTEJBAppClient();

		oneofme.go(args);
	}
	
	public void doaniteration(
			String phporfiller, int clientId, boolean asyncFlag,
			boolean publishFlag, int serverSleepTime,
			int requestNumber)
	{
		FubarRequestData req = null;
		FubarResponseData res = null;
		
		req = new FubarRequestData();
		req.clientID = clientId+"";
		req.asyncFlag = asyncFlag;
		req.publishFlag = publishFlag;
		req.sleepTime = serverSleepTime;
		req.reqNumber = requestNumber;
		
		System.out.println("ejbRequest: ["+req.dump()+"]");
		try
		{
			res = ss.sampleMethod(req);
		}
		catch (Throwable e)
		{
			System.out.println(
				"Detected stale BMTEJB connection attempting one-time reconnect/retry for request: ["+
				req.dump()+"] "+e);
			try
			{
				setup(phporfiller, clientId, asyncFlag,
					publishFlag, serverSleepTime);

				res = ss.sampleMethod(req);
			}
			catch (Throwable f)
			{
				res = null;
				System.out.println("Exception invoking ejb: "+e);
			}
		}

		if ((res == null) || (res.result.indexOf("FubarBMTEJB") < 0) ||
				(res.result.indexOf("ERROR") >= 0))
		{
			System.out.println("ejbResponse: ["+res+"]");
			System.out.println("ERROR: Negative result");
		}
		else
		{
			System.out.println("ejbResponse: ["+res.result+"]");
			System.out.println(
				"SUCCESS: Positive result for iteration: ["+requestNumber+"]");
		}
	}
	
	public void setup(
		String phporfiller, int clientId, boolean asyncFlag,
		boolean publishFlag, int serverSleepTime)
	{
		FubarBMTEJBHome ssHome = null;
		ss = null;
		try
		{
			System.out.println("Initial connection information ["+
					System.getProperty(javax.naming.Context.PROVIDER_URL)+
					"]/["+
					System.getProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY)+
			"]");
			Hashtable<String,String> h = new Hashtable<String,String>();
			h.put(javax.naming.Context.PROVIDER_URL, 
				System.getProperty(javax.naming.Context.PROVIDER_URL)); 
			h.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, 
					System.getProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY));
			// Setting the following JNDI lookup property tells JNDI
			// that this application understands that it is looking up a
			// versioned object and that this application understands that
			// it needs to reconnect/retry if it encounters a stale handle.
			// Without this, you will get a warning from the JNDI server. 
			// See: http://download.oracle.com/docs/cd/E13222_01/wls/docs92/programming/versioning.html#wp1021565
			h.put(weblogic.jndi.WLContext.ALLOW_EXTERNAL_APP_LOOKUP, "true");
			if (System.getProperty(javax.naming.Context.PROVIDER_URL).indexOf("t3s") >= 0)
			{
				h.put(javax.naming.Context.SECURITY_PRINCIPAL, "weblogic");
				h.put(javax.naming.Context.SECURITY_CREDENTIALS, "weblogic");
			}
			javax.naming.InitialContext initialContext = 
				new javax.naming.InitialContext(h);

			String lun = "com.weblogic.sample.ejbapp.FubarBMTEJBHome";
			System.out.println(
				"Performing lookup on ejb name ["+lun+"]");
			java.lang.Object lookupObject =
				initialContext.lookup(lun);

			System.out.println("Connecting step 1...");
			ssHome = (FubarBMTEJBHome)
			javax.rmi.PortableRemoteObject.narrow(
					lookupObject,
					FubarBMTEJBHome.class);

			System.out.println("Connecting step 2...");
			ss = ssHome.create();
		}
		catch (Throwable e)
		{
			System.out.println("Exception occured connecting to ejb "+e);
			ss = null;
		}
	}
}
