package com.farbig.cart.persistence.hibernate;

import static javax.transaction.Status.STATUS_ACTIVE;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.TxnMgmtType;

public class HibernateAllHandlers implements PersistenceHandler {

	private SessionFactory sf = null;

	private static Map<TxnMgmtType, String> configfiles = new HashMap<TxnMgmtType, String>();

	private static Map<TxnMgmtType, SessionFactory> factories = new HashMap<TxnMgmtType, SessionFactory>();

	static {

		configfiles.put(TxnMgmtType.HIBERNATE_JDBC, "hibernate-jdbc.cfg.xml");
		configfiles.put(TxnMgmtType.HIBERNATE_CMT, "hibernate-cmt.cfg.xml");
		configfiles.put(TxnMgmtType.HIBERNATE_JTA, "hibernate-jta.cfg.xml");
	}

	Session session;

	UserTransaction txn;

	boolean isUserTxnAvailable = false;

	private TxnMgmtType txnMgmtType = null;

	public HibernateAllHandlers(TxnMgmtType type) {

		System.out.println("");
		System.out.println("Starting HibernateHandler");
		txnMgmtType = type;
		createSessionFactory();
		checkForUserTxn();
	}

	@SuppressWarnings("deprecation")
	private void createSessionFactory() {

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getGlobal().setLevel(Level.OFF);

		sf = factories.get(txnMgmtType);
		if (sf == null || sf.isClosed()) {
			sf = new Configuration().configure(configfiles.get(txnMgmtType))
					.setInterceptor(new CartInterceptor())
					.buildSessionFactory();
			System.out.println("---------------------------" + txnMgmtType
					+ " Session Factory Created------------------");
			factories.put(txnMgmtType, sf);
		} else {
			System.out.println("---------------------------" + txnMgmtType
					+ " Session Factory Retrieved------------------");
		}
	}
	
	private void checkForUserTxn() {
		
		try {
			txn = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");

			if (txn != null && txn.getStatus() != Status.STATUS_ACTIVE) {
				isUserTxnAvailable = true;
			}
		} catch (NamingException | SystemException e) {
			e.printStackTrace();
		}
	}

	public SessionFactory getSessionFactory() {

		return sf;
	}
	
	public void openSession() {

		if (sf == null || sf.isClosed()) {

			createSessionFactory();
		}
		if (isUserTxnAvailable) {
			
			start();
			session = sf.getCurrentSession();
			System.out
					.println("-----------JTA Session Retrieved !!----------------");
		} else {
			
			session = sf.openSession();
			System.out.println("-----------Session Created !!----------------");
			start();
		}
	}

	public void closeConnection() {

		if (session != null && session.isConnected()) {
			session.close();
			System.out
					.println("--------------------------------Session Closed!!--------------------");
			System.out
					.println("--------------------------------------------------------------------");
		}
		sf.close();
		System.out
				.println("---------------------------Session Factory Closed!!-----------------");
		System.out
				.println("--------------------------------------------------------------------");
		System.out
				.println("--------------------------------------------------------------------");
	}

	

	public void closeSession() {

		if (session != null && session.isConnected())
			session.close();
		System.out
				.println("--------------------------------Session Closed!!--------------------");
		System.out
				.println("--------------------------------------------------------------------");
	}

	private void start() {

		if (isUserTxnAvailable && getUserTxnStatus() != Status.STATUS_ACTIVE) {

			try {
				txn.begin();
				System.out
						.println("--------------------------------JTA Transaction Started!!--------------------");
			} catch (NotSupportedException | SystemException e) {
				e.printStackTrace();
			}
		} else {

			session.getTransaction().begin();
			System.out
					.println("--------------------------------Transaction Started!!--------------------");
		}
	}

	public void load(Object obj, Serializable id) {

		try {
			session.load(obj, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object get(Class<?> obj, Serializable id) {

		try {
			if (session == null || !session.isConnected())
				session = sf.openSession();
			Object ob = session.get(obj, id);
			return ob;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object save(Object obj) {

		try {
			
			session.save(obj);
			session.flush();
			commit();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public void commit() {

		if (isUserTxnAvailable && getUserTxnStatus() == STATUS_ACTIVE) {

			try {
				txn.commit();
				System.out
						.println("------------------------JTA Transaction Commited--------------------");

			} catch (SecurityException | IllegalStateException
					| RollbackException | HeuristicMixedException
					| HeuristicRollbackException | SystemException e) {
				e.printStackTrace();
			}

		} else {
			if (session.getTransaction().isActive()) {

				session.getTransaction().commit();
				System.out
						.println("------------------------Transaction Commited--------------------");
			} else {
				System.out
						.println("------------------------No Active Transaction to Commit--------------------");
			}
		}
	}

	public Object update(Object obj) {

		try {
			session.save(obj);
			commit();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public void delete(Object obj) {

		try {
			session.delete(obj);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object merge(Object obj) {
		
		try {
			obj = session.merge(obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public void save(List<Object> objs) {
		
		try {
			for (Iterator<Object> iterator = objs.iterator(); iterator
					.hasNext();) {
				Object obj = iterator.next();
				session.persist(obj);
			}
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Criteria getCriteria(Class persistentClass) {
		
		return session.createCriteria(persistentClass);
	}

	public List<Object> getList(Criteria crit) {

		List objects = null;
		try {
			objects = crit.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}

	

	private int getUserTxnStatus() {

		int status = -1;
		try {
			status = txn.getStatus();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		System.out.println("TRANSACTION STATUS " + status);

		return status;
	}

	@Override
	public List<CartEntity> getList(String namedQuery, Map parameters) {
		// TODO Auto-generated method stub
		return null;
	}
}
