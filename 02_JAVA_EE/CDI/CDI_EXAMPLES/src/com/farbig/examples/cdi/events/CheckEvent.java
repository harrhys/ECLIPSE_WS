package com.farbig.examples.cdi.events;

import javax.faces.context.FacesContext;

public class CheckEvent {

	private String name;

	private FacesContext facesContext;

	public CheckEvent(String name, FacesContext facesContext) {
		this.name = name;
		this.facesContext = facesContext;
	}

	public String getName() {
		return name;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}
}
