package com.farbig.services.ws.calculator.client.dynamicproxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote{
	
	public abstract String add(int a, int b) throws RemoteException ;

}
