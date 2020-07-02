package com.farbig.cart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Store
 *
 */
@Entity(name = "store")
@NamedQueries({ 
	@NamedQuery(name = "getAllStores", query = "select s from store s"), 
	@NamedQuery(name = "getStoresByMerchant", query = "select s from store s where s.merchant.id=:merchantId"),
	@NamedQuery(name = "getStoreById", query = "select s from store s where s.id=:storeId")})
public class Store implements Serializable {

	private static final long serialVersionUID = 1L;

	public Store() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_id")
	@SequenceGenerator(name = "store_id", sequenceName = "store_id", allocationSize = 1)
	@Column(name = "STORE_ID")
	private int id;

	private String storeName;

	private String description;

	@ManyToOne
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private Merchant merchant;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Product> products;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {

		if (this.products == null)
			this.products = products;
		else
			this.products.addAll(products);
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
