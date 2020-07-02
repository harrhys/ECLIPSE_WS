package com.farbig.cart.actions;

import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.struts2.interceptor.SessionAware;

import com.farbig.cart.entity.Admin;
import com.farbig.cart.entity.User;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.PersistenceHandlerFactory;
import com.farbig.cart.persistence.TxnMgmtType;
import com.farbig.cart.services.UserServices;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String name;

	String result = "success";

	UserServices service = (UserServices) ServiceHelper
			.getServiceProxy(UserServices.class);

	public String execute() {

		result = "success";
		
		System.out.println("xxx");

		return result;
	}

	public void validate() {
		
		System.out.println("yyy");

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		try {
			//user = service.login(user);
			System.out.println(user.getName());
			name = (user.getName());
			
			
			//Admin user = new Admin();
			user.setName("Hibernate JTA");
			//user.setAdminType("ROOT");
			user.setStatus("ACTIVE");
			user.setUsername("cartuser2");
			user.setPassword("cart123");
			user.setCreatedBy("test");
			user.setCreatedDate(new Date());
			user.setTxnMgmtType(TxnMgmtType.HIBERNATE_JTA);
			PersistenceHandler handler = PersistenceHandlerFactory.getDataHandler(TxnMgmtType.JPA_NON_JTA_DS);
			handler.openSession();
			user = (User) handler.save(user);
			handler.closeSession();
			this.session.put("userId", user);
			System.out.println("userid:"+user.getId());

			
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Please enter valid username and password");
			result = "error";
		}
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
	
	 Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		
		this.session = session;
		
	}

}
