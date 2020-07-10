package com.farbig.practice.entity.relations.onetoone;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.Vehicle;


@Entity
@Table(name="VEHICLE")
@DiscriminatorValue("BI_JC_O2O")
public class BIJCO2OVehicle extends Vehicle {
	
	@OneToOne(mappedBy = "vehicle")
	private BIJCO2OTrip trip;
	
	public BIJCO2OTrip getTrip() {
		return trip;
	}

	public void setTrip(BIJCO2OTrip trip) {
		this.trip = trip;
	}
}
