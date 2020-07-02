package com.farbig.practice.entity.relations.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.JCTrip;

@Entity
@Table(name = "TRIP_JC")
@DiscriminatorValue("BI_JC_O2O")
public class BIJCO2OTrip extends JCTrip {

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumns({ 
	@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID", nullable = false),
	@JoinColumn(name = "VEHICLE_NUMBER", referencedColumnName = "REG_NUMBER", nullable = false) })
	private BIJCO2OVehicle vehicle;

	public BIJCO2OVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(BIJCO2OVehicle vehicle) {
		this.vehicle = vehicle;
	}
}
