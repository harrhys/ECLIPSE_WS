package com.farbig.practice.entity.relations.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.JTTrip;
import com.farbig.practice.entity.relations.Vehicle;

@Entity
@Table(name="TRIP_JT")
@DiscriminatorValue("UNI_JT_O2O")
public class UNIJTO2OTrip extends JTTrip {
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	
	
	  @JoinTable(name = "VEHICLE_TRIP", joinColumns = @JoinColumn(name = "TRIP_ID",
	  referencedColumnName = "ID"), inverseJoinColumns = {@JoinColumn(name =
	  "VEHICLE_ID", referencedColumnName = "ID"),
	  
	  @JoinColumn(name = "VEHICLE_NUMBER",referencedColumnName = "REG_NUMBER")})
	 
	private Vehicle vehicle ;

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
