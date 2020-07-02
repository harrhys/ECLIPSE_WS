package com.oracle.enterprisetest.jsca.getqty.ejb30;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;

@Stateless(name = "GetQuantityBean30", mappedName = "GetQuantityRef_EJB30_JNDI")
@Remote(IGetQuantityRemote.class)
@Local(IGetQuantityLocal.class)
public class GetQuantityBean implements IGetQuantityRemote {

	@PostConstruct
	public void init() {
	}

	@Override
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
