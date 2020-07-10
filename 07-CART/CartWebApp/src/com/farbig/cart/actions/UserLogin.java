package com.farbig.cart.actions;

import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.farbig.cart.entity.User;
import com.farbig.cart.persistence.TxnMgmtType;
import com.farbig.cart.services.UserServices;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.opensymphony.xwork2.ActionSupport;

public class UserLogin extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;

	SessionMap<String, Object> session;

	private String username;

	private String password;

	String result = "success";

	UserServices service = (UserServices) ServiceHelper.getServiceProxy(UserServices.class);

	public String execute() {

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(Level.INFO);

		result = "success";

		HttpServletRequest request = ServletActionContext.getRequest();

		String s = request.getRequestURI();

		if (s.contains("login")) {

			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setTxnMgmtType(TxnMgmtType.JPA_JTA_DS);

			try {
				user = service.login(user);
				this.session.put("user", user);
				request.getSession().setAttribute("user", user);
				System.out.println("userid:" + user.getId());
				System.out.println("userType:" + user.getClass().getSimpleName());

			} catch (Exception e) {
				e.printStackTrace();
				addActionError("Please enter valid username and password");
				result = "error";
			}
		}

		return result;
	}

	public String home() {

		result = "success";

		if(session.get("user")==null)
			result="error";

		return result;
	}

	public String logout() {

		result = "welcome";

		session.invalidate();

		return result;
	}

	public void validate() {

		System.out.println("yyy");

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

	@Override
	public void setSession(Map<String, Object> session) {

		this.session = (SessionMap<String, Object>) session;

	}

}
