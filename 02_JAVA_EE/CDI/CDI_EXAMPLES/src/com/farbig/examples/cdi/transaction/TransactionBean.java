package com.farbig.examples.cdi.transaction;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.TransactionScoped;

@TransactionScoped
public class TransactionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void postConstruct() {
		System.out.println("[CDI transaction scope example] ExampleBean is created.");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("[CDI transaction scope example] ExampleBean is destroyed.");
	}
}
