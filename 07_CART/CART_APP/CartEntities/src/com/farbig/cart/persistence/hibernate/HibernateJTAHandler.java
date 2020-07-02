package com.farbig.cart.persistence.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.farbig.cart.entity.CartEntity;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.TxnMgmtType;

public class HibernateJTAHandler implements PersistenceHandler {

	private SessionFactory sf = null;

	Session session;

	UserTransaction txn;

	private TxnMgmtType txnMgmtType = TxnMgmtType.HIBERNATE_JTA;

	public HibernateJTAHandler() {

		System.out.println("");
		System.out.println("Starting HibernateJTAHandler");
		createSessionFactory();
	}

	@SuppressWarnings("deprecation")
	private void createSessionFactory() {

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getGlobal().setLevel(Level.OFF);

		if (sf == null || sf.isClosed()) {
			sf = new Configuration().configure("hibernate-jta.cfg.xml")
					.setInterceptor(new CartInterceptor())
					.buildSessionFactory();
			System.out.println("---------------------------Hibernate JTA Session Factory Created------------------");
		} else {
			System.out.println("---------------------------Hibernate JTA Session Factory Retrieved------------------");
		}
	}

	public void openSession() {

		try {
			txn = (UserTransaction) new InitialContext()
					.lookup("java:comp/UserTransaction");
			
			txn.begin();

			System.out
					.println("--------------------------------Hibernate JTA Transaction Started!!--------------------");
			session = getSessionFactory().getCurrentSession();
			System.out
					.println("-----------Hibernate JTA Session Retrieved !!----------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void commit() {

		try {
			txn.commit();
			System.out
					.println("------------------------Hibernate JTA Transaction Commited--------------------");

		} catch (SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException
				| SystemException e) {
			e.printStackTrace();
		}

	}

	public SessionFactory getSessionFactory() {
		if (sf == null || sf.isClosed()) {
			createSessionFactory();
		}
		return sf;
	}

	public void closeSession() {

		if (session != null && session.isOpen())
			session.close();
		System.out
				.println("--------------------------------Hibernate JTA Session Closed!!--------------------");
		System.out
				.println("--------------------------------------------------------------------");
	}

	public void closeConnection() {

		if (session != null && session.isConnected()) {
			session.close();
			System.out
					.println("--------------------------------Hibernate JTA Connection Session Closed!!--------------------");
			System.out
					.println("--------------------------------------------------------------------");
		}
		sf.close();
		System.out
				.println("---------------------------Hibernate JTA Session Factory Closed!!-----------------");
		System.out
				.println("--------------------------------------------------------------------");
		System.out
				.println("--------------------------------------------------------------------");
	}

	public void load(Object obj, Serializable id) {

		try {
			session.load(obj, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object get(Class obj, Serializable id) {

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

	@Override
	public List<CartEntity> getList(String namedQuery, Map parameters) {
		// TODO Auto-generated method stub
		return null;
	}
}
