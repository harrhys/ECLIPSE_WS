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

@Entity
@Table(name="TRIP_JC")
@DiscriminatorValue("BI_JC_O2M")
public class BIJCO2MTrip extends JCTrip  {
		
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumns({ 
	@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID", nullable = false),
	@JoinColumn(name = "VEHICLE_NUMBER", referencedColumnName = "REG_NUMBER", nullable = false) })
	private BIJCO2MVehicle vehicle;

	public BIJCO2MVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(BIJCO2MVehicle vehicle) {
		this.vehicle = vehicle;
	}
}
