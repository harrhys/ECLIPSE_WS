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

public class JDBCTxnManagedHandler implements DataHandler {

	SessionFactory sf;

	Session session;

	Configuration config;

	Logger logger;

	public JDBCTxnManagedHandler() {
		
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);
		
		logger = Logger.getLogger("com.farbig.persistence.JDBCTxnManagedHandler");
		logger.log(Level.INFO, "INSIDE JDBCTxnManagedHandler");
		
		
		config = new Configuration().configure("hibernate.jdbc.cfg.xml");
		config.setInterceptor(new MyInterceptor());
		logger.log(Level.INFO, "Starting JDBCTxnManagedHandler");
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
			logger.log(Level.INFO,
					"---------------------------Session Factory Created------------------");
		}
		session = sf.getCurrentSession();
		logger.log(Level.INFO,
				"-----------Current Session retrieved----------------");
	}

	public void start() {
		session.beginTransaction();
		logger.log(Level.INFO,
				"-----------Transaction started!!----------------");
	}

	public void commit() {
		session.getTransaction().commit();
		logger.log(Level.INFO,
				"-----------Transaction commited and Session closed!!----------------");
	}

	public void rollback() {
		session.getTransaction().rollback();
		logger.log(Level.SEVERE,
				"-----------Transaction rolledback and Session closed!!----------------");
	}

	public void closeSession() {

		logger.log(Level.WARNING,
				"--------------------------------Session already Closed!!--------------------");

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
			openSession();
			session.load(obj, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object get(Class obj, Serializable id) {
		try {
			openSession();
			start();
			Object ob = session.get(obj, id);
			session.close();
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
			openSession();
			start();
			session.save(obj);
			commit();
			closeSession();
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
			openSession();
			start();
			session.update(obj);
			commit();
			closeSession();
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
			openSession();
			start();
			obj = session.merge(obj);
			commit();
			closeSession();
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

}
