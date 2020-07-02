package com.farbig.practice.entity.relations.manytomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.Vehicle;

@Entity
@Table(name = "VEHICLE")
@DiscriminatorValue("BI_JT_M2M")
public class BIJTM2MVehicle extends Vehicle {

	@ManyToMany(mappedBy = "vehicles")
	private List<BIJTM2MTrip> trips;

	public List<BIJTM2MTrip> getTrips() {
		return trips;
	}

	public void addTrip(BIJTM2MTrip trip) {
		if (trips == null)
			trips = new ArrayList<BIJTM2MTrip>();
		this.trips.add(trip);
	}

	public void removeTrip(BIJTM2MTrip trip) {
		if (trips != null)
			this.trips.remove(trip);
	}

}
