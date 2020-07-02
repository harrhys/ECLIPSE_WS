package com.farbig.ws.objects;

import javax.xml.ws.BindingType;

@BindingType()
public class Product {
	
	int id;
	
	String code;
	
	String name;
	
	int storeId;

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	

}
