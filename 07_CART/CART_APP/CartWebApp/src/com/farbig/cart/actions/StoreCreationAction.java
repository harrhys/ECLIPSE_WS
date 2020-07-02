package com.farbig.cart.actions;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.apache.struts2.interceptor.SessionAware;

import com.farbig.cart.entity.Merchant;
import com.farbig.cart.entity.Store;
import com.farbig.cart.entity.User;
import com.farbig.cart.services.ProductService;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.opensymphony.xwork2.ActionSupport;

public class StoreCreationAction extends ActionSupport implements SessionAware {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private List<Store> stores;
	private String name;
	private String desc;
	private String result;

	ProductService service = (ProductService) ServiceHelper
			.getServiceProxy(ProductService.class);

	public String execute() {

		result = "success";

		System.out.println("xxx");

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(
				Level.OFF);
		try {
			Store store = new Store();
			store.setStoreName(name);
			store.setDescription(desc);
			User user = (User) this.session.get("userId");
			Merchant mer = new Merchant();
			mer.setId(user.getId());
			store.setMerchant(mer);
			store = service.createStore(store);

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}

	public void validate() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {

		this.session = session;

	}

}
