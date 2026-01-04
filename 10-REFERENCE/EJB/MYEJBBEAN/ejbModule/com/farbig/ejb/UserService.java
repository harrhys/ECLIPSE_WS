package com.farbig.ejb;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.farbig.collections.Address;
import com.farbig.collections.BaseUser;
import com.farbig.util.DataHandler;
import com.farbig.util.DataHandlerFactory;

public class UserService {

	DataHandler dataHandler = DataHandlerFactory.getDataHandler("JPA");

	public BaseUser createBaseUser() {

		System.out
				.println("----------------------------CREATING BASE USER-----------------------");

		BaseUser user = null;

		user = getNewUser();

		dataHandler.openSession();

		user = (BaseUser) dataHandler.save(user);

		dataHandler.closeSession();

		return user;

	}

	public BaseUser updateBaseUser(BaseUser user) {

		dataHandler.openSession();

		dataHandler.start();

		int userid = user.getUserId();

		System.out.println("userid--" + userid);

		long t1, t2;

		t1 = System.currentTimeMillis();

		user = (BaseUser) dataHandler.get(BaseUser.class, userid);

		t2 = System.currentTimeMillis();

		System.out.println("Retrival Time: " + (t2 - t1));

		System.out.println(user.getUserName());

		user.setUserName("Updated Base User");

		user = (BaseUser) dataHandler.update(user);

		dataHandler.closeSession();

		return user;
	}

	public BaseUser getBaseUser(int userId) {

		dataHandler.openSession();

		long t3 = System.currentTimeMillis();

		BaseUser user = (BaseUser) dataHandler.get(BaseUser.class, userId);

		long t4 = System.currentTimeMillis();

		System.out.println("Retrival Time: " + (t4 - t3));

		System.out.println(user.getUserName());

		dataHandler.closeSession();

		return user;
	}
	
	public void deleteBaseUser(int userId) {

		dataHandler.openSession();

		BaseUser user = (BaseUser) dataHandler.get(BaseUser.class, userId);
		
		dataHandler.delete(user);

		dataHandler.closeSession();

	}

	public BaseUser getNewUser() {

		BaseUser user = new BaseUser();

		user.setUserName("Base User");

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

		//user.setAddressList(addresses);

		user.setAddress(addr3);

		user.setCreateDt(new Date(System.currentTimeMillis()));

		return user;

	}
}
