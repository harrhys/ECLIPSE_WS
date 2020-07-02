package com.oracle.enterprisetest.jsca.getprc.ejb30;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetPriceLocal {

  public double getTotalPrice(ShoppingCart sCart);
}

