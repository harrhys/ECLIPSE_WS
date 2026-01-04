package com.farbig.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

public class JPAEJBHandler implements DataHandler {

	private EntityManagerFactory emf;

	private EntityManager em;

	public JPAEJBHandler() {
		
		if(emf==null || !emf.isOpen())

		emf = Persistence.createEntityManagerFactory("test");

		em = emf.createEntityManager();

	}

	public void start() {

		em.joinTransaction();
	}

	public void closeConnection() {

		emf.close();
	}

	public void openSession() {

		if (!emf.isOpen())

			emf = Persistence.createEntityManagerFactory("test");

		em = emf.createEntityManager();

		//em.joinTransaction();
	}

	public void closeSession() {

		if (em != null && em.isOpen())

			em.close();
	}

	public void load(Object obj, Serializable id) {

	}

	public Object get(Class obj, Serializable id) {

		Object ob = em.find(obj, id);
		return ob;
	}

	public Object save(Object obj) {
		em.persist(obj);
		return obj;
	}

	public void commit() {

		em.getTransaction().commit();
	}

	public Object saveAndCommit(Object obj) {
		return null;
	}

	public Object update(Object obj) {
		return null;
	}

	public Object updateAndCommit(Object obj) {
		return null;
	}

	public void delete(Object obj) {

	}

	public Object merge(Object obj) {
		return null;
	}

	public Object mergeAndCommit(Object obj) {
		return null;
	}

	public void save(List<Object> objs) {

	}

	@Override
	public UserTransaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}
}
