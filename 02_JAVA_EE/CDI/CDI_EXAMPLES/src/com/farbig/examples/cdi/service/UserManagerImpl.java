/**
 * @author Copyright (c) 2010,2013, Oracle and/or its affiliates. All rights reserved.
 *  
 */
package com.farbig.examples.cdi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.farbig.examples.cdi.entity.User;
import com.farbig.examples.cdi.qualifier.Stored;
import com.farbig.examples.cdi.qualifier.UserEM;
import com.farbig.examples.cdi.util.UserFactory;

@SuppressWarnings("unchecked")
public class UserManagerImpl implements UserManager {
	@Inject
	@UserEM
	private EntityManager em;

	@Inject
	private Login current;

	@Produces
	@Stored
	public User findUser() {
		System.out.println("inside findUser");
		User user = null;
		if (current.getUsername() != null) {

			Map<String, String> params = new HashMap<String, String>();
			params.put("username", current.getUsername());
			params.put("password", current.getPassword());
			
			List<User> users = (List<User>) getList("login", params);
			if (users != null && users.size() > 0)
				user = users.get(0);

			// user = em.find(User.class, Integer.valueOf(current.getUserName()));
			if (user != null)
				UserFactory.getInstance().getUserMap().put(current.getUsername(), user);
		}
		return user;
	}

	public List<?> getList(String namedQuery, Map<String, String> parameters) {

		List<?> entities = null;
		Query query;

		if (parameters.containsKey("CQ")) {

			parameters.remove("CQ");
			query = em.createQuery(namedQuery);

		} else {

			query = em.createNamedQuery(namedQuery);

		}

		if (parameters != null) {
			for (Object key : parameters.keySet()) {

				query.setParameter(key.toString(), parameters.get(key));
			}
		}
		entities = query.getResultList();

		return entities;
	}
}
