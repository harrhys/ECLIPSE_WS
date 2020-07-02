package com.farbig.jaas;

import javax.security.auth.login.LoginContext;

public class AppLoginTest {

	public static void main(String[] args) {

		System.setProperty("java.security.auth.login.config", "jaas.config");
		LoginContext context = null;
		try {
			context = new LoginContext("jaas", new AppLoginCallBackHandler());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		while (true) {
			try {
				context.login(); 
				System.exit(0);
			} catch (Exception e) {
				System.out.println(e.getMessage());;
			}
		}
	}

}
