package com.farbig.cart.test.cases;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.farbig.cart.entity.Account;
import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.services.UserServices;
import com.farbig.cart.services.gateway.util.ServiceHelper;

public class TestUsers {

	UserServices userService = (UserServices) ServiceHelper
			.getServiceProxy(UserServices.class);
	
/*	@Test
	public void testAdminCreation() throws Exception {

		Admin user = new Admin();
		user.setName("CMT JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser1");
		user.setPassword("cart123");
		user.setCreatedBy("TestUsers.java");
		user.setCreatedDate(new Date());
		user = userService.createAdminUser(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		assertTrue(user.getId() > 0);
	}
	
	*/
	
	@Test
	public void testMerchantCreation() throws Exception {

		Merchant user = new Merchant();
		user.setName("FARBIG MERCHANT");
		user.setBusinessName("FARBIG MERCHANT PVT LTD");
		user.setStatus("ACTIVE");
		user.setUsername("merchant1");
		user.setPassword("cart123");
		user.setCreatedBy("TestUsers.java");
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createMerchant(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		assertTrue(user.getId() > 0);

	}


	/*
	
	

	@Test
	public void testAdminCreationBMT() throws Exception {

		Admin user = new Admin();
		user.setName("BMT JTA");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartuser2");
		user.setPassword("cart123");
		user.setCreatedBy("TestUsers.java");
		user.setCreatedDate(new Date());
		user = userService.createAdminUserBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		assertTrue(user.getId() > 0);
	}

	@Test
	public void testMerchantCreationBMT() throws Exception {

		Merchant user = new Merchant();
		user.setName("BMT NON JTA");
		user.setBusinessName("FARBIG MERCHANT");
		user.setStatus("ACTIVE");
		user.setUsername("merchant1");
		user.setPassword("cart123");
		user.setCreatedBy("TestUsers.java");
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createMerchantBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		assertTrue(user.getId() > 0);

	}

	@Test
	public void testCustomerCreation() throws Exception {

		Customer user = new Customer();
		user.setName("CMT JDBC");
		user.setCustomerType("SILVER");
		user.setStatus("ACTIVE");
		user.setUsername("customer1");
		user.setPassword("cart123");
		user.setCreatedBy("TestUsers.java");
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createCustomer(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		assertTrue(user.getId() > 0);

	}

	@Test
	public void testCustomerCreationBMT() throws Exception {

		Customer user = new Customer();
		user.setName("BMT JDBC");
		user.setCustomerType("SILVER");
		user.setStatus("ACTIVE");
		user.setUsername("customer1");
		user.setPassword("cart123");
		user.setCreatedBy("TestUsers.java");
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createCustomerBMT(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		assertTrue(user.getId() > 0);

	}
	*/
	

	public Account getNewAccount() {
		Account a = new Account();
		a.setBalance(0);
		a.setStatus("ACTIVE");
		return a;
	}

}
