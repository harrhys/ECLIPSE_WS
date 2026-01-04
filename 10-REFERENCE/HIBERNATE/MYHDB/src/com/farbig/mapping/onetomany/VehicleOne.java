package com.farbig.mapping.onetomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "VEHICLE_ONE")
@org.hibernate.annotations.Entity(selectBeforeUpdate = true)
/*@Cacheable(value = true)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
public class VehicleOne {

	@Id
	@Column(name = "VEHICLE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq")
	@SequenceGenerator(name = "vehicle_seq", sequenceName = "vv_seq", allocationSize = 1)
	private int vehicleId;

	@Column(name = "VEHICLE_NAME")
	private String vehicleName;

	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<TripOne> trips = new ArrayList<TripOne>();

	public List<TripOne> getTrips() {
		return trips;
	}

	public void addTrip(TripOne tripOne) {
		this.trips.add(tripOne);
		tripOne.setVehicle(this);
	}

	public void removeTrip(TripOne tripOne) {
		this.trips.remove(tripOne);
		tripOne.setVehicle(this);
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
