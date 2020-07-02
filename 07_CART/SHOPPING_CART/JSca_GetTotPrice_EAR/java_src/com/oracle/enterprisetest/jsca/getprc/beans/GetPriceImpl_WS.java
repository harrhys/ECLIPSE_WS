package com.oracle.enterprisetest.jsca.getprc.beans;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.getprc.svc.ws.IGetPrice;

public class GetPriceImpl_WS implements IGetPrice {

	public double getTotalPrice(ShoppingCart sCart) {
		GetPriceImpl getPrc = new GetPriceImpl();
		return getPrc.getTotalPrice(sCart);
	}

}
