package com.farbig.jaas;

import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class AppLoginCallBackHandler implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		if (callbacks[0] instanceof NameCallback) {
			NameCallback name = (NameCallback) callbacks[0];
			PasswordCallback password = (PasswordCallback) callbacks[1];
			System.out.println(name.getPrompt());
			name.setName(new Scanner(System.in).nextLine());

			System.out.println(password.getPrompt());
			password.setPassword(new Scanner(System.in).nextLine()
					.toCharArray());
		} else {
			NameCallback name = (NameCallback) callbacks[1];
			PasswordCallback password = (PasswordCallback) callbacks[0];
			System.out.println(name.getPrompt());
			name.setName(new Scanner(System.in).nextLine());

			System.out.println(password.getPrompt());
			password.setPassword(new Scanner(System.in).nextLine()
					.toCharArray());
		}
	}

}
