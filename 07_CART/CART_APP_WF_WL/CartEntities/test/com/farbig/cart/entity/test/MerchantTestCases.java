package com.farbig.cart.entity.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.farbig.cart.entity.Address;
import com.farbig.cart.entity.AddressType;
import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.entity.Store;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.PersistenceHandlerFactory;
import com.farbig.cart.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MerchantTestCases extends EntityUtil {

	PersistenceHandler handler;

	@Before
	public void setUp() throws Exception {

		handler = PersistenceHandlerFactory.getDataHandler(TxnMgmtType.JPA_JDBC);
		handler.openConnection();
	}

	@After
	public void tearDown() throws Exception {

		handler.closeConnection();
	}

	@Test
	public void test1_MerchantCreation() {

		Merchant user = createMerchant("merchant1");
		List<Address> adrs = new ArrayList<Address>();
		adrs.add(createAddress(AddressType.HOME));
		adrs.add(createAddress(AddressType.OFFICE));
		user.setAddressList(adrs);
		user.setAccount(createAccount());

		handler.openSession();
		user = (Merchant) handler.save(user);
		handler.closeSession();
		
		Assert.assertNotNull("Merchant is not created", user);
		Assert.assertTrue("Merchant is not created", user.getId() > 0);
		Assert.assertNotNull("Merchant address is not created", user.getAddressList());
		Assert.assertTrue("Merchant account is not created", user.getAccount().getId() > 0);
		
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	@Test
	public void test2_GetMerchant() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		params.put("username", "merchant1");

		handler.openSession();
		List<CartEntity> list = handler.getList(getUserQuery("merchant"), params);
		handler.closeSession();

		Assert.assertNotNull("Merchant not available", list);

		Merchant user = (Merchant) list.get(0);

		Assert.assertNotNull("Merchant Address not available", user.getAddressList());
		Assert.assertTrue("Merchant Account not available", user.getAccount().getId() > 0);
	}

	@Test
	public void test3_StoreNProductCreation() {

		Merchant user = createMerchant("merchant3");

		List<Address> adrs = new ArrayList<Address>();
		adrs.add(createAddress(AddressType.HOME));
		adrs.add(createAddress(AddressType.OFFICE));
		user.setAddressList(adrs);
		user.setAccount(createAccount());
		
		List<Store> stores = new ArrayList<Store>(); 

		Store store = createStore(user);

		List<Product> products = new ArrayList<Product>();
		
		handler.openSession();

		ProductCategory pc1 = getProductCategory(handler, "Electronics");
		products.add(createProduct(store, pc1, "P1"));
		products.add(createProduct(store, pc1, "P2"));

		ProductCategory pc2 = getProductCategory(handler, "Furnishings");
		products.add(createProduct(store, pc2, "P3"));

		store.setProducts(products);
		store.setMerchant(user);
		stores.add(store);
		
		user.setStores(stores);
		user = (Merchant) handler.save(user);
		handler.closeSession();
		
		Assert.assertNotNull("Merchant not created", user);
		Assert.assertTrue(user.getId() > 0);
		System.out.println(store.getId() + "-" + store.getId() + store.getStoreName());

		

	}

	@Test
	public void test4_GetMerchantDeleted() {

		Merchant user = createMerchant("merchant4");
		handler.openSession();
		user = (Merchant) handler.save(user);
		handler.closeSession();
		Assert.assertTrue(user.getId() > 0);
		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		
		handler.openSession();
		Merchant admin = (Merchant) handler.get(Merchant.class, user.getId());
		Assert.assertTrue(admin.getUsername().equals("merchant4"));
		handler.delete(admin);
		handler.closeSession();

		handler.openSession();
		admin = (Merchant) handler.get(Merchant.class, user.getId());
		handler.closeSession();

		Assert.assertNull(admin);
	}

	@Test
	public void test5_GetAllMerchants() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> merchants = handler.getList("select m from merchant m", params);
		handler.closeSession();

		Assert.assertTrue(merchants.size() > 0);
	}

	@Test
	public void test0_GetAllMerchantsDeleted() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> users = handler.getList("select m from merchant m", params);
		if (users != null && users.size() > 0) {
			handler.delete(users);
		}
		handler.closeSession();
		
		handler.openSession();
		params = new HashMap<String, String>();
		params.put("CQ", "");
		users = handler.getList("select m from merchant m", params);
		Assert.assertTrue(users.size() == 0);
		handler.closeSession();

	}
}
