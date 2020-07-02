package com.farbig.cart.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Admin
 *
 */
@Entity(name = "admin")
@DiscriminatorValue(value = "ADMIN")
public class Admin extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
	}

	@Column(name = "ADMIN_TYPE")
	private String adminType;

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

}
