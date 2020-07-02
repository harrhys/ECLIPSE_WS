package com.farbig.practice.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.farbig.practice.persistence.hibernate.HibernateCMTHandler;
import com.farbig.practice.persistence.hibernate.HibernateJDBCHandler;
import com.farbig.practice.persistence.hibernate.HibernateJTAHandler;

public class PersistenceHandlerFactory {

	private static Map<TxnMgmtType, PersistenceHandler> handlerMap = Collections
			.synchronizedMap(new HashMap<TxnMgmtType, PersistenceHandler>());

	public static PersistenceHandler getPersistenceHandler(TxnMgmtType txnMgmtType) {

		PersistenceHandler handler = handlerMap.get(txnMgmtType);

		if (handler == null) {

			System.out.println("Creating Persistence Handler " + txnMgmtType);

			switch (txnMgmtType) {

			case HIBERNATE_JDBC:
				handler = new HibernateJDBCHandler();
				break;
			case HIBERNATE_JTA:
				handler = new HibernateJTAHandler();
				break;
			case HIBERNATE_CMT:
				handler = new HibernateCMTHandler();
				break;
			default:
				handler = new HibernateJDBCHandler();
				break;
			}

			handlerMap.put(txnMgmtType, handler);
		} else {
			System.out.println("Retrieving Persistence Handler " + txnMgmtType);

		}

		return handler;
	}

	public static PersistenceHandler getPersistenceHandler() {
		PersistenceHandler persistenceHandler = new HibernateJDBCHandler();

		return persistenceHandler;
	}

}
