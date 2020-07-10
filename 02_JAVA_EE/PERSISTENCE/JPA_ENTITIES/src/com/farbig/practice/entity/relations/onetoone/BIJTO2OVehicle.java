package com.farbig.practice.entity.relations.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.Vehicle;

@Entity
@Table(name="VEHICLE")
@DiscriminatorValue("BI_JT_O2O")
public class BIJTO2OVehicle extends Vehicle {
	
	@OneToOne(mappedBy="vehicle",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private BIJTO2OTrip trip;
	
	public BIJTO2OTrip getTrip() {
		return trip;
	}

	public void setTrip(BIJTO2OTrip trip) {
		this.trip = trip;
	}
}
