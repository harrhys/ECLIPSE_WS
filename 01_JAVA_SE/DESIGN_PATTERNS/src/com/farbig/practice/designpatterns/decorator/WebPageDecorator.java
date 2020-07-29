package com.farbig.practice.designpatterns.decorator;

public abstract class WebPageDecorator implements WebPage {
	protected WebPage page;

	public WebPageDecorator(WebPage webpage) {
		this.page = webpage;
	}

	public void display() {
		this.page.display();
	}
}