package com.farbig.cart.actions;

import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.entity.Store;
import com.farbig.cart.entity.User;
import com.farbig.cart.services.ProductService;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.opensymphony.xwork2.ActionSupport;

public class ProductCreation extends ActionSupport {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private List<ProductCategory> categories;
	private List<Store> stores;
	private List<Product> products;
	private Product product;
	private Integer storeId;
	private Integer categoryId;
	private Integer productId;
	private String name;
	private String code;
	private String price;
	private String desc;
	private String result;

	ProductService service = (ProductService) ServiceHelper.getServiceProxy(ProductService.class);

	public String execute() {

		result = "success";

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(Level.WARNING);
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			setCategories(service.getAllCategories());
			setStores(service.getStoresByMerchant(user.getId()));
			System.out.println("Categories--" + categories.size());
			System.out.println("Stores--" + stores.size());

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}

	public String show() {

		result = "success";

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(Level.WARNING);
		try {

			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			setProducts(service.getProductsByMerchant(user.getId()));

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}

	public String create() {

		result = "success";

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(Level.WARNING);
		try {
				product = new Product();
				product.setName(name);
				product.setDescription(desc);
				product.setPrice(new Float(price));
				product.setProductCode(code);
				ProductCategory cat = new ProductCategory();
				cat.setId(categoryId);
				product.setProductCategory(cat);
				Store store = new Store();
				store.setId(storeId);
				product.setStore(store);
				setProduct(service.addProduct(product));
				
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
			addActionError(e.getMessage());
		}

		return result;
	}

	public void validate() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {

			result = "login";
			session.invalidate();
		}

	}

	public List<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductCategory> categories) {
		this.categories = categories;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
