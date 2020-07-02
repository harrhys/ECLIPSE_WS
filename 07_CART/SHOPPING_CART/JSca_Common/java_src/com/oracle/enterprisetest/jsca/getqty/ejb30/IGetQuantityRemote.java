package com.oracle.enterprisetest.jsca.getqty.ejb30;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetQuantityRemote {

  public int getTotalQuantity(ShoppingCart sCart);
}

