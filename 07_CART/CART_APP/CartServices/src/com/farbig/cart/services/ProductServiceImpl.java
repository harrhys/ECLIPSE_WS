package com.farbig.cart.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.farbig.cart.entity.Product;
import com.farbig.cart.entity.ProductCategory;
import com.farbig.cart.entity.Store;
import com.farbig.cart.persistence.PersistenceHandler;
import com.farbig.cart.persistence.PersistenceHandlerFactory;
import com.farbig.cart.persistence.TxnMgmtType;

public class ProductServiceImpl implements ProductService {

	PersistenceHandler handler = PersistenceHandlerFactory
			.getDataHandler(TxnMgmtType.JPA_JTA_DS);

	@Override
	public Store createStore(Store store) throws Exception {

		handler.openSession();
		handler.save(store);
		handler.closeSession();

		return store;

	}

	@Override
	public ProductCategory addCategory(ProductCategory category)
			throws Exception {

		handler.openSession();
		handler.save(category);
		handler.closeSession();

		return category;
	}

	@Override
	public List<ProductCategory> getAllCategories() throws Exception {

		List<ProductCategory> categories = new ArrayList();

		List results = handler.getList("getAllCategories", null);

		System.out.println("We found categories - " + results.size());

		for (Object result : results) {
			ProductCategory category = (ProductCategory) result;
			categories.add(category);
		}
		return categories;

	}

	@Override
	public Product addProduct(Product product) throws Exception {

		handler.openSession();
		handler.save(product);
		handler.closeSession();
		return product;
	}
	
	@Override
	public Product getProduct(Integer productId) throws Exception {

		return (Product) handler.get(Product.class, productId);
	}
	
	@Override
	public Product getProduct(String productCode) throws Exception {

		Product product = null;
		Map params = new HashMap();
		params.put("productCode", productCode);
		List results = handler.getList("getProductByCode", params);
		System.out.println("We found products - " + results.size());

		if (results != null && results.size() > 0)
			product = (Product) results.get(0);

		return product;
	}

	@Override
	public List<Product> getProductsByCategory(Integer categoryId) throws Exception {

		List<Product> products = new ArrayList();
		Map params = new HashMap();
		params.put("categoryId", categoryId);
		List results = handler.getList("getProductsByCategory", params);
		System.out.println("We found products - " + results.size());
		for (Object result : results) {
			Product product = (Product) result;
			products.add(product);
		}
		return products;
	}

	
	@Override
	public List<Product> getProductsByStore(Integer storeId) throws Exception {

		List<Product> products = new ArrayList();
		Map params = new HashMap();
		params.put("storeId", storeId);
		List results = handler.getList("getProductsByStoreId", params);
		System.out.println("We found products - " + results.size());
		for (Object result : results) {
			Product product = (Product) result;
			products.add(product);
		}
		return products;
	}

	
	

}
