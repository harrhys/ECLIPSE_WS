package com.weblogic.sample.clientapp;

import java.io.*;
import java.net.*;

import com.weblogic.sample.common.FubarRequestData;

//Usage: FubarWebAppClient [protocol://host:port] [iterations] [client sleep time] [client id] [async flag] [publish flag] [server sleep time]

public class FubarWebAppClient extends FubarAppClientBaseClass
{
	public final static String default_protocolhostandport = "http://localhost:80";
	public final static String servletURLSuffix = "/fubar";
	public final static int BUF_SIZE = 1024;
	
	public String servletURL = null;
	
	public static void main(String[] args)
	{
		FubarWebAppClient oneofme = new FubarWebAppClient();

		oneofme.go(args);
	}
	
	public void doaniteration(
			String phporfiller, int clientId, boolean asyncFlag,
			boolean publishFlag, int serverSleepTime,
			int requestNumber)
	{
		FubarRequestData req = null;
		String res = null;
		
		req = new FubarRequestData();
		req.clientID = clientId+"";
		req.asyncFlag = asyncFlag;
		req.publishFlag = publishFlag;
		req.sleepTime = serverSleepTime;
		req.reqNumber = requestNumber;
		String tsr = servletURL+"?"+req.convertToURLParameters();
		
		System.out.println("servletRequest: ["+tsr+"]");
		res = invokeServletViaRawHTTPRequestString(tsr);
		System.out.println("servletResponse: ["+res+"]");
		if ((res == null) || (res.indexOf("FubarBMTEJB") < 0) ||
				(res.indexOf("ERROR") >= 0))
		{
			System.out.println("ERROR: Negative result");
		}
		else
		{
			System.out.println(
				"SUCCESS: Positive result for iteration: ["+requestNumber+"]");
		}
	}

	public void setup(
		String phporfiller, int clientId, boolean asyncFlag,
		boolean publishFlag, int serverSleepTime)
	{
		servletURL = default_protocolhostandport+servletURLSuffix;
		if (phporfiller != null)
		{
			servletURL = phporfiller+servletURLSuffix;
		}
	}
	
	public String invokeServletViaRawHTTPRequestString(String theURL)
	{
		String retval = null;

		try
		{
			URL url = null;
			InputStream inStream = null;
			url = new URL(theURL);
			inStream = url.openStream();
			byte[] buf = new byte[BUF_SIZE];
			String tmp = null;
			StringBuffer httpResponseBuffer = new StringBuffer();
			int bytes = -1;
			do
			{
				bytes = inStream.read(buf, 0, BUF_SIZE);
				if (bytes > 0)
				{
					tmp = new String(buf, 0, bytes);
					httpResponseBuffer.append(tmp);
				}
			}
			while (bytes > 0);
			inStream.close();
			retval = httpResponseBuffer.toString();
		}
		catch (Throwable e)
		{
			System.out.println("ERROR: "+e);
		}
		
		return retval;
	}
	
}