package com.farbig.cart.persistence.jpa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.TxnMgmtType;

public class JPANonJTADSHandler implements PersistenceHandler {

	private static Map<TxnMgmtType, EntityManagerFactory> factories = new HashMap<TxnMgmtType, EntityManagerFactory>();

	private EntityManagerFactory emf;

	private EntityManager em;

	private TxnMgmtType persistenceUnit = TxnMgmtType.JPA_NON_JTA_DS;

	private String nonJTADSName = "CARTNONXADS";
	
	public JPANonJTADSHandler() {
		
		System.out.println("Starting JPANonJTADSHandler");

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);

		createEntityManagerFactory();
	}
	
	public void openConnection() {
		
		createEntityManagerFactory();
		
	}

	public JPANonJTADSHandler(String dataSourceName) {
		
		System.out.println("Starting JPANonJTADSHandler");

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);

		if (dataSourceName != null && !dataSourceName.equals(""))
			nonJTADSName = dataSourceName;
		
		createEntityManagerFactory();
	}
	

	private void createEntityManagerFactory() {

		emf = factories.get(persistenceUnit);

		if (emf == null || !emf.isOpen()) {
			
			Map<String, Object> config = new HashMap<String, Object>();
			
			try {
				
				Properties p = new Properties();
				p.put(Context.PROVIDER_URL, "jnp://localhost:8080");
				p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
				
				Context context = new InitialContext();
				
				config.put("javax.persistence.nonJtaDataSource", context.lookup(nonJTADSName));

			} catch (NamingException e) {
				e.printStackTrace();
			}

			emf = Persistence.createEntityManagerFactory(persistenceUnit.name(), config);

			factories.put(persistenceUnit, emf);
		}
	}

	public void closeConnection() {

		emf.close();
	}

	public void openSession() {

		System.out.println("----------------Opening the JPANonJTADSHandler session for " + persistenceUnit);

		if (!emf.isOpen())
			createEntityManagerFactory();

		if (em == null || !em.isOpen())
			em = emf.createEntityManager();

		start();
	}

	public void closeSession() {

		if (em != null && em.isOpen())
			em.close();
		System.out.println("----------------Closing the JPANonJTADSHandler session for " + persistenceUnit);
		System.out.println();
	}

	private void start() {

		em.getTransaction().begin();
		System.out.println("------------------JPANonJTADSHandler Non JTA Transaction started------------------");
		System.out.println("Is joined to Txn-" + em.isJoinedToTransaction());

	}

	private void commit() {
		if (!em.isJoinedToTransaction()) {
			em.getTransaction().commit();
			System.out.println("------------------JPANonJTADSHandler Non JTA Transaction Committed------------------");
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
		obj = em.find(obj.getClass(), id);
	}

	public Object get(Class obj, Serializable id) {
		Object ob = em.find(obj, id);
		return ob;
	}

	public List<CartEntity> getList(String namedQuery, Map parameters) {

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

		closeSession();
		return null;
	}

	public void delete(Object obj) {

		closeSession();

	}

	public Object merge(Object obj) {

		closeSession();
		return null;
	}

}
