package com.farbig.persistence.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.farbig.persistence.DataHandler;

public class JDBCUserManagedHandler implements DataHandler {

	SessionFactory sf;

	Session session;

	Configuration config;

	Logger logger;

	public JDBCUserManagedHandler() {
		
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.WARNING);
		
		logger = Logger.getLogger("com.farbig.persistence.JDBCUserManagedHandler");
		logger.log(Level.INFO, "INSIDE JDBCUserManagedHandler");
		
		config = new Configuration().configure("hibernate.jdbc.cfg.xml");
		config.setInterceptor(new MyInterceptor());
		logger.log(Level.INFO, "Starting JDBCUserManagedHandler");
		sf = config.buildSessionFactory();
		logger.log(Level.INFO,
				"---------------------------Session Factory Created------------------");
	}

	public Session getSession() {
		return session;
	}

	public SessionFactory getSessionFactory() {
		return sf;
	}

	public void openSession() {
		if (sf == null || sf.isClosed()) {
			sf = config.buildSessionFactory();
			System.out
					.println("---------------------------Session Factory Created------------------");
		}
		session = sf.openSession();
		System.out
				.println("-----------Current Session retrieved----------------");
	}

	public void start() {
		session.beginTransaction();
		logger.log(Level.INFO,
				"-----------Transaction started!!----------------");
	}

	public void commit() {
		session.getTransaction().commit();
		logger.log(Level.INFO,
				"-----------Transaction commited !!----------------");
	}

	public void rollback() {
		session.getTransaction().rollback();
		logger.log(Level.INFO,
				"-----------Transaction rolledback!!----------------");
	}

	public void closeSession() {

		if (session != null && session.isOpen()) {
			logger.log(Level.WARNING, "" + session.isConnected());
			session.close();
			logger.log(Level.WARNING,
					"--------------------------------Open Session is Closed!!--------------------");
			logger.log(Level.WARNING, "" + session.isConnected());
		} else {
			logger.log(Level.WARNING,
					"--------------------------------Session already Closed!!--------------------");
		}

		logger.log(Level.WARNING,
				"--------------------------------------------------------------------");

	}

	public void closeConnection() {
		if (session != null && session.isConnected()) {
			session.close();
			logger.log(
					Level.WARNING,
					"--------------------------------Connected Session Closed!!--------------------");
			logger.log(Level.WARNING,
					"--------------------------------------------------------------------");
		}
		sf.close();
		logger.log(Level.WARNING,
				"---------------------------Session Factory Closed!!-----------------");
		logger.log(Level.WARNING,
				"--------------------------------------------------------------------");

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
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
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
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return obj;
		}
	}

	public void delete(Object obj) {
		try {
			session.delete(obj);
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
			// session.getTransaction().commit();
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

}
