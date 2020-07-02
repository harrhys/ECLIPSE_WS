package com.oracle.enterprisetest.jsca.getprc.ref.ejb20;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetPrice {

  public double getTotalPrice(ShoppingCart sCart);
}

