package com.farbig.ejb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.farbig.collections.Address;
import com.farbig.collections.BaseUser;
import com.farbig.util.DataHandler;
import com.farbig.util.DataHandlerFactory;

/**
 * Session Bean implementation class MyEJB
 */
@Stateless(mappedName = "myfirstejb")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MyEJB implements MyEJBRemote {

	/**
	 * Default constructor.
	 */
	public MyEJB() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	private void test1()
	{
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}
	
	@PreDestroy
	private void test2()
	{
		System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
	}


	public String sayHello(String s) throws Exception{
		
		System.out.println("INSIDE MY EJB");

		DataHandler dataHandler = DataHandlerFactory.getDataHandler();

		testBaseUser(dataHandler);
		

		return "Hello.." + s;
	}

	public static void testBaseUser(DataHandler dataHandler) {

		System.out
				.println("----------------------------TESTING BASE USER-----------------------");

		BaseUser user = null;

		user = getNewUser();

		user = (BaseUser) dataHandler.save(user);

		dataHandler.commit();
		
		dataHandler.openSession();

		//dataHandler.start();

		int userid = user.getUserId();

		System.out.println("userid--" + userid);

		long t1, t2;

		t1 = System.currentTimeMillis();

		user = (BaseUser) dataHandler.get(BaseUser.class, userid);

		t2 = System.currentTimeMillis();

		System.out.println("Retrival Time: " + (t2 - t1));

		System.out.println(user.getUserName());

		user.setUserName("My cache User");

		user = (BaseUser) dataHandler.update(user);

		dataHandler.commit();

		dataHandler.closeSession();

		dataHandler.openSession();

		long t3 = System.currentTimeMillis();

		BaseUser otherUser = (BaseUser) dataHandler.get(BaseUser.class, userid);

		long t4 = System.currentTimeMillis();

		System.out.println("Retrival Time: " + (t4 - t3));

		System.out.println(otherUser.getUserName());

		dataHandler.closeSession();

		dataHandler.closeConnection();

		dataHandler.openSession();

		t3 = System.currentTimeMillis();

		otherUser = (BaseUser) dataHandler.get(BaseUser.class, userid);

		t4 = System.currentTimeMillis();

		System.out.println("Retrival Time: " + (t4 - t3));

		System.out.println(otherUser.getUserName());

		dataHandler.closeSession();

	}

	public static BaseUser getNewUser() {

		BaseUser user = new BaseUser();

		user.setUserName("Other User");

		Address addr1 = new Address();

		addr1.setCity("Chennai");
		addr1.setStreet("Silver Spring Rd");
		addr1.setState("Karnataka");
		addr1.setPincode("660037");

		Address addr2 = new Address();

		addr2.setCity("mumbai");
		addr2.setStreet("Victoria Meadows Rd");
		addr2.setState("Karnataka");
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

		user.setAddress(addr3);

		user.setCreateDt(new Date(System.currentTimeMillis()));

		return user;

	}

}
