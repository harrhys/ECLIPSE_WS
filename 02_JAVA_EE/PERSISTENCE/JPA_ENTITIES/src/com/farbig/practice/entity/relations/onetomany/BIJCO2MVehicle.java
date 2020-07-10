package com.farbig.practice.entity.relations.onetomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.Vehicle;

@Entity
@Table(name = "VEHICLE")
@DiscriminatorValue("BI_JC_O2M")
public class BIJCO2MVehicle extends Vehicle {

	@OneToMany(mappedBy = "vehicle")
	private List<BIJCO2MTrip> trips;

	public List<BIJCO2MTrip> getTrips() {
		return trips;
	}

	public void setTrips(List<BIJCO2MTrip> trips) {
		this.trips = trips;
	}

	public void addTrip(BIJCO2MTrip trip) {
		if (this.trips == null)
			trips = new ArrayList<BIJCO2MTrip>();
		this.trips.add(trip);
	}

	public void removeTrip(BIJCO2MTrip trip) {
		this.trips.remove(trip);
	}
}
