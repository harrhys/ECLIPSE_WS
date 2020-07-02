package com.farbig.cart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Merchant
 *
 */
@Entity(name = "merchant")
@DiscriminatorValue(value = "MERCHANT")
public class Merchant extends User implements Serializable {

	private static final long serialVersionUID = 331L;

	public Merchant() {
		super();
	}

	@Column(name = "BUSINESS_NAME")
	private String businessName;

	@OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Store> stores;
	
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}
}
