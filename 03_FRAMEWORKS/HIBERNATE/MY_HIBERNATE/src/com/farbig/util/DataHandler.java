package com.farbig.util;

import java.io.Serializable;
import java.util.List;

import javax.transaction.UserTransaction;

public interface DataHandler {
	
	public void start();

	public void closeConnection();

	public void openSession();

	public void closeSession();

	public void load(Object obj, Serializable id);

	public Object get(Class obj, Serializable id);

	public Object save(Object obj);

	public void commit();

	public Object saveAndCommit(Object obj);

	public Object update(Object obj);

	public Object updateAndCommit(Object obj);

	public void delete(Object obj);

	public Object merge(Object obj);

	public Object mergeAndCommit(Object obj);

	public void save(List<Object> objs);
	
	public UserTransaction getTransaction();


}
