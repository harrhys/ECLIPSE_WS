package com.oracle.enterprisetest.jsca.getprc.beans;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;

public class Bean2 {

	private Bean21 bean21;
	private Bean22 bean22;

	void testFunc() {

	}

	public double getTotalPrice(ShoppingCart sCart) {
		bean21.testFunc();
		bean22.testFunc();
		return bean22.getTotalPrice(sCart);
	}

	public Bean21 getBean21() {
		return bean21;
	}

	public void setBean21(Bean21 bean21) {
		this.bean21 = bean21;
	}

	public Bean22 getBean22() {
		return bean22;
	}

	public void setBean22(Bean22 bean22) {
		this.bean22 = bean22;
	}


}
