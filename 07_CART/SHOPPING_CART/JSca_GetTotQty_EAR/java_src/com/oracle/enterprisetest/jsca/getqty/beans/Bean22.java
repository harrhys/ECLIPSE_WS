package com.oracle.enterprisetest.jsca.getqty.beans;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public class Bean22 {

	private Bean221 bean221;
	private Bean222 bean222;

	void testFunc() {

	}

	public int getTotalQuantity(ShoppingCart sCart) {
		bean221.testFunc();
		bean222.testFunc();
		return bean222.getTotalQuantity(sCart);
	}

	public Bean221 getBean221() {
		return bean221;
	}

	public void setBean221(Bean221 bean221) {
		this.bean221 = bean221;
	}

	public Bean222 getBean222() {
		return bean222;
	}

	public void setBean222(Bean222 bean222) {
		this.bean222 = bean222;
	}

}
