package test;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;


import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;
import com.oracle.enterprisetest.jsca.getprc.ejb30.IGetPriceRemote;
import com.oracle.enterprisetest.jsca.getqty.ejb30.IGetQuantityRemote;

public class Test {
	public static void main(String args[]) throws Exception {
		InitialContext ctx = getContext();
		IGetPriceRemote getPrc = (IGetPriceRemote)ctx.lookup("GetPriceRef_EJB30_JNDI#com.oracle.enterprisetest.jsca.getprc.ejb30.IGetPriceRemote");
		IGetQuantityRemote getQty = (IGetQuantityRemote)ctx.lookup("GetQuantityRef_EJB30_JNDI#com.oracle.enterprisetest.jsca.getqty.ejb30.IGetQuantityRemote");

		ShoppingCart sCart = new ShoppingCart();
		List<ShoppingCartItem> list = sCart.getItems();

		{
			ShoppingCartItem item = new ShoppingCartItem();
			item.setId(1);
			item.setQuantity(10);
			item.setPrice(1.0);
			list.add(item);
		}

		{
			ShoppingCartItem item = new ShoppingCartItem();
			item.setId(2);
			item.setQuantity(2);
			item.setPrice(1.0);
			list.add(item);
		}

		{
			ShoppingCartItem item = new ShoppingCartItem();
			item.setId(3);
			item.setQuantity(3);
			item.setPrice(1.0);
			list.add(item);
		}

		System.out.println("Total Price: " + getPrc.getTotalPrice(sCart));
		System.out.println("Total Quantity: " + getQty.getTotalQuantity(sCart));
	}

	public static InitialContext getContext() throws Exception {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"weblogic.jndi.WLInitialContextFactory");
		p.put(Context.PROVIDER_URL, "t3://churchill:7001");
		p.put(Context.SECURITY_PRINCIPAL, "defAdmin");
		p.put(Context.SECURITY_CREDENTIALS, "defPwd00");
		InitialContext ctx = new InitialContext(p);
		return ctx;
	}
}