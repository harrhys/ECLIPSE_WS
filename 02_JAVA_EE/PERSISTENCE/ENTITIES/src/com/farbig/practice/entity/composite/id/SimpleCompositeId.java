package com.farbig.practice.entity.composite.id;

import java.io.Serializable;

public class SimpleCompositeId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int a;
	
	private int b;

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
	
	

}
