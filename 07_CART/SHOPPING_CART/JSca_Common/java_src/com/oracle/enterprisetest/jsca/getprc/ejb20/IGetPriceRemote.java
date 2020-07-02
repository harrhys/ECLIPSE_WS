package com.oracle.enterprisetest.jsca.getprc.ejb20;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetPriceRemote extends EJBObject {

  public double getTotalPrice(ShoppingCart sCart) throws RemoteException;
}

