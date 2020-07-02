package com.farbig.cart.services;

import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.Customer;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.entity.User;
import com.farbig.cart.services.gateway.util.BeanType;
import com.farbig.cart.services.gateway.util.BeanType.Type;
import com.farbig.cart.services.gateway.util.ReturnHandlerService;

public interface UserServices extends ReturnHandlerService{
	
	public String SERVICE_NAME = UserServices.class.getName();
	
	@BeanType(value=Type.BMT)
	public Admin createAdminUserBMT(Admin user) throws Exception;
	
	@BeanType(value=Type.BMT)
	public Merchant createMerchantBMT(Merchant user)throws Exception;
	
	@BeanType(value=Type.BMT)
	public Customer createCustomerBMT(Customer user)throws Exception;
	
	public Admin createAdminUser(Admin user) throws Exception;
	
	public Merchant createMerchant(Merchant user)throws Exception;
	
	public Customer createCustomer(Customer user)throws Exception;
	
	public User getUser(User user)throws Exception;

	public User login(User user) throws Exception;
	
}
