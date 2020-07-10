package com.farbig.entity.onetomany;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name="TRIP_ONE")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@org.hibernate.annotations.Entity(selectBeforeUpdate=true)
@Cacheable(value=true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TripOne{
	
	@Id
	@Column(name = "TRIP_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_seq")
	@SequenceGenerator(name = "trip_seq", sequenceName = "tt_seq",allocationSize=1)
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
	@JoinTable(name="VEHICLE_TRIP_ONE",joinColumns=@JoinColumn(name="TRIP_ID"),inverseJoinColumns=@JoinColumn(name="VEHICLE_ID"))
	private VehicleOne vehicle;

	public int getTripId() {
		return tripId;
	}

	public VehicleOne getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleOne vehicleOne) {
		this.vehicle = vehicleOne;
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
