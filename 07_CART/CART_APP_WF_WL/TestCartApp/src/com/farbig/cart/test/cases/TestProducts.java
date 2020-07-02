package com.farbig.cart.test.cases;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.farbig.cart.entity.Account;
import com.farbig.cart.entity.Merchant;
import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.entity.Store;
import com.farbig.cart.services.ProductService;
import com.farbig.cart.services.UserServices;
import com.farbig.cart.services.gateway.util.ServiceHelper;

public class TestProducts {

	UserServices userService = (UserServices) ServiceHelper
			.getServiceProxy(UserServices.class);
	ProductService service = (ProductService) ServiceHelper
			.getServiceProxy(ProductService.class);

	// @Test
	public void addStore() throws Exception {

		Store store = new Store();
		store.setDescription("Merchants First Store");
		store.setStoreName("My First Store");
		Merchant mer = new Merchant();
		mer.setId(2);
		store.setMerchant(mer);
		service.createStore(store);
		System.out.println("STORE ID--" + store.getId());
		assertTrue(store.getId() > 0);

	}

	// @Test
	public void addProductCategory() throws Exception {

		ProductCategory category = new ProductCategory();
		category.setDescription("All Electronic Products come under this category");
		category.setName("Electronics");
		category.setStatus("ACTIVE");
		service.addCategory(category);
		System.out.println("Category ID--" + category.getId());
		assertTrue(category.getId() > 0);

	}

	//@Test
	public void addProducts() throws Exception {

		Product product = new Product();
		product.setDescription("Samsung Galaxy S6 Edge");
		product.setName("Samsung S6 Edge");
		product.setProductCode("SAMSUNG_S6_EDGE");
		Store store = new Store();
		store.setId(1);
		product.setStore(store);
		ProductCategory category = new ProductCategory();
		category.setId(1);
		product.setProductCategory(category);
		product = service.addProduct(product);
		System.out.println("Product ID--" + product.getId());
		assertTrue(category.getId() > 0);

	}
	
	@Test
	public void getProductById() throws Exception {

		Product product = service.getProduct(4);
		System.out.println("Product Name--" + product.getName());
		assertTrue(product.getName()!=null);

	}
	
	@Test
	public void getProductByCode() throws Exception {

		Product product = service.getProduct("SAMSUNG_S6");
		System.out.println("Product ID--" + product.getId());
		assertTrue(product.getName()!=null);

	}
	
	@Test
	public void getProductsByCategory() throws Exception {

		List<Product> products = service.getProductsByCategory(1);
		System.out.println("Products in the Category-"+products.get(0).getProductCategory().getName());
		for (Product product : products) {
			System.out.println("Product ID--" + product.getId());
		}
		
		assertTrue(products.size()>0);

	}
	
	@Test
	public void getProductByStore() throws Exception {

		List<Product> products = service.getProductsByStore(1);
		System.out.println("Product ID--" + products.get(0).getId());
		assertTrue(products.size()>0);

	}
}
