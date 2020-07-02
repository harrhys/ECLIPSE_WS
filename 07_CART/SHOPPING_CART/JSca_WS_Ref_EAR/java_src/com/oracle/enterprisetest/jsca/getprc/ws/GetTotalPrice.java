package com.oracle.enterprisetest.jsca.getprc.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;

@WebService (serviceName="GetPriceRef_WS_Name", portName="GetPriceRef_WS_Port", targetNamespace="http://jsca.oracle.com")
public class GetTotalPrice {

	@WebMethod
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
