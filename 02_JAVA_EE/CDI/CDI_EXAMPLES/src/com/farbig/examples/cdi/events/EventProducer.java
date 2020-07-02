package com.farbig.examples.cdi.events;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.faces.context.FacesContext;

public class EventProducer {

	@Inject
	Event<CheckEvent> approval;

	@Inject
	@Deny
	Event<CheckEvent> deny;

	public void check(User user, FacesContext facesContext) {
		CheckEvent check = new CheckEvent(user.getName(), facesContext);

		if (user.getAge() < 18) {
			deny.fire(check);
		} else {
			approval.fire(check);
		}
	}
}
