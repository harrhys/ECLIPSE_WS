package com.farbig.collections;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Version;


@Entity
@IdClass(TestCompositeID.class)
public class Test {
	
	@Id
	private int a;
	
	@Id
	private int b;
	
	private String c;
	
	@Version
	private int version;

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
