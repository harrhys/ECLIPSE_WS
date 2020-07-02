package com.oracle.enterprisetest.jsca.getqty.ref.ejb30;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetQuantity {

  public int getTotalQuantity(ShoppingCart sCart);
}

