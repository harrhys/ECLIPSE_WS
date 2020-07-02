package com.farbig.cart.actions;

import java.util.List;
import java.util.logging.Level;

import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.services.ProductService;
import com.farbig.cart.services.gateway.util.ServiceHelper;
import com.opensymphony.xwork2.ActionSupport;

public class ProductSearchAction extends ActionSupport {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private List<ProductCategory> categories;
	private List<Product> products;
	private Product product;
	private Integer categoryId;
	private Integer productId;
	private String flag;
	private String result;

	ProductService service = (ProductService) ServiceHelper
			.getServiceProxy(ProductService.class);

	public String execute() {

		result = "success";

		System.out.println("xxx");

		java.util.logging.Logger.getLogger("com.opensymphony").setLevel(
				Level.OFF);
		try {
			if (flag == null || flag.equals("")) {
				categories = service.getAllCategories();
				System.out.println("CATEGORIES--" + categories.size());
				flag = "cat";
			} else if (flag.equals("cat")) {
				setProducts(service.getProductsByCategory(categoryId));
				flag = "pro";
			} else if (flag.equals("pro")) {
				product = service.getProduct(productId);
				flag = "det";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		}

		return result;
	}

	public void validate() {
		
		if(flag!=null && flag.equals("cat")&& (categoryId==null || categoryId==0))
		{
			
			addActionError("Please select the category");
			try {
				categories = service.getAllCategories();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("CATEGORIES--" + categories.size());
			flag = "cat";
			result = "error";
		}

	}

	public List<ProductCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ProductCategory> categories) {
		this.categories = categories;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

}
