package com.oracle.enterprisetest.jsca.getqty.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;

@WebService(serviceName="GetQuantityRef_WS_Name", portName="GetQuantityRef_WS_Port", targetNamespace="http://jsca.oracle.com")
public class GetTotalQuantity {

	@WebMethod
	public int getTotalQuantity(ShoppingCart sCart) {
		int retQty = 0;
		List<ShoppingCartItem> items = sCart.getItems();
		for ( int i=0; i<items.size(); i++ ) {
			ShoppingCartItem item = items.get(i);
			retQty += item.getQuantity();
		}
		return retQty;
	}

}
