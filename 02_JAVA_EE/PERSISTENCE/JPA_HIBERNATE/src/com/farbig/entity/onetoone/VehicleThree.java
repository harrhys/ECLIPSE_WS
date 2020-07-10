package com.farbig.entity.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity(name="VEHICLE_THREE")
public class VehicleThree {
	
	@Id
	@Column(name = "VEHICLE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int vehicleId;
	
	@Column(name = "VEHICLE_NAME")
	private String vehicleName;
	
	@OneToOne(cascade=CascadeType.ALL,mappedBy="vehicle",fetch=FetchType.LAZY)
	private TripThree trip = new TripThree();
	
	public TripThree getTrip() {
		return trip;
	}

	public void setTrip(TripThree trip) {
		this.trip = trip;
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
