package com.oracle.enterprisetest.jsca.getqty.beans;

import java.util.List;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;

public class GetQuantityImpl {

	public int getTotalQuantity(ShoppingCart sCart) {
		int retQuantity = 0;
		List<ShoppingCartItem> items = sCart.getItems();
		for ( int i=0; i<items.size(); i++ ) {
			ShoppingCartItem item = items.get(i);
			retQuantity += item.getQuantity();
		}
		return retQuantity;
	}

}
