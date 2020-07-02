package com.oracle.enterprisetest.jsca.getprc.svc.ws;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetPrice {

  public double getTotalPrice(ShoppingCart sCart);
}

