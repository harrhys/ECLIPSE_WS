package com.farbig.practice.entity.relations.onetomany;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.JTTrip;
import com.farbig.practice.entity.relations.Vehicle;


@Entity
@Table(name="TRIP_JT")
@DiscriminatorValue("UNI_JT_O2M")
public class UNIJTO2MTrip extends JTTrip {
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "VEHICLE_TRIP", 
	joinColumns = @JoinColumn(name = "TRIP_ID", unique = false, referencedColumnName = "ID"), 
	inverseJoinColumns = {@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID"),
	@JoinColumn(name = "VEHICLE_NUMBER",referencedColumnName = "REG_NUMBER")})

	private Vehicle vehicle;

	
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
