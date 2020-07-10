package com.farbig.practice.entity.composite.id;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.farbig.practice.entity.BaseEntity;


@Entity
@Table(name= "COMPOSITE_ENTITY")
@IdClass(SimpleCompositeId.class)
public class SimpleCompositeEntity extends BaseEntity {
	
	@Id
	private int a;
	
	@Id
	private int b;
	
	private String c;
	
	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
