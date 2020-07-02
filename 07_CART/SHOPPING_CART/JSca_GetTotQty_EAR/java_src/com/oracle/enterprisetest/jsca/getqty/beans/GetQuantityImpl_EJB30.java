package com.oracle.enterprisetest.jsca.getqty.beans;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.getqty.svc.ejb30.IGetQuantity;

public class GetQuantityImpl_EJB30 implements IGetQuantity {

	public int getTotalQuantity(ShoppingCart sCart) {
		GetQuantityImpl getPrc = new GetQuantityImpl();
		return getPrc.getTotalQuantity(sCart);
	}
}
