package com.oracle.enterprisetest.jsca.getprc.ejb20;

import javax.ejb.EJBLocalObject;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetPriceLocal extends EJBLocalObject {

  public double getTotalPrice(ShoppingCart sCart);
}
