package com.oracle.enterprisetest.jsca.getqty.svc.ws;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetQuantity {

  public int getTotalQuantity(ShoppingCart sCart);
}

