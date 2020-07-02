package com.farbig.cart.persistence;

import com.farbig.cart.persistence.hibernate.HibernateCMTHandler;
import com.farbig.cart.persistence.hibernate.HibernateJDBCHandler;
import com.farbig.cart.persistence.hibernate.HibernateJTAHandler;
import com.farbig.cart.persistence.jpa.JPAJDBCHandler;
import com.farbig.cart.persistence.jpa.JPAJTADSHandler;
import com.farbig.cart.persistence.jpa.JPANonJTADSHandler;

public class PersistenceHandlerFactory {

	public static PersistenceHandler getDataHandler(TxnMgmtType handler) {

		PersistenceHandler dataHandler = null;

		switch (handler) {

		case HIBERNATE_JDBC:
			dataHandler = new HibernateJDBCHandler();
			break;
		case HIBERNATE_JTA:
			dataHandler = new HibernateJTAHandler();
			break;
		case HIBERNATE_CMT:
			dataHandler = new HibernateCMTHandler();
			break;
		case JPA_JDBC:
			dataHandler = new JPAJDBCHandler();
			break;
		case JPA_NON_JTA_DS:
			dataHandler = new JPANonJTADSHandler(); // pass non JTA datasource name as parameter to access other datasources
			break;
		case JPA_JTA_DS:
			dataHandler = new JPAJTADSHandler();
			break;

		default:
			dataHandler = new HibernateJDBCHandler();
			break;
		}

		return dataHandler;
	}

}
