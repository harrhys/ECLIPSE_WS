package com.farbig.cart.actions;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.services.ProductService;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.opensymphony.xwork2.ActionSupport;

public class ProductSearch extends ActionSupport implements SessionAware {

	public String execute() {

		result = "success";

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(Level.WARNING);

		try {

			if (selectType == null || selectType.equals("")) {

				setCategories(service.getAllCategories());
				setDisplay("categories");

			} else if (selectType.equals("categoryId")) {

				setProducts(service.getProductsByCategory(categoryId));
				setDisplay("products");

			} else if (selectType.equals("productId")) {

				setProduct(service.getProduct(productId));
				setDisplay("product");

			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}

	public void validate() {

		HttpSession session = ServletActionContext.getRequest().getSession();

		if (session.getAttribute("user") == null) {

			result = "welcome";
			session.invalidate();
			this.session.invalidate();
			ServletActionContext.getRequest().getSession(true);

		}

		if (selectType != null && selectType.equals("categoryId") && (categoryId == -1)) {

			addActionError("Please select the category");
			setDisplay("categories");
			result = "error";

			try {
				categories = service.getAllCategories();
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Categories--" + categories.size());

		} else if (selectType != null && selectType.equals("productId") && (productId == null || productId == -1)) {

			addActionError("Please select the Product");
			setDisplay("products");
			result = "error";

			try {
				setProducts(service.getProductsByCategory(categoryId));
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Products--" + products.size());

		}

	}

	public List<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductCategory> categories) {
		this.categories = categories;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
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

	@Override
	public void setSession(Map<String, Object> session) {

		this.session = (SessionMap<String, Object>) session;

	}

	private static final long serialVersionUID = 1L;

	SessionMap<String, Object> session;

	private List<ProductCategory> categories;
	private List<Product> products;
	private Product product;
	private Integer categoryId;
	private Integer productId;
	private String selectType;
	private String display;
	private String result;

	ProductService service = (ProductService) ServiceHelper.getServiceProxy(ProductService.class);

}
