package com.farbig.cart.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "product")
@NamedQueries({ 
	@NamedQuery(name = "getProductsByCategory", query = "select p from product p where p.productCategory.id=:categoryId") ,
	@NamedQuery(name = "getProductsByStore", query = "select p from product p where p.store.id=:storeId"), 
	@NamedQuery(name = "getProductByCode", query = "select p from product p where p.productCode=:productCode"),
	@NamedQuery(name = "getProductsByMerchant", query = "select p from product p,users u, store s where p.store.id=s.id and s.merchant.id=u.id and u.id=:userid")
	})
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_id")
	@SequenceGenerator(name = "prod_id", sequenceName = "prod_id", allocationSize = 1)
	@Column(name = "PRODUCT_ID")
	private Integer id;

	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "PRODUCT_CODE", unique = true, nullable = false)
	private String productCode;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "PRICE", nullable = false)
	private Float price;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private ProductCategory productCategory;
	
	@ManyToOne
	@JoinColumn(name = "STORE_ID", nullable = false)
	private Store store;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
	private OrderItem orderItem;

	public Product() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

}
