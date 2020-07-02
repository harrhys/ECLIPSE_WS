package com.farbig.examples.cdi.events;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;

@Singleton
@Startup
public class EventObserver {

	public void approve(@Observes @Default CheckEvent event) {
		/*
		 * do something
		 */
		event.getFacesContext().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome on board, " + "" + event.getName() + "!", null));
	}

	public void deny(@Observes @Deny CheckEvent event) {
		/*
		 * do something
		 */
		event.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Sorry " + event.getName() + ", you aren't old enough to join us.", null));
	}
}
