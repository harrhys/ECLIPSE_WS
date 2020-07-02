package com.farbig.entity.onetoone;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity(name="VEHICLE_FOUR")
public class VehicleFour {
	
	@Id
	@Column(name = "VEHICLE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int vehicleId;
	
	@Column(name = "VEHICLE_NAME")
	@Basic(fetch=FetchType.LAZY)
	private String vehicleName;
	
	@OneToOne(mappedBy="vehicle")
	@Basic(fetch=FetchType.LAZY)
	private TripFour trip = new TripFour();
	
	public TripFour getTrips() {
		return trip;
	}

	public void setTrip(TripFour trip) {
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
