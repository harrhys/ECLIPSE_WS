package com.farbig.examples.cdi.events;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Model
public class WebController {

	@Inject
	User user;

	@Inject
	EventProducer producer;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void check() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		producer.check(user, facesContext);
	}

}