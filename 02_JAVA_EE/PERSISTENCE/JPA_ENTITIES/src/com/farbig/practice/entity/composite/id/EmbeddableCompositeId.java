package com.farbig.practice.entity.composite.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmbeddableCompositeId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "A")
	private int a;
	
	@Column(name = "B")
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
	
	public boolean equals(Object o)
	{
		EmbeddableCompositeId id = (EmbeddableCompositeId) o;
		
		return id.getA()==this.getA() && id.getB()==this.getB();
	}
	
	public int hashcode() {
		return this.hashcode();
	}
}
