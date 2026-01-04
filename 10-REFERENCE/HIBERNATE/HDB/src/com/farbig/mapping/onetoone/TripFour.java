package com.farbig.mapping.onetoone;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="TRIP_FOUR")
public class TripFour {
	
	@Id
	@Column(name = "TRIP_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int tripId;
	
	@Column(name = "TRIP_NAME")
	private String tripName;
	
	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	@Basic(fetch=FetchType.LAZY,optional=true)
	private Date startDate;
	
	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	@Basic(fetch=FetchType.LAZY)
	private Date endDate;
	
	@OneToOne
	@JoinColumn(name="VEHICLE_ID",nullable=false)
	@Basic(fetch=FetchType.LAZY)
	private VehicleFour vehicle;

	public int getTripId() {
		return tripId;
	}

	public VehicleFour getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleFour vehicleFour) {
		this.vehicle = vehicleFour;
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
