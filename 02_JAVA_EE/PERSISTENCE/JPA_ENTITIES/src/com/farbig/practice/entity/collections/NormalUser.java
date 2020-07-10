package com.farbig.practice.entity.collections;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "COLL_NORMAL_USER")
@DiscriminatorValue(value = "NORMALUSER")

public class NormalUser extends BaseUser {
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;


}
