package com.farbig.practice.designpatterns.decorator;

import org.junit.Test;

public class Driver {
	@Test
	public void test() {
		WebPage myPage = new BasicWebPage();
		myPage = new AuthorizedWebPage(myPage);
		myPage = new AuthenticatedWebPage(myPage);
		myPage.display();
	}
}