package com.farbig.practice.persistence.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.farbig.practice.persistence.PersistenceHandler;
import com.farbig.practice.persistence.TxnMgmtType;

public class HibernateJDBCHandler implements PersistenceHandler {

	private SessionFactory sf = null;

	Session session;

	private TxnMgmtType txnMgmtType = TxnMgmtType.HIBERNATE_JDBC;

	public HibernateJDBCHandler() {

		System.out.println("");
		System.out.println("Starting HibernateJDBCHandler");
		createSessionFactory();
	}
	
	public void openConnection() {
		
		createSessionFactory();
		
	}

	@SuppressWarnings("deprecation")
	private void createSessionFactory() {

		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getGlobal().setLevel(Level.OFF);

		if (sf == null || sf.isClosed()) {
			sf = new Configuration().configure("hibernate-jdbc.cfg.xml")
					.setInterceptor(new UserInterceptor())
					.buildSessionFactory();
			System.out.println("---------------------------Hibernate JDBC Session Factory Created------------------");
		} else {
			System.out.println("---------------------------Hibernate JDBC Session Factory Retrieved------------------");
		}
	}

	public SessionFactory getSessionFactory() {
		if (sf == null || sf.isClosed()) {
			createSessionFactory();
		}
		return sf;
	}

	public void openSession() {
		session = getSessionFactory().openSession();
		System.out.println("-----------Hibernate JDBC Session Created !!----------------");
		session.getTransaction().begin();
		System.out
				.println("--------------------------------Hibernate JDBC Transaction Started!!--------------------");
	}

	public void commit() {

		if (session.getTransaction().isActive()) {

			session.getTransaction().commit();
			System.out
					.println("------------------------Hibernate JDBC Transaction Commited--------------------");
		} else {
			System.out
					.println("------------------------No Active Transaction to Commit--------------------");
		}
	}

	public void closeSession() {

		if (session != null && session.isConnected())
			session.close();
		System.out
				.println("--------------------------------Hibernate JDBC Session Closed!!--------------------");
		System.out
				.println("--------------------------------------------------------------------");
	}

	public void closeConnection() {

		if (session != null && session.isConnected()) {
			session.close();
			System.out
					.println("--------------------------------Hibernate JDBC Connection Session Closed!!--------------------");
			System.out
					.println("--------------------------------------------------------------------");
		}
		sf.close();
		System.out
				.println("---------------------------Hibernate JDBC Session Factory Closed!!-----------------");
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
			session.update(obj);
			session.flush();
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
			session.flush();
			commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object merge(Object obj) {

		try {
			obj = session.merge(obj);
			commit();
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
	public List getList(String namedQuery, Map parameters) {
		// TODO Auto-generated method stub
		return null;
	}
}
