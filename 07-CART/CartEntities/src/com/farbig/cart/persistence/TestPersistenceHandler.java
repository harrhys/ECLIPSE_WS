package com.farbig.cart.persistence;

import com.farbig.cart.entity.Admin;




public class TestPersistenceHandler {

	public static void main(String[] args) {
	/*	
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);
		*/
		
		PersistenceHandler util = PersistenceHandlerFactory.getDataHandler(TxnMgmtType.HIBERNATE_JDBC);
		
		Admin admin = new Admin();
		
		admin.setName("BalajiAdmin");
		
		util.openSession();
		
		util.save(admin);
		
		util.closeConnection();

	}

}
