package com.weblogic.sample.clientapp;

import com.weblogic.sample.common.FubarRequestData;
import com.weblogic.sample.common.FubarResponseData;

//Usage: FubarWSAppClient [protocol://host:port] [iterations] [client sleep time] [client id] [async flag] [publish flag] [server sleep time]

public class FubarWSAppClient extends FubarAppClientBaseClass
{
	public final static String default_protocolhostandport = "http://localhost:80";
	public final static String webServiceURLSuffix = "/FubarWSApp/Series1?WSDL";
	private static FubarWSApp test;
	private static FubarWSAppPortType port;
	
	public String webServiceURL = null;
	
	public static void main(String[] args)
	{
		FubarWSAppClient oneofme = new FubarWSAppClient();

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
		req.setClientID(clientId+"");
		req.setAsyncFlag(asyncFlag);
		req.setPublishFlag(publishFlag);
		req.setSleepTime(serverSleepTime);
		req.setReqNumber(requestNumber);

		System.out.println("webServiceRequest: ["+
				clientId+asyncFlag+publishFlag+serverSleepTime+requestNumber+"]");
		try
		{
			res = port.sampleMethod(req);
		}
		catch (Throwable e)
		{
			res = null;
			System.out.println("Exception invoking webservice: "+e);
		}
		if ((res == null) || (res.getResult().indexOf("FubarBMTEJB") < 0) ||
			(res.getResult().indexOf("ERROR") >= 0))
		{
			System.out.println("webServiceResponse: ["+res+"]");
			System.out.println("ERROR: Negative result");
		}
		else
		{
			System.out.println("webServiceResponse: ["+res.getResult()+"]");
			System.out.println(
				"SUCCESS: Positive result for iteration: ["+requestNumber+"]");
		}
	}
	
	public void setup(
		String phporfiller, int clientId, boolean asyncFlag,
		boolean publishFlag, int serverSleepTime)
	{
		webServiceURL = default_protocolhostandport+webServiceURLSuffix;
		if (phporfiller != null)
		{
			webServiceURL = phporfiller+webServiceURLSuffix;
		}
		try
		{
			test = new FubarWSApp_Impl(webServiceURL);
			port = test.getFubarWSAppPortTypeSoapPort();
		}
		catch (Throwable e)
		{
			System.out.println("Exception connecting to webservice: "+e);
		}
	}
}