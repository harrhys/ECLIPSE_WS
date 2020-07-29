package com.farbig.practice.designpatterns.decorator;

public class BasicWebPage implements WebPage {

	public String html = "";
	public String styleSheet = "";
	public String script = "";

	public void display() {
		/* Renders the HTML to the stylesheet, and run any embedded scripts */
		System.out.println("BasicWebPage");
	}
}