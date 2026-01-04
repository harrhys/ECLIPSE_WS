package com.farbig.collections;

import java.io.Serializable;

public class TestCompositeID implements Serializable{
	
	/**
	 * 
	 */
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
	
	@Override
	public boolean equals(Object obj) {
		
		TestCompositeID testid = (TestCompositeID) obj;
		
		String id = testid.getA()+""+testid.getB();
		
		return (this.a+""+this.b).equals(id);
	}
	
	@Override
	public int hashCode() {
		return new Integer(this.a+""+this.b);
	}
	
	

}
