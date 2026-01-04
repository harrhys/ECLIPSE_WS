package com.farbig.ejb;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;


public interface MyEJB extends EJBObject {
	
	public String process(String name) throws RemoteException, Exception;
}
