package com.weblogic.sample.ejbapp;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.weblogic.sample.common.FubarRequestData;
import com.weblogic.sample.common.FubarResponseData;
import com.weblogic.sample.common.LH;

public abstract class FubarEJBBaseClass implements SessionBean
{
	public final static String VERSION = "#.#";
	public final static String PACKAGE_AND_CLASS = "com.weblogic.sample.ejbapp.FubarEJBBaseClass";
	private static final long serialVersionUID = 947502096794576339L;
	
	private SessionContext ctx = null;
	
	public void ejbActivate()
	{
	}
	public void ejbPassivate()
	{
	}
	public void ejbRemove()
	{
	}
	public void ejbCreate() throws CreateException 
	{
		final String METHOD = "ejbCreate";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}
	public void setSessionContext(SessionContext context)
	{
		final String METHOD = "setSessionContext";
		ctx = context;
		if (ctx == null)
		{
			LH.error(VERSION, PACKAGE_AND_CLASS, METHOD,
				"ctx is null");
		}
	}

	public FubarEJBBaseClass() 
	{
		final String METHOD = "FubarEJBBaseClass";
		LH.trace(VERSION, PACKAGE_AND_CLASS, METHOD,
			"entered");
	}

	public abstract FubarResponseData sampleMethod(
			FubarRequestData inputData) throws RemoteException;
}
