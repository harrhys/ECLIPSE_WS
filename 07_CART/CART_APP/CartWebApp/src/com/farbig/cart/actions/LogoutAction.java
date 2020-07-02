package com.farbig.cart.actions;

import com.farbig.cart.entity.User;
import com.farbig.cart.services.UserServices;
import com.farbig.cart.services.gateway.util.ServiceHelper;


public class LogoutAction {

	private String username;

	private String password;
	
	private String name;

	public String execute() {
		
		String result = "success";
		
		UserServices service = (UserServices) ServiceHelper.getServiceProxy(UserServices.class);
		
		User user = new User();
		
		user.setUsername(username);
		user.setPassword(password);
		
		try {
			user  = service.login(user);
			System.out.println(user.getName());
			name=(user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
