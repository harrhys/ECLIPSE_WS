package com.farbig.cart.actions;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.farbig.cart.entity.Merchant;
import com.farbig.cart.entity.Store;
import com.farbig.cart.entity.User;
import com.farbig.cart.services.ProductService;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.opensymphony.xwork2.ActionSupport;

public class StoreCreation extends ActionSupport implements SessionAware {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private List<Store> stores;
	private Store store;
	private String name;
	private String desc;
	private String result;

	ProductService service = (ProductService) ServiceHelper.getServiceProxy(ProductService.class);

	public String execute() {

		result = "success";

		System.out.println("execute");

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(Level.OFF);
		try {
			store = new Store();
			store.setStoreName(name);
			store.setDescription(desc);
			User user = (User) this.session.get("user");
			Merchant mer = new Merchant();
			mer.setId(user.getId());
			store.setMerchant(mer);
			store = service.createStore(store);
			clear();

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}
	
	public String show() {

		result = "success";

		System.out.println("show");

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(Level.OFF);
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();

			User user = (User) session.getAttribute("user");
			setStores(service.getStoresByMerchant(user.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}

	public void validate() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {

			result = "welcome";
			session.invalidate();
			
		}

	}
	
	private void clear()
	{
		name = desc = null;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

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
