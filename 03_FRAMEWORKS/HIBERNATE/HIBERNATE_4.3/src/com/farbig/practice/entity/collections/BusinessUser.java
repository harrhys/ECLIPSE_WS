package com.farbig.practice.entity.collections;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "COLL_BUSINESS_USER")
@DiscriminatorValue(value = "BUSINESSUSER")
public class BusinessUser extends BaseUser {
	
	public BusinessUser() {
		super();
	}

	public BusinessUser(BaseUser user) {
		super(user);
	}
	
	private String businessName;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	
}
