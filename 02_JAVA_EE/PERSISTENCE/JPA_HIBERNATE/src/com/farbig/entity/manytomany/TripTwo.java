package com.farbig.entity.manytomany;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="TRIP_TWO_MANY")
public class TripTwo {
	
	@Id
	@Column(name = "TRIP_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int tripId;
	
	@Column(name = "TRIP_NAME")
	private String tripName;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name="VEHICLE_ID",nullable=false)
	private VehicleTwo vehicle;

	public int getTripId() {
		return tripId;
	}

	public VehicleTwo getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleTwo vehicleTwo) {
		this.vehicle = vehicleTwo;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
