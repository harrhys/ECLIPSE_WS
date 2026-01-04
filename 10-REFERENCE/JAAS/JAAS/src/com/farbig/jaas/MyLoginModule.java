package com.farbig.jaas;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class MyLoginModule implements LoginModule{
	
	public static final String TEST_USER = "balaji";
	
	public static final String TEST_PASSWORD = "password";
	
	private boolean flag = false;
	
	private CallbackHandler callbackHandler = null;

	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		
		this.callbackHandler = callbackHandler;
		
	}

	public boolean login() throws LoginException {
		
		Callback[] callbackArray = new Callback[2];
		callbackArray[0]= new NameCallback("User Name");
		callbackArray[1]= new PasswordCallback("Password", false);
		
		try {
			this.callbackHandler.handle(callbackArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedCallbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String username = ((NameCallback)callbackArray[0]).getName();
		String password = new String(((PasswordCallback)callbackArray[1]).getPassword());
		
		if(TEST_USER.equals(username) && TEST_PASSWORD.equals(password))
		{
			System.out.println("Authentication Sucess!!");
			flag = true;
			return flag;
		}
		else
		{
			throw new FailedLoginException("Authentication Failure!!");
		}
	}

	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		return flag;
	}

	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

}
