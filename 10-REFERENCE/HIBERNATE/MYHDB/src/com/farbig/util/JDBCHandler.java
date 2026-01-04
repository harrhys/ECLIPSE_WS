package com.farbig.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import javax.transaction.UserTransaction;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class JDBCHandler implements DataHandler{

	private static SessionFactory sf;

	Session session;
	
	Configuration config;

	public JDBCHandler() {
		
		if(sf==null || sf.isClosed())
		{
		System.out.println("Starting JDBC Handler");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);
		config = new Configuration().configure("jdbc-hibernate.cfg.xml");
		config.setInterceptor(new MyInterceptor());
		sf = config.buildSessionFactory();
		System.out.println("---------------------------Session Factory Created------------------");
		session = sf.openSession();
		//session = sf.getCurrentSession();
		session.beginTransaction();
		System.out.println("-----------Session Created and Transaction started!!----------------");
		}
		else
		{
			System.out
			.println("---------------------------Session Factory Retrieved------------------");
		}
	}
	
	public void openSession() {
		if (sf == null || sf.isClosed()) {
			sf = config.buildSessionFactory();
			System.out.println("---------------------------Session Factory Created------------------");
		}
		if (session != null && session.isConnected())
			session.close();
		session = sf.openSession();
		session.beginTransaction();
		
		System.out.println("-----------Session Created and Transaction started!!----------------");
	}
	
	public Session getSession() {
		return session;
	}

	public void closeSession() {
		if (session != null && session.isConnected())
			session.close();

		System.out.println("--------------------------------Session Closed!!--------------------");
		System.out.println("--------------------------------------------------------------------");
	}

	public void start() {
		//session.beginTransaction();
	}

	public SessionFactory getSessionFactory() {
		return sf;
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
			commit();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public void commit() {
		try {
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object saveAndCommit(Object obj) {
		try {
			session.save(obj);
			session.getTransaction().commit();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public Object update(Object obj) {
		try {
			session.save(obj);
			
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public Object updateAndCommit(Object obj) {
		try {
			session.update(obj);
			session.getTransaction().commit();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public void delete(Object obj) {
		try {
			session.delete(obj);
			session.getTransaction().commit();
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

	public Object mergeAndCommit(Object obj) {
		try {
			obj = session.merge(obj);
			session.getTransaction().commit();
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
			session.getTransaction().commit();
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
	public UserTransaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

}
