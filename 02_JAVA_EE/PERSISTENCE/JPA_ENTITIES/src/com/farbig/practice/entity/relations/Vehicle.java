package com.farbig.practice.entity.relations;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.farbig.practice.entity.BaseEntity;

@Entity
@Table(name = "VEHICLE")
@DiscriminatorColumn(name="VEHICLE_TYPE")
@DiscriminatorValue("UNI_ALL")
@IdClass(VehicleId.class)
public class Vehicle extends BaseEntity {
	
	public Vehicle()
	{}
	
	public Vehicle(String name, String number)
	{
		this.name = name;
		this.number=number;
	}
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "v_seq")
	@SequenceGenerator(name = "v_seq", sequenceName = "vehicle_seq",allocationSize=1,initialValue = 100)
	private int id;
	
	@Id
	@Column(name = "REG_NUMBER", unique = true, nullable = false)
	private String number;
	
	@Column(name = "NAME")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
