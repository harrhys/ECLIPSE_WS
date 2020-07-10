package com.farbig.practice.jsf.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class CalculatorTest {
	private String name;

	public CalculatorTest() {
	}

	public String getName() {
		return name;
	}

	public void setName(String user_name) {
		this.name = user_name;
	}
}