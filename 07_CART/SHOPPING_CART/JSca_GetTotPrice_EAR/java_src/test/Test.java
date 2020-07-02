package test;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import test.ws.client.GetPriceSvcWS;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;
import com.oracle.enterprisetest.jsca.getprc.svc.ejb30.IGetPrice;

public class Test {
	public static void main(String args[]) throws Exception {
		invokeEJB();
		//invokeWS();

	}

	private static void invokeWS() {
		GetPriceSvcWS svc = new GetPriceSvcWS();
		test.ws.client.IGetPrice port = svc.getPort(test.ws.client.IGetPrice.class);

		test.ws.client.ShoppingCart sCart1 = new test.ws.client.ShoppingCart();
		List<test.ws.client.ShoppingCartItem> list1 = sCart1.getItems();

		{
			test.ws.client.ShoppingCartItem item = new test.ws.client.ShoppingCartItem();
			item.setId(1);
			item.setQuantity(10);
			item.setPrice(1.0);
			list1.add(item);
		}

		{
			test.ws.client.ShoppingCartItem item = new test.ws.client.ShoppingCartItem();
			item.setId(2);
			item.setQuantity(2);
			item.setPrice(1.0);
			list1.add(item);
		}

		{
			test.ws.client.ShoppingCartItem item = new test.ws.client.ShoppingCartItem();
			item.setId(3);
			item.setQuantity(3);
			item.setPrice(1.0);
			list1.add(item);
		}

		System.out.println("Total Price: " + port.getTotalPrice(sCart1));
	}

	private static void invokeEJB() throws Exception, NamingException {
		InitialContext ctx = getContext();
		IGetPrice getPrc = (IGetPrice)ctx.lookup("GetPriceSvc_EJB30_JNDI");

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