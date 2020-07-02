package com.oracle.enterprisetest.jsca.getprc.ejb30;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetPriceRemote {

  public double getTotalPrice(ShoppingCart sCart);
}

