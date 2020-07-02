package com.farbig.cart.services;

import java.util.List;

import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.entity.Store;

public interface ProductService {
	
	public Store createStore(Store store) throws Exception;
	
	public ProductCategory addCategory(ProductCategory category) throws Exception;
	
	public List<ProductCategory> getAllCategories() throws Exception;
	
	public Product addProduct(Product product) throws Exception;
	
	public Product getProduct(Integer productId) throws Exception;
	
	public Product getProduct(String productCode) throws Exception;
	
	public List<Product> getProductsByCategory(Integer categoryId) throws Exception;
	
	public List<Product> getProductsByStore(Integer storeId) throws Exception;

}
