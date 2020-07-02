package com.oracle.enterprisetest.jsca.getqty.beans;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.getqty.ref.ejb20.IGetQuantity;

public class Bean222 {

	IGetQuantity getQuantityEJB20Ref;
	com.oracle.enterprisetest.jsca.getqty.ref.ejb30.IGetQuantity getQuantityEJB30Ref;
	com.oracle.enterprisetest.jsca.getqty.ref.ws.IGetQuantity getQuantityWSRef;

	public void testFunc() {

	}

	public int getTotalQuantity(ShoppingCart sCart) {
		int retQty = 0;
		int qty1 = getQuantityEJB20Ref.getTotalQuantity(sCart);
		int qty2 = getQuantityEJB30Ref.getTotalQuantity(sCart);
		int qty3 = getQuantityWSRef.getTotalQuantity(sCart);
		if ( ( qty1 == qty2 ) && ( qty2 == qty3 ) ) {
			retQty = qty1;
		}
		return retQty;
	}

	public IGetQuantity getGetQuantityEJB20Ref() {
		return getQuantityEJB20Ref;
	}

	public void setGetQuantityEJB20Ref(IGetQuantity getQuantityEJB20Ref) {
		this.getQuantityEJB20Ref = getQuantityEJB20Ref;
	}

	public com.oracle.enterprisetest.jsca.getqty.ref.ejb30.IGetQuantity getGetQuantityEJB30Ref() {
		return getQuantityEJB30Ref;
	}

	public void setGetQuantityEJB30Ref(
			com.oracle.enterprisetest.jsca.getqty.ref.ejb30.IGetQuantity getQuantityEJB30Ref) {
		this.getQuantityEJB30Ref = getQuantityEJB30Ref;
	}

	public com.oracle.enterprisetest.jsca.getqty.ref.ws.IGetQuantity getGetQuantityWSRef() {
		return getQuantityWSRef;
	}

	public void setGetQuantityWSRef(
			com.oracle.enterprisetest.jsca.getqty.ref.ws.IGetQuantity getQuantityWSRef) {
		this.getQuantityWSRef = getQuantityWSRef;
	}

}
