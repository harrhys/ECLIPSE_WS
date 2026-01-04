package com.farbig.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;

import weblogic.ejb.GenericSessionBean;



public class MyEJBImpl extends GenericSessionBean implements SessionBean{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public void ejbCreate()
    {
    }
	public String process(String name) throws RemoteException, Exception{
		
		return name+"-processed successfully";
	}


}
