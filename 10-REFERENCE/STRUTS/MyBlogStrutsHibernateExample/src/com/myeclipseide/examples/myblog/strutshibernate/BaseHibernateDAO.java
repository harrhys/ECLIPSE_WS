package com.myeclipseide.examples.myblog.strutshibernate;

import org.hibernate.Session;

/**
 * Data access object (DAO) for domain model
 * 
 * http://www.myeclipseide.com/documentation/quickstarts/hibernate/
 * 
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO {
	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}
}
