package com.farbig.mapping.manytomany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name="TRIP")
public class TripOne {
	
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
	
	@ManyToMany
	@JoinTable(name="VEHICLE_TRIP_MAP",joinColumns=@JoinColumn(name="TRIP_ID"),inverseJoinColumns=@JoinColumn(name="VEHICLE_ID"))
	private List<VehicleOne> vehicles = new ArrayList<VehicleOne>();

	public int getTripId() {
		return tripId;
	}

	public void addVehicle(VehicleOne vehicle) {
		 vehicles.add(vehicle);
	}

	public void removeVehicle(VehicleOne vehicle) {
		 vehicles.remove(vehicle);
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
