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

@Entity
@Table(name = "TRIP_JT")
@DiscriminatorValue("BI_JT_M2M")
public class BIJTM2MTrip extends JTTrip {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)

	@JoinTable(name = "VEHICLE_TRIP", joinColumns = @JoinColumn(name = "TRIP_ID", unique = false, referencedColumnName = "ID"), inverseJoinColumns = {
			@JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID"),
			@JoinColumn(name = "VEHICLE_NUMBER", referencedColumnName = "REG_NUMBER") })

	private List<BIJTM2MVehicle> vehicles;

	public List<BIJTM2MVehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<BIJTM2MVehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public void addVehicle(BIJTM2MVehicle vehicle) {
		if (vehicles == null)
			vehicles = new ArrayList<BIJTM2MVehicle>();
		vehicles.add(vehicle);
	}

	public void removeVehicle(BIJTM2MVehicle vehicle) {
		if (vehicles != null)
			vehicles.remove(vehicle);
	}
}
