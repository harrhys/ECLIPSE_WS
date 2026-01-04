package com.farbig.ejb;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface MyEJBHome extends EJBHome{
	
	  public MyEJB create() throws CreateException, RemoteException;

}
