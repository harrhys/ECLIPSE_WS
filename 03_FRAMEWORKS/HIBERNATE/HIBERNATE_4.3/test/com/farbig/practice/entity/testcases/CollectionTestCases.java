package com.farbig.practice.entity.testcases;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.farbig.practice.entity.collections.BaseUser;
import com.farbig.practice.entity.collections.BusinessUser;
import com.farbig.practice.entity.collections.NormalUser;
import com.farbig.practice.entity.embeddable.Address;
import com.farbig.practice.entity.test.util.EntityUtil;
import com.farbig.practice.persistence.PersistenceHandler;
import com.farbig.practice.persistence.PersistenceHandlerFactory;
import com.farbig.practice.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CollectionTestCases extends EntityUtil {

	

	@Test
	public void test1_BusinessUserCreation() {

		BusinessUser user = getBusinessUser();
		
		handler.openSession();
		handler.save(user);
		handler.closeSession();
		
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getId() > 0);
		
		handler.openSession();
		user = (BusinessUser) handler.get(BusinessUser.class, user.getId());
		user.setUserName("UpdatedBaseUser");
		setUpdatedEntityInfo(user);
		handler.update(user);
		handler.closeSession();
		
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getId() > 0);

		handler.openSession();
		user = (BusinessUser) handler.get(BusinessUser.class, user.getId());
		handler.closeSession();
		
		Assert.assertNotNull(user);
		Assert.assertEquals("UpdatedBaseUser", user.getUserName());
	}

	@Test
	public void test2_NormalUserCreation() {

		NormalUser user = getNormalUser();
		
		handler.openSession();
		handler.save(user);
		handler.closeSession();
		
		Assert.assertTrue(user.getId() > 0);
		
		handler.openSession();
		user = (NormalUser) handler.get(NormalUser.class, user.getId());
		user.setUserName("UpdatedSpecialUser");
		setUpdatedEntityInfo(user);
		handler.update(user);
		handler.closeSession();
		
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getId() > 0);

		handler.openSession();
		user = (NormalUser) handler.get(NormalUser.class, user.getId());
		handler.closeSession();
		
		Assert.assertNotNull(user);
		Assert.assertEquals("UpdatedSpecialUser", user.getUserName());
	}

	public void test3_CriteriaBuilder()
	{
		
	}
	
	public BusinessUser getBusinessUser() {

		BusinessUser user = new BusinessUser(getBaseUser());
		user.setUserName("BaseUser");
		user.setBusinessName("Farbig Traders");

		Address addr1 = new Address();
		addr1.setCity("Delhi");
		addr1.setStreet("Silver Spring Rd");
		addr1.setState("Delhi");
		addr1.setPincode("660037");

		List<Address> addresses = (List<Address>) user.getAddressList();
		addresses.add(addr1);

		user.setAddressList(addresses);
		


		return user;
	}

	public NormalUser getNormalUser() {

		NormalUser user = new NormalUser(getBaseUser());
		user.setUserName("NormalUser");
		user.setName("Harrhy");

		Address addr1 = new Address();
		addr1.setCity("Hyderabad");
		addr1.setStreet("Silver Spring Rd");
		addr1.setState("Telangana");
		addr1.setPincode("660037");

		List<Address> addresses = (List<Address>) user.getAddressList();
		addresses.add(addr1);

		user.setAddressList(addresses);
		

		return user;

	}
	
	public BaseUser getBaseUser() {

		BaseUser user = new BaseUser();
		setEntityInfo(user);

		user.setUserName("BaseUser");

		Address addr1 = new Address();
		addr1.setCity("Chennai");
		addr1.setStreet("Silver Spring Rd");
		addr1.setState("TamilNadu");
		addr1.setPincode("660037");

		Address addr2 = new Address();
		addr2.setCity("mumbai");
		addr2.setStreet("Victoria Meadows Rd");
		addr2.setState("Maharastra");
		addr2.setPincode("260037");

		Address addr3 = new Address();
		addr3.setCity("Bangalore");
		addr3.setStreet("Victoria Meadows Rd");
		addr3.setState("Karnataka");
		addr3.setPincode("560037");

		List<Address> addresses = new ArrayList<Address>();
		addresses.add(addr1);
		addresses.add(addr2);

		user.setAddressList(addresses);
		

		return user;

	}
	
	
	static PersistenceHandler handler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("Starting Collection Test cases");
		handler = PersistenceHandlerFactory.getPersistenceHandler(TxnMgmtType.HIBERNATE_JDBC);
		//handler.openConnection();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//handler.closeConnection();
		System.out.println("Completed Collection Test cases");
	}

	@Before
	public void setUp() throws Exception {

		System.out.println("Starting the Testcase");
	}

	@After
	public void tearDown() throws Exception {

		System.out.println("Completed the Testcase");
	}

}