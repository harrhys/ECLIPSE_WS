package com.oracle.enterprisetest.jsca.getqty.ejb20;

import javax.ejb.EJBLocalObject;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetQuantityLocal extends EJBLocalObject {

  public int getTotalQuantity(ShoppingCart sCart);
}
