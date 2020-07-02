package com.oracle.enterprisetest.jsca.getprc.ejb30;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;

@Stateless(name = "GetPriceBean30", mappedName = "GetPriceRef_EJB30_JNDI")
@Remote(IGetPriceRemote.class)
@Local(IGetPriceLocal.class)
public class GetPriceBean implements IGetPriceRemote {

	@PostConstruct
	public void init() {
	}

	@Override
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
