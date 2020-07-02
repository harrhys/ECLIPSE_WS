package com.farbig.practice.entity.test;

import com.farbig.practice.entity.collections.BaseUser;
import com.farbig.practice.persistence.PersistenceHandler;
import com.farbig.practice.persistence.PersistenceHandlerFactory;
import com.farbig.practice.persistence.TxnMgmtType;




public class TestPersistenceHandler {

	public static void main(String[] args) {
	/*	
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);
		*/
		
		PersistenceHandler util = PersistenceHandlerFactory.getPersistenceHandler(TxnMgmtType.HIBERNATE_JDBC);
		
		BaseUser admin = new BaseUser();
		
		admin.setUserName("BalajiAdmin");
		
		util.openSession();
		
		util.save(admin);
		
		util.closeConnection();

	}

}
