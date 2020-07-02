package com.oracle.enterprisetest.jsca.getprc.ref.ejb30;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetPrice {

  public double getTotalPrice(ShoppingCart sCart);
}

