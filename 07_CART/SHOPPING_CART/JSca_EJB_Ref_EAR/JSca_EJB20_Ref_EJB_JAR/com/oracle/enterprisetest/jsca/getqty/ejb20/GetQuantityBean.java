package com.oracle.enterprisetest.jsca.getqty.ejb20;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.oracle.enterprisetest.jsca.cart.ShoppingCart;
import com.oracle.enterprisetest.jsca.cart.ShoppingCartItem;

public class GetQuantityBean implements SessionBean {

	public void ejbCreate() {
		//TODO
	}

	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

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
