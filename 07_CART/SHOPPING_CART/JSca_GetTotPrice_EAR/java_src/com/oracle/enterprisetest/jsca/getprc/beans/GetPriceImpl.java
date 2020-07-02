package com.oracle.enterprisetest.jsca.getprc.beans;

import java.util.List;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;

public class GetPriceImpl {

	public double getTotalPrice(ShoppingCart sCart) {
		double retPrice = 0.0;
		List<ShoppingCartItem> items = sCart.getItems();
		for ( int i=0; i<items.size(); i++ ) {
			ShoppingCartItem item = items.get(i);
			retPrice += item.getPrice() * item.getQuantity();
		}
		return retPrice;
	}

}
