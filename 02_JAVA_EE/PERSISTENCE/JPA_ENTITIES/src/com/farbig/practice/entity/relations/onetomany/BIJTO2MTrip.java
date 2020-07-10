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


@Entity
@Table(name="TRIP_JT")
@DiscriminatorValue("BI_JT_O2M")
public class BIJTO2MTrip extends JTTrip {
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	
	  @JoinTable(name = "VEHICLE_TRIP", joinColumns = @JoinColumn(name = "TRIP_ID",
	  unique = false, referencedColumnName = "ID"), inverseJoinColumns =
	  {@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID"),
	  @JoinColumn(name = "VEHICLE_NUMBER",referencedColumnName = "REG_NUMBER")})
	 

	private BIJTO2MVehicle vehicle;

	
	public BIJTO2MVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(BIJTO2MVehicle vehicle) {
		this.vehicle = vehicle;
	}
}
