package com.farbig.cart.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.farbig.cart.entity.CartEntity;

public interface PersistenceHandler {

	public Object save(Object obj);
	
	public void load(Object obj, Serializable id);

	public Object get(Class<?> obj, Serializable id);
	
	public List<CartEntity> getList(String namedQuery, Map<?,?> parameters);

	public Object update(Object obj);

	public void delete(Object obj);

	public Object merge(Object obj);

	public void save(List<Object> objs);

	public void closeConnection();

	public void openSession();

	public void closeSession();

}
