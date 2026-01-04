package com.farbig.collections;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.farbig.util.DataHandler;
import com.farbig.util.DataHandlerFactory;

public class CollectionTest {

	public static void main(String[] args) {
		
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		
		DataHandler dataHandler = DataHandlerFactory.getDataHandler();
		
		testBaseUser(dataHandler);
		
		testSpecialUser(dataHandler);
		
		dataHandler.closeConnection();
		
		Hello h = new Hello(){
			
		};

	}

	public static void testBaseUser(DataHandler dataHandler) {

		System.out.println("----------------------------TESTING BASE USER-----------------------");
		
		BaseUser user = null;
				
		user = getNewUser();

		user = (BaseUser) dataHandler.save(user);

		dataHandler.commit();
		
		dataHandler.start();
		
		int userid = user.getUserId();

		System.out.println("userid--"+userid);

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
	
	public static void testSpecialUser(DataHandler dataHandler) {
		
		System.out.println("----------------------------TESTING SPECIAL USER-----------------------");
		
		dataHandler.openSession();

		SpecialUser user = new SpecialUser();
				
		user = getSpecialUser();

		user = (SpecialUser) dataHandler.save(user);

		dataHandler.commit();
		
		dataHandler.start();
		
		int userid = user.getUserId();

		System.out.println(userid);

		long t1, t2;

		t1 = System.currentTimeMillis();

		user = (SpecialUser) dataHandler.get(SpecialUser.class, userid);
		
		t2 = System.currentTimeMillis();

		System.out.println("Retrival Time: " + (t2 - t1));
		
		System.out.println(user.getUserName());

		user.setUserName("My cached Special User");
		
		

		user = (SpecialUser) dataHandler.update(user);

		dataHandler.commit();

		dataHandler.closeSession();

		dataHandler.openSession();

		long t3 = System.currentTimeMillis();

		BaseUser otherUser = (BaseUser) dataHandler.get(SpecialUser.class, userid);

		long t4 = System.currentTimeMillis();

		System.out.println("Retrival Time: " + (t4 - t3));

		System.out.println(otherUser.getUserName());

		dataHandler.closeSession();

		dataHandler.closeConnection();

		dataHandler.openSession();

		t3 = System.currentTimeMillis();

		otherUser = (SpecialUser) dataHandler.get(SpecialUser.class, userid);

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
	
	public static SpecialUser getSpecialUser() {
		
		SpecialUser user = new SpecialUser();
		
		
		user.setUserName("Special User");
		
		user.setSpeciality("test special");
		

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