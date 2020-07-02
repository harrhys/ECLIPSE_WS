package com.farbig.entity.manytomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity(name="VEHICLE")
public class VehicleOne {
	
	@Id
	@Column(name = "VEHICLE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int vehicleId;
	
	@Column(name = "VEHICLE_NAME")
	private String vehicleName;
	
	@ManyToMany(mappedBy="vehicles")
	private List<TripOne> trips = new ArrayList<TripOne>();
	
	public List<TripOne> getTrips() {
		return trips;
	}

	public void addTrip(TripOne tripOne) {
		this.trips.add(tripOne);
	}
	
	public void removeTrip(TripOne tripOne) {
		this.trips.remove(tripOne);
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}


}
