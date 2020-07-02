package com.farbig.entity.manytomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity(name="VEHICLE_TWO_MANY")
public class VehicleTwo {
	
	@Id
	@Column(name = "VEHICLE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int vehicleId;
	
	@Column(name = "VEHICLE_NAME")
	private String vehicleName;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="vehicle")
	private List<TripTwo> triptwos = new ArrayList<TripTwo>();
	
	public List<TripTwo> getTrips() {
		return triptwos;
	}

	public void addTrip(TripTwo tripOne) {
		this.triptwos.add(tripOne);
		//trip.setVehicle(this);
	}
	
	public void removeTrip(TripTwo tripOne) {
		this.triptwos.remove(tripOne);
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
