package com.farbig.practice.designpatterns.decorator;

public class AuthenticatedWebPage extends WebPageDecorator {
	
	public AuthenticatedWebPage(WebPage decoratedPage) {
		super(decoratedPage);
	}

	public void authenticateUser() {
		System.out.println("Authenticating user");
	}

	public void display() {
		super.display();
		this.authenticateUser();
	}
}