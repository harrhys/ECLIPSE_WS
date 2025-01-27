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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.TxnMgmtType;

public class JPAHandler implements PersistenceHandler {

	private static Map<TxnMgmtType, EntityManagerFactory> factories = new HashMap<TxnMgmtType, EntityManagerFactory>();

	private EntityManagerFactory emf;

	private EntityManager em;

	UserTransaction txn;

	boolean isUserTxnAvailable = false;

	boolean isJTA = false;

	private TxnMgmtType persistenceUnit = null;

	private boolean isUserTxnCreated = false;

	public JPAHandler(TxnMgmtType txnMgmtType) {

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);
		
		persistenceUnit = txnMgmtType;
	
	}
	
	public void openConnection() {
		
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

		System.out
				.println("----------------Opening the session JPAHandler for "
						+ persistenceUnit);

		if (!emf.isOpen())
			createEntityManagerFactory();

		if (em == null || !em.isOpen())
			em = emf.createEntityManager();

		start();
	}

	public void closeSession() {

		if (em != null && em.isOpen())
			em.close();
		System.out
				.println("----------------Closing the session JPAHandler for "
						+ persistenceUnit);
		System.out.println();
	}

	private void start() {

		checkJTA();

		if (isJTA) {

			if (getUserTxnStatus() != Status.STATUS_ACTIVE) {

				try {
					txn.begin();

					isUserTxnCreated = true;
					
					System.out
					.println("------------------JTA Transaction started------------------");
				} catch (NotSupportedException | SystemException e) {
					e.printStackTrace();
				}

				

			}
			em.joinTransaction();

		} else if (!isJTA) {

			em.getTransaction().begin();
			System.out
					.println("------------------Non JTA Transaction started------------------");
		}
		System.out.println("Is joined to Txn-" + em.isJoinedToTransaction());

	}
	
	private int getUserTxnStatus() {
		int status = -1;
		try {
			txn = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");
			status = txn.getStatus();
		} catch (SystemException | NamingException e) {
			e.printStackTrace();
		}
		System.out.println("TRANSACTION STATUS " + status);

		return status;
	}

	private void checkJTA() {
		try {
			em.getTransaction();
		} catch (Exception e) {
			isJTA = true;
		}
	}

	private void commit() {

		if (isJTA && isUserTxnCreated) {

			try {
				txn.commit();
				System.out
						.println("------------------JTA Transaction Committed------------------");
			} catch (SecurityException | IllegalStateException
					| RollbackException | HeuristicMixedException
					| HeuristicRollbackException | SystemException e) {
				e.printStackTrace();
			}
		} else if (!isJTA && getUserTxnStatus() != Status.STATUS_ACTIVE) {
			em.getTransaction().commit();
			System.out
					.println("------------------Non JTA Transaction Committed------------------");
		}

	}

	public Object save(Object obj) {
		
		//openSession();
		em.persist(obj);
		em.flush();
		commit();
		closeSession();
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
		if(parameters!=null)
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
