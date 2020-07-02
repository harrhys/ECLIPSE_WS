package com.farbig.cart.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity implementation class for Entity: OrderItem
 *
 */
@Entity(name = "orderitem")
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"ORDER_ID", "ORDER_ITEM_ID"})
	}) 
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	public OrderItem() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id")
	@SequenceGenerator(name = "order_item_id", sequenceName = "order_item_id", allocationSize = 1)
	@Column(name = "ORDER_ITEM_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	@OneToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;
	
	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "STATUS")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
