package com.farbig.cart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "productcategory")
@NamedQueries({ @NamedQuery(name = "getAllCategories", query = "select pc from productcategory pc") })
public class ProductCategory extends CartEntity implements  Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_category_id")
	@SequenceGenerator(name = "prod_category_id", sequenceName = "prod_category_id", allocationSize = 1)
	@Column(name = "PRODUCT_CATEGORY_ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "STATUS")
	private String status;

	@OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = Product.class)
	private List<Product> products;

	public ProductCategory() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
