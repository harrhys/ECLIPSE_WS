package com.farbig.cart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Customer
 *
 */
@Entity(name = "customer")
@DiscriminatorValue(value = "CUSTOMER")
public class Customer extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	public Customer() {
		super();
	}

	@Column(name = "CUSTOMER_TYPE")
	private String customerType;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Order> orders;
	
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
