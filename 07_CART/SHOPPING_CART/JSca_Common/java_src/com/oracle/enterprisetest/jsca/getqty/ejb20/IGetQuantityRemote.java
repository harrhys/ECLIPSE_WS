package com.oracle.enterprisetest.jsca.getqty.ejb20;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetQuantityRemote extends EJBObject {

  public int getTotalQuantity(ShoppingCart sCart) throws RemoteException;
}

