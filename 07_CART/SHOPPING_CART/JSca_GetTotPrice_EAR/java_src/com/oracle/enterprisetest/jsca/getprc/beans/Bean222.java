package com.oracle.enterprisetest.jsca.getprc.beans;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.getprc.ref.ejb20.IGetPrice;

public class Bean222 {

	IGetPrice getPriceEJB20Ref;
	com.oracle.enterprisetest.jsca.getprc.ref.ejb30.IGetPrice getPriceEJB30Ref;
	com.oracle.enterprisetest.jsca.getprc.ref.ws.IGetPrice getPriceWSRef;

	public void testFunc() {

	}

	public double getTotalPrice(ShoppingCart sCart) {
		double retPrice = 0.0;
		double price1 = getPriceEJB20Ref.getTotalPrice(sCart);
		double price2 = getPriceEJB30Ref.getTotalPrice(sCart);
		double price3 = getPriceWSRef.getTotalPrice(sCart);
		if ( ( price1 == price2 ) && ( price2 == price3 ) ) {
			retPrice = price1;
		}
		return retPrice;
	}

	public IGetPrice getGetPriceEJB20Ref() {
		return getPriceEJB20Ref;
	}

	public void setGetPriceEJB20Ref(IGetPrice getPriceEJB20Ref) {
		this.getPriceEJB20Ref = getPriceEJB20Ref;
	}

	public com.oracle.enterprisetest.jsca.getprc.ref.ejb30.IGetPrice getGetPriceEJB30Ref() {
		return getPriceEJB30Ref;
	}

	public void setGetPriceEJB30Ref(
			com.oracle.enterprisetest.jsca.getprc.ref.ejb30.IGetPrice getPriceEJB30Ref) {
		this.getPriceEJB30Ref = getPriceEJB30Ref;
	}

	public com.oracle.enterprisetest.jsca.getprc.ref.ws.IGetPrice getGetPriceWSRef() {
		return getPriceWSRef;
	}

	public void setGetPriceWSRef(
			com.oracle.enterprisetest.jsca.getprc.ref.ws.IGetPrice getPriceWSRef) {
		this.getPriceWSRef = getPriceWSRef;
	}

}
