package com.farbig.practice.entity.relations.manytomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.farbig.practice.entity.relations.JTTrip;
import com.farbig.practice.entity.relations.Vehicle;

@Entity
@Table(name = "TRIP_JT")
@DiscriminatorValue("UNI_JT_M2M")
public class UNIJTM2MTrip extends JTTrip {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	
	  @JoinTable(name = "VEHICLE_TRIP", joinColumns = @JoinColumn(name = "TRIP_ID",
	  unique = false, referencedColumnName = "ID"), inverseJoinColumns =
	  {@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID"),
	  
	  @JoinColumn(name = "VEHICLE_NUMBER",referencedColumnName = "REG_NUMBER")})
	 
	private List<Vehicle> vehicles;

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public void addVehicle(Vehicle vehicle) {
		if(vehicles==null)
			vehicles = new ArrayList<Vehicle>();
		vehicles.add(vehicle);
	}

	public void removeVehicle(Vehicle vehicle) {
		if(vehicles!=null)
		vehicles.remove(vehicle);
	}
}
