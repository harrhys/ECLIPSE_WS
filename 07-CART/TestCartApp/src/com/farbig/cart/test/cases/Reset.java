package com.farbig.cart.test.cases;

import java.util.Date;

import com.farbig.cart.entity.Account;
import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.services.UserServices;
import com.farbig.cart.services.gateway.util.ServiceHelper;

public class Reset {
	
	public static void main(String args[]) throws Exception
	{
		UserServices userService = (UserServices) ServiceHelper
				.getServiceProxy(UserServices.class);
		
		Admin user = new Admin();
		user.setName("ROOT ADMIN");
		user.setAdminType("ROOT");
		user.setStatus("ACTIVE");
		user.setUsername("cartadmin");
		user.setPassword("cart123");
		user.setCreatedBy("TestUsers.java");
		user.setCreatedDate(new Date());
		Account a = getNewAccount();
		user.setAccount(a);
		user = userService.createAdminUser(user);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}
	
	public static Account getNewAccount() {
		Account a = new Account();
		a.setBalance(0);;
		a.setStatus("ACTIVE");
		return a;
	}


}
