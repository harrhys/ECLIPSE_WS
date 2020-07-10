package com.farbig.entity.collections;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Version;


@Entity
@IdClass(CompositeId.class)
public class CompositeIdStudent{
	
	@Id
	private int rollNumber;
	
	@Id
	private int batch;
	
	private String name;
	
	private String branch;
	
	@Version
	private int version;
	
	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}



}
