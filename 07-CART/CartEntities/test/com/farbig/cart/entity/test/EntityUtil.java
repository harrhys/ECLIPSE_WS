package com.farbig.cart.entity.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.farbig.cart.entity.Account;
import com.farbig.cart.entity.Address;
import com.farbig.cart.entity.AddressType;
import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.entity.Customer;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.entity.Store;
import com.farbig.cart.persistence.PersistenceHandler;

public class EntityUtil {

	public Admin createAdmin(String username) {
		Admin user = new Admin();
		user.setName("Admin");
		user.setStatus("ACTIVE");
		user.setUsername(username);
		user.setPassword("cart123");
		user.setAdminType("ROOT");
		user.setCreatedBy(CustomerTestCases.class.getSimpleName());
		user.setCreatedDate(new Date());
		return user;
	}

	public String getUserQuery(String usertype) {

		return "select u from " + usertype + " u where u.username = :username";
	}

	public Customer createCustomer(String username) {
		Customer user = new Customer();
		user.setName("Customer");
		user.setCustomerType("SILVER");
		user.setStatus("ACTIVE");
		user.setUsername(username);
		user.setPassword("cart123");
		user.setCreatedBy(CustomerTestCases.class.getSimpleName());
		user.setCreatedDate(new Date());
		return user;
	}

	public Merchant createMerchant(String username) {
		Merchant user = new Merchant();
		user.setName("Merchant");
		user.setBusinessName("Farbig Merchant");
		user.setStatus("ACTIVE");
		user.setUsername(username);
		user.setPassword("cart123");
		user.setCreatedBy(CustomerTestCases.class.getSimpleName());
		user.setCreatedDate(new Date());
		return user;
	}

	public Address createAddress(AddressType type) {
		Address address = new Address();
		address.setAddressType(type);
		address.setStreet("Saroj Residency, Silver Spring road");
		address.setCity("bangalore");
		address.setState("Karnataka");
		address.setPincode("560037");
		return address;
	}

	public Account createAccount() {
		Account act = new Account();
		act.setBalance(9999);
		act.setStatus("ACTIVE");
		return act;
	}

	public Store createStore(Merchant user) {
		Store store = new Store();
		store.setStoreName("Farbig Store");
		store.setDescription("Farbig Merchant Store");
		store.setMerchant(user);
		return store;
	}

	public ProductCategory getProductCategory(PersistenceHandler handler, String category) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		params.put("name", category);
		List<CartEntity> pc1 = handler.getList("select a from productcategory a where a.name = :name", params);
		ProductCategory pc = null;
		if (pc1 != null && pc1.size() > 0) {

			pc = (ProductCategory) pc1.get(0);
			System.out.println("Product Category" + pc.getId());
		}
		return pc;
	}

	public Product createProduct(Store store, ProductCategory category, String code) {

		Product product = new Product();
		product.setName("Prod-" + code);
		product.setDescription(category.getName() + "-Product-" + code);
		product.setProductCode(code);
		product.setProductCategory(category);
		product.setStore(store);
		Random r = new Random();
		product.setPrice(r.nextFloat());
		return product;
	}

	public Product createProductCategory() {
		return null;
	}

}
