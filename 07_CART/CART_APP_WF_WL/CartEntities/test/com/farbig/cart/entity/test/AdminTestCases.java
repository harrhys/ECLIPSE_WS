package com.farbig.cart.entity.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.PersistenceHandlerFactory;
import com.farbig.cart.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminTestCases extends EntityUtil {

	PersistenceHandler handler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("Starting Admin Test cases");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		System.out.println("Completed Admin Test cases");
	}

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
	public void test0_DeleteAllAdmins() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> users = handler.getList("select a from admin a", params);
		if (users != null && users.size() > 0) {
			handler.delete(users);
		}
		handler.closeSession();

		handler.openSession();
		params = new HashMap<String, String>();
		params.put("CQ", "");
		users = handler.getList("select a from admin a", params);
		Assert.assertTrue(users.size() == 0);
		handler.closeSession();
	}

	@Test
	public void test1_AdminCreation() {

		Admin user = createAdmin("admin1");

		handler.openSession();
		user = (Admin) handler.save(user);
		handler.closeSession();

		Assert.assertNotNull(user);
		Assert.assertTrue(user.getId() > 0);

		System.out.println(user.getId() + "-" + user.getId() + user.getName());
	}

	@Test
	public void test2_GetAdmin() {

		Admin user = createAdmin("admin2");
		handler.openSession();
		user = (Admin) handler.save(user);
		handler.closeSession();

		Assert.assertNotNull(user);
		Assert.assertTrue(user.getId() > 0);

		System.out.println(user.getId() + "-" + user.getId() + user.getName());

		handler.openSession();
		Admin admin = (Admin) handler.get(Admin.class, user.getId());
		handler.closeSession();

		Assert.assertTrue(admin.getAdminType().equals("ROOT"));
	}

	// @Test
	public void test3_DeleteAdmin() {

		Admin user = createAdmin("admin3");

		handler.openSession();
		user = (Admin) handler.save(user);
		handler.closeSession();

		System.out.println(user.getId() + "-" + user.getId() + user.getName());
		Assert.assertTrue(user.getId() > 0);

		handler.openSession();
		Admin admin = (Admin) handler.get(Admin.class, user.getId());
		Assert.assertTrue(admin.getAdminType().equals("ROOT"));
		handler.delete(admin);
		handler.closeSession();

		handler.openSession();
		admin = (Admin) handler.get(Admin.class, user.getId());
		handler.closeSession();

		Assert.assertNull(admin);
	}

	@Test
	public void test4_GetAllAdmins() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> admins = handler.getList("select a from admin a", params);
		handler.closeSession();

		Assert.assertTrue(admins.size() > 0);
	}

	@Test
	public void test5_DeleteAllProductCategories() {

		handler.openSession();
		Map<String, String> params = new HashMap<String, String>();
		params.put("CQ", "");
		List<?> pcs = handler.getList("select a from productcategory a", params);

		if (pcs != null & pcs.size() > 0) {

			Assert.assertTrue(pcs.size() > 0);

			handler.delete(pcs);
			handler.closeSession();

			handler.openSession();
			params = new HashMap<String, String>();
			params.put("CQ", "");
			pcs = handler.getList("select a from productcategory a", params);
			Assert.assertTrue(pcs.size() == 0);
			handler.closeSession();

		}

	}

	@Test
	public void test6_CreateProductCategories() {

		ProductCategory pc1 = new ProductCategory();
		pc1.setName("Electronics");
		pc1.setDescription("All Electronic Items");
		pc1.setStatus("ACTIVE");

		handler.openSession();
		pc1 = (ProductCategory) handler.save(pc1);
		handler.closeSession();
		System.out.println(pc1.getId() + "-" + pc1.getId() + pc1.getName());

		Assert.assertTrue(pc1.getId() > 0);

		ProductCategory pc2 = new ProductCategory();
		pc2.setName("Home Appliances");
		pc2.setDescription("All Home Appliance Items");
		pc2.setStatus("ACTIVE");

		handler.openSession();
		pc2 = (ProductCategory) handler.save(pc2);
		handler.closeSession();
		System.out.println(pc2.getId() + "-" + pc2.getId() + pc2.getName());

		Assert.assertTrue(pc2.getId() > 0);

		ProductCategory pc3 = new ProductCategory();
		pc3.setName("Furnishings");
		pc3.setDescription("All Furnishing Items");
		pc3.setStatus("ACTIVE");

		handler.openSession();
		pc3 = (ProductCategory) handler.save(pc3);
		handler.closeSession();
		System.out.println(pc3.getId() + "-" + pc3.getId() + pc3.getName());

		Assert.assertTrue(pc3.getId() > 0);

	}

}
