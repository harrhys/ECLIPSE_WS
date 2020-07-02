package com.farbig.cart.persistence.jpa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.TxnMgmtType;

public class JPAJTADSHandler implements PersistenceHandler {

	private static Map<TxnMgmtType, EntityManagerFactory> factories = new HashMap<TxnMgmtType, EntityManagerFactory>();

	private EntityManagerFactory emf;

	private EntityManager em;

	UserTransaction txn;

	boolean isBMT = false;

	private TxnMgmtType persistenceUnit = TxnMgmtType.JPA_JTA_DS;

	public JPAJTADSHandler() {
		
		System.out.println("Starting JPAJTADSHandler");

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);
		createEntityManagerFactory();
	}

	private void createEntityManagerFactory() {

		emf = factories.get(persistenceUnit);
		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory(persistenceUnit.name());
			factories.put(persistenceUnit, emf);
		}
	}

	public void closeConnection() {

		emf.close();
	}

	public void openSession() {

		System.out.println("----------------Opening the JPAJTADSHandler session  for " + persistenceUnit);

		if (!emf.isOpen())
			createEntityManagerFactory();

		if (em == null || !em.isOpen())
			em = emf.createEntityManager();

		start();
	}

	public void closeSession() {

		if (em != null && em.isOpen())
			em.close();
		System.out.println("----------------Closing the JPAJTADSHandler session for " + persistenceUnit);
		System.out.println();
	}

	private void start() {

		try {
			txn = getUserTxn();
			// Begin the Txn only for BMT, CMT doesnt need this txn
			if (txn.getStatus() != Status.STATUS_ACTIVE) {
				txn.begin();
				isBMT = true;
				System.out.println("------------------JPAJTADSHandler JTA Transaction started------------------");
			}

		} catch (NotSupportedException | SystemException e) {
			e.printStackTrace();
		}
		em.joinTransaction();

		System.out.println("Is joined to Txn-" + em.isJoinedToTransaction());

	}

	private UserTransaction getUserTxn() {
		UserTransaction txn = null;
		try {
			txn = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
			System.out.println("JPAJTADSHandler TRANSACTION STATUS " + txn.getStatus());
		} catch (SystemException | NamingException e) {
			e.printStackTrace();
		}
		return txn;
	}

	private void commit() {

		try {
			// Commit the Txn only for BMT, CMT doesn't need commit
			if (isBMT) {
				txn.commit();
				System.out.println("------------------JPAJTADSHandler JTA Transaction Committed------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Object save(Object obj) {

		em.persist(obj);
		em.flush();
		commit();
		return obj;
	}

	public void save(List<Object> objs) {

	}

	public void load(Object obj, Serializable id) {

		openSession();
		obj = em.find(obj.getClass(), id);
		closeSession();
	}

	public Object get(Class obj, Serializable id) {

		openSession();
		Object ob = em.find(obj, id);
		closeSession();
		return ob;
	}

	public List<CartEntity> getList(String namedQuery, Map parameters) {

		openSession();

		Query query = em.createNamedQuery(namedQuery);
		if (parameters != null)
			for (Object key : parameters.keySet()) {

				query.setParameter(key.toString(), parameters.get(key));
			}
		List<CartEntity> users = query.getResultList();
		closeSession();
		return users;
	}

	public Object update(Object obj) {

		openSession();

		closeSession();
		return null;
	}

	public void delete(Object obj) {

		openSession();

		closeSession();

	}

	public Object merge(Object obj) {

		openSession();

		closeSession();
		return null;
	}

}
