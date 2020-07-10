package com.farbig.cart.persistence.jpa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.TxnMgmtType;

public class JPAJDBCHandler implements PersistenceHandler {

	private static Map<TxnMgmtType, EntityManagerFactory> factories = new HashMap<TxnMgmtType, EntityManagerFactory>();

	private EntityManagerFactory emf;

	private EntityManager em;

	private TxnMgmtType persistenceUnit = TxnMgmtType.JPA_JDBC;

	public JPAJDBCHandler() {

		System.out.println("Starting JPAJDBCHandler");

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);

		createEntityManagerFactory();
	}

	public void openConnection() {

		System.out.println("----------------Opening the JPAJDBCHandler Connection for " + persistenceUnit);

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

		System.out.println("----------------Closing the JPAJDBCHandler connection for " + persistenceUnit);

		emf.close();
	}

	public void openSession() {

		System.out.println("----------------Opening the JPAJDBCHandler session for " + persistenceUnit);

		if (!emf.isOpen())
			createEntityManagerFactory();

		if (em == null || !em.isOpen())
			em = emf.createEntityManager();

		start();
	}

	public void closeSession() {

		if (em != null && em.isOpen())
			em.close();
		System.out.println("----------------Closing the JPAJDBCHandler session for " + persistenceUnit);
		System.out.println();
	}

	private void start() {

		em.getTransaction().begin();
		System.out.println("------------------JPAJDBCHandler Non JTA Transaction started------------------");
		System.out.println("Is joined to Txn-" + em.isJoinedToTransaction());

	}

	private void commit() {

		em.getTransaction().commit();
		System.out.println("------------------JPAJDBCHandler Non JTA Transaction Committed------------------");

	}

	private void rollback() {

		em.getTransaction().rollback();
		System.out.println("------------------JPAJDBCHandler Non JTA Transaction Rolledback------------------");

	}

	public Object save(Object obj) {
		
		em.persist(obj);
		try {
			em.flush();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			obj=null;
		}
		return obj;
	}

	public void save(List<Object> objs) {

	}

	public void load(Object obj, Serializable id) {
		obj = em.find(obj.getClass(), id);
	}

	public Object get(Class obj, Serializable id) {
		Object ob = em.find(obj, id);
		return ob;
	}

	public List<CartEntity> getList(String namedQuery, Map parameters) {

		List<CartEntity> entities = null;
		Query query;

		if (parameters.containsKey("CQ")) {

			parameters.remove("CQ");
			query = em.createQuery(namedQuery);

		} else {

			query = em.createNamedQuery(namedQuery);

		}

		if (parameters != null) {
			for (Object key : parameters.keySet()) {

				query.setParameter(key.toString(), parameters.get(key));
			}
		}
		entities = query.getResultList();

		return entities;
	}

	public Object update(Object obj) {

		closeSession();
		return null;
	}

	public void delete(Object obj) {

		if (obj instanceof List) {
			List<CartEntity> entities = (List) obj;
			for (Iterator iterator = entities.iterator(); iterator.hasNext();) {
				CartEntity entity = (CartEntity) iterator.next();
				em.remove(entity);
			}
		} else {

			em.remove(obj);
		}
		commit();

	}

	public Object merge(Object obj) {

		closeSession();
		return null;
	}

}
