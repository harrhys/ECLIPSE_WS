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
import com.farbig.cart.entity.Customer;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.PersistenceHandlerFactory;
import com.farbig.cart.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerTestCases extends EntityUtil {

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
	public void test1_CustomerCreation() {

		Customer user = createCustomer("customer1");

		List<Address> adrs = new ArrayList<Address>();
		adrs.add(createAddress(AddressType.BILLING));
		adrs.add(createAddress(AddressType.SHIPPING));
		user.setAddressList(adrs);
		user.setAccount(createAccount());

		handler.openSession();
		user = (Customer) handler.save(user);
		handler.closeSession();

		Assert.assertNotNull("Customer is not created", user);
		Assert.assertTrue("Customer is not created", user.getId() > 0);
		Assert.assertNotNull("Customer address is not created", user.getAddressList());
		Assert.assertTrue("Customer account is not created", user.getAccount().getId() > 0);

		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	@Test
	public void test2_GetCustomer() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		params.put("username", "customer1");

		handler.openSession();
		List<CartEntity> list = handler.getList(getUserQuery("customer"), params);
		handler.closeSession();

		if (list != null && list.size() > 0) {

			Customer user = (Customer) list.get(0);

			Assert.assertNotNull(user.getAddressList());
			Assert.assertTrue(user.getAccount().getId() > 0);

		} else {
			Assert.fail("Customer not available");
		}
	}

	@Test
	public void test5_GetAllCustomers() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> merchants = handler.getList("select m from customer m", params);
		handler.closeSession();

		Assert.assertTrue(merchants.size() > 0);
	}

	@Test
	public void test0_GetAllCustomersDeleted() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> users = handler.getList("select m from customer m", params);
		if (users != null && users.size() > 0) {
			handler.delete(users);
		}
		handler.closeSession();

		handler.openSession();
		params = new HashMap<String, String>();
		params.put("CQ", "");
		users = handler.getList("select m from customer m", params);
		Assert.assertTrue(users.size() == 0);
		handler.closeSession();

	}
	
	@Test
	public void test6_OrderItems() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> merchants = handler.getList("select m from product m", params);
		handler.closeSession();

		Assert.assertTrue(merchants.size() > 0);
	}
}
