package com.oracle.enterprisetest.jsca.getqty.ejb30;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public interface IGetQuantityLocal {

  public int getTotalQuantity(ShoppingCart sCart);
}

