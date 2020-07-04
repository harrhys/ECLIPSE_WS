package com.oracle.enterprisetest.jsca.getqty.beans;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.getqty.svc.ws.IGetQuantity;

public class GetQuantityImpl_WS implements IGetQuantity {

	private Bean1 bean1;
	private Bean2 bean2;

	public int getTotalQuantity(ShoppingCart sCart) {
		//System.out.println("************getTotalQuantity called**********");
		bean1.testFunc();
		bean2.testFunc();
		return bean2.getTotalQuantity(sCart);
	}

	public Bean1 getBean1() {
		return bean1;
	}

	public void setBean1(Bean1 bean1) {
		this.bean1 = bean1;
	}

	public Bean2 getBean2() {
		return bean2;
	}

	public void setBean2(Bean2 bean2) {
		this.bean2 = bean2;
	}

}