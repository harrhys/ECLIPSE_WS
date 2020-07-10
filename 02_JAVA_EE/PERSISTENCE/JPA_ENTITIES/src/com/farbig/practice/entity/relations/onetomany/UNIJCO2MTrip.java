package com.farbig.practice.entity.relations.onetomany;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.JCTrip;
import com.farbig.practice.entity.relations.Vehicle;

@Entity
@Table(name="TRIP_JC")
@DiscriminatorValue("UNI_JC_O2M")
public class UNIJCO2MTrip extends JCTrip  {
		
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumns({ 
	@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID", nullable = false),
	@JoinColumn(name = "VEHICLE_NUMBER", referencedColumnName = "REG_NUMBER", nullable = false) })
	private Vehicle vehicle;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
