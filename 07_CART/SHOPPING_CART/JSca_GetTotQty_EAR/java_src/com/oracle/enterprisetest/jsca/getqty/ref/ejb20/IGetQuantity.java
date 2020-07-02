package com.oracle.enterprisetest.jsca.getqty.ref.ejb20;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetQuantity {

  public int getTotalQuantity(ShoppingCart sCart);
}

