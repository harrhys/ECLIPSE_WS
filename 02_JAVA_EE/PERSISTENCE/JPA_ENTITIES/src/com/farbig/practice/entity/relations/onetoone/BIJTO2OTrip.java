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

@Entity
@Table(name="TRIP_JT")
@DiscriminatorValue("BI_JT_O2O")
public class BIJTO2OTrip extends JTTrip {
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name = "VEHICLE_TRIP", joinColumns = @JoinColumn(name = "TRIP_ID",
	referencedColumnName = "ID"), inverseJoinColumns = {@JoinColumn(name =
	"VEHICLE_ID", referencedColumnName = "ID"),
	@JoinColumn(name = "VEHICLE_NUMBER",referencedColumnName = "REG_NUMBER")})
	private BIJTO2OVehicle vehicle ;

	public BIJTO2OVehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(BIJTO2OVehicle vehicle) {
		this.vehicle = vehicle;
	}
}
