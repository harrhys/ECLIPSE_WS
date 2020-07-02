package com.farbig.cart.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.entity.Customer;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.entity.User;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.PersistenceHandlerFactory;
import com.farbig.cart.persistence.TxnMgmtType;
import com.farbig.cart.services.gateway.util.BeanType;
import com.farbig.cart.services.gateway.util.BeanType.Type;
import com.farbig.cart.services.gateway.util.GatewayMessage;
import com.farbig.cart.services.gateway.util.ReturnHandlerService;

public class UserServicesImpl implements UserServices, ReturnHandlerService {

	
	@Override
	public Admin createAdminUser(Admin user) {
		
		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());
		
		handler.openSession();
		handler.save(user);
		handler.closeSession();

		return user;
	}

	@Override
	public Merchant createMerchant(Merchant user) {

		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());

		handler.openSession();
		handler.save(user);
		handler.closeSession();

		return user;
	}

	@Override
	public Customer createCustomer(Customer user) {

		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());

		handler.openSession();
		handler.save(user);
		handler.closeSession();
		return user;
	}

	@Override
	@BeanType(value = Type.BMT)
	public Admin createAdminUserBMT(Admin user) {

		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());

		handler.openSession();
		handler.save(user);
		handler.closeSession();

		return user;
	}

	@Override
	@BeanType(value = Type.BMT)
	public Merchant createMerchantBMT(Merchant user) {

		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());

		handler.openSession();
		handler.save(user);
		handler.closeSession();

		return user;
	}

	@Override
	@BeanType(value = Type.BMT)
	public Customer createCustomerBMT(Customer user) {

		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());

		handler.openSession();
		handler.save(user);
		handler.closeSession();
		return user;
	}

	@Override
	public void handleReturnService(GatewayMessage orignalMsg,
			Object tgtServiceResponse) {

		System.out.println("Inside Handler Return service"
				+ tgtServiceResponse.getClass());
	}

	@Override
	public User getUser(User user) throws Exception {

		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());

		Map params = new HashMap();
		params.put("username", user.getUsername());

		List<CartEntity> users = handler.getList(
				"from user where username:username", params);

		return (User) users.get(0);
	}

	@Override
	public User login(User user) throws Exception {

		User loggedInUser = null;

		PersistenceHandler handler = PersistenceHandlerFactory
				.getDataHandler(user.getTxnMgmtType());

		Map params = new HashMap();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());

		List users = handler.getList("login", params);

		if (users!=null && users.size() > 0)
		{
			System.out.println("size" + users.size());
			loggedInUser = (User) users.get(0);
		}
		else
			throw new Exception("Login Failed");

		return loggedInUser;
	}

}
