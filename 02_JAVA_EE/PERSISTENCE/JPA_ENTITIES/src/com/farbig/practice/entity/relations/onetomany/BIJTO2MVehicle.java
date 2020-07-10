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
@DiscriminatorValue("BI_JT_O2M")
public class BIJTO2MVehicle extends Vehicle {

	@OneToMany(mappedBy = "vehicle")
	private List<BIJTO2MTrip> trips;

	public List<BIJTO2MTrip> getTrips() {
		return trips;
	}
	
	public void setTrips(List<BIJTO2MTrip> trips) {
		this.trips = trips;
	}

	public void addTrip(BIJTO2MTrip trip) {
		if(this.trips==null)
			trips = new ArrayList<BIJTO2MTrip>();
		this.trips.add(trip);
	}

	public void removeTrip(BIJTO2MTrip trip) {
		if(this.trips!=null)
		this.trips.remove(trip);
	}
}
