package com.farbig.practice.entity.relations;

import java.io.Serializable;

public class VehicleId implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String number;
	
	public VehicleId()
	{
		
	}
	
	public VehicleId(int id, String number) {
		
		this.id = id;
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	

}
