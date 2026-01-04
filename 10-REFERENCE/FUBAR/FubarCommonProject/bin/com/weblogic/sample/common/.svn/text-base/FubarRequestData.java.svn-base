package com.weblogic.sample.common;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

public class FubarRequestData implements Serializable
{
	public final static String VERSION = "#.#";
	public final static String PACKAGE_AND_CLASS = "com.weblogic.sample.common.FubarRequestData";
	private static final long serialVersionUID = -7943508950409919335L;
	public String clientID = "NOT_SET";
	public boolean asyncFlag = false;
	public boolean publishFlag = false;
	public int sleepTime = 0;
	public int reqNumber = 0;

	public FubarRequestData()
	{
		super();
	}
	public String dump()
	{
		String retval = "";
		retval = VERSION+"-"+
			clientID+"-"+
			asyncFlag+"-"+
			publishFlag+"-"+
			sleepTime+"-"+
			reqNumber;
		return retval;
	}
	public String convertToString()
	{
		String retval = "";
		retval =
			clientID+";"+
			asyncFlag+";"+
			publishFlag+";"+
			sleepTime+";"+
			reqNumber;
		return retval;
	}
	public String convertToURLParameters()
	{
		String retval = "";
		retval =
			"clientID="+clientID+
			"&asyncFlag="+asyncFlag+
			"&publishFlag="+publishFlag+
			"&sleepTime="+sleepTime+
			"&reqNumber="+reqNumber;
		return retval;
	}
	public void parseFromString(String inputData)
	{
		final String METHOD = "parseFromString";
		String[] toks = inputData.split(";");
		if ((toks != null) && (toks.length >= 5))
		{
			clientID = toks[0];
			if ((toks[1] != null) && (toks[1].compareToIgnoreCase("true") == 0))
			{
				asyncFlag = true;
			}
			if ((toks[2] != null) && (toks[2].compareToIgnoreCase("true") == 0))
			{
				publishFlag = true;
			}
			try
			{
				Integer ti = new Integer(toks[3]);
				sleepTime = ti.intValue();
			}
			catch (Throwable e)
			{
				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"invalid value ["+toks[3]+"] setting default sleepTime value");
			}
			try
			{
				Integer ti = new Integer(toks[4]);
				reqNumber = ti.intValue();
			}
			catch (Throwable e)
			{
				LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
					"invalid value ["+toks[4]+"] setting default reqNumber value");
			}
		}
		else
		{
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"invalid request ["+inputData+"], setting default values");
		}
	}
	public void parseFromHTTPRequest(HttpServletRequest req)
	{
		final String METHOD = "parseFromHTTPRequest";
		String tmp = null;
		tmp = req.getParameter("clientID");
		if (tmp != null)
		{
			clientID = tmp;
		}
		else
		{
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"missing value, setting default clientID value");
		}
		tmp = req.getParameter("asyncFlag");
		if ((tmp != null) && (tmp.compareToIgnoreCase("true") == 0))
		{
			asyncFlag = true;
		}
		tmp = req.getParameter("publishFlag");
		if ((tmp != null) && (tmp.compareToIgnoreCase("true") == 0))
		{
			publishFlag = true;
		}
		try
		{
			tmp = req.getParameter("sleepTime");
			Integer ti = new Integer(tmp);
			sleepTime = ti.intValue();
		}
		catch (Throwable e)
		{
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"invalid request, setting default sleepTime value");
		}
		try
		{
			tmp = req.getParameter("reqNumber");
			Integer ti = new Integer(tmp);
			reqNumber = ti.intValue();
		}
		catch (Throwable e)
		{
			LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
				"invalid request, setting default reqNumber value");
		}
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public boolean isAsyncFlag() {
		return asyncFlag;
	}
	public void setAsyncFlag(boolean asyncFlag) {
		this.asyncFlag = asyncFlag;
	}
	public boolean isPublishFlag() {
		return publishFlag;
	}
	public void setPublishFlag(boolean publishFlag) {
		this.publishFlag = publishFlag;
	}
	public int getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	public int getReqNumber() {
		return reqNumber;
	}
	public void setReqNumber(int reqNumber) {
		this.reqNumber = reqNumber;
	}
}
