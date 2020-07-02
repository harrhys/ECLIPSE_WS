package com.farbig.examples.jsf.flows;

import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@FlowScoped("signup")
public class SignupController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
