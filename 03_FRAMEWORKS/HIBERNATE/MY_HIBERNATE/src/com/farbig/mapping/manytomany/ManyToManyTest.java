package com.farbig.mapping.manytomany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.farbig.util.DataHandler;
import com.farbig.util.DataHandlerFactory;

public class ManyToManyTest {

	public static void main(String[] args) {
		// new mapping table is created
		joinTable();

		// new
		joinColumn();

		System.exit(0);

	}

	public static void joinTable() {

		VehicleOne v1 = new VehicleOne();

		v1.setVehicleName("Fiesta");

		VehicleOne v2 = new VehicleOne();

		v2.setVehicleName("HondaCity");

		TripOne t1 = new TripOne();

		t1.setTripName("Uber");
		t1.setStartDate(new Date());
		t1.setEndDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60
				* 1000));

		TripOne t2 = new TripOne();

		t2.setTripName("Ola");
		t2.setStartDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60
				* 1000));
		t2.setEndDate(new Date(System.currentTimeMillis() + 4 * 24 * 60 * 60
				* 1000));

		v1.addTrip(t1);
		v2.addTrip(t2);
		v2.addTrip(t1);
		
		t1.addVehicle(v1);
		t1.addVehicle(v2);
		t2.addVehicle(v2);

		List domainObjects = new ArrayList();
		domainObjects.add(v1);
		domainObjects.add(v2);
		domainObjects.add(t1);
		domainObjects.add(t2);
		
		
		DataHandler dataHandler = DataHandlerFactory.getDataHandler("JDBC");
		dataHandler.saveAndCommit(domainObjects);

	}

	public static void joinColumn() {/*
									 * VehicleTwo v = new VehicleTwo();
									 * 
									 * v.setVehicleName("Fiesta");
									 * 
									 * TripOne trip1 = new TripOne();
									 * 
									 * trip1.setTripName("Uber");
									 * trip1.setStartDate(new Date());
									 * trip1.setEndDate(new
									 * Date(System.currentTimeMillis() + 2 * 24
									 * * 60 * 60 1000));
									 * 
									 * TripOne trip2 = new TripOne();
									 * 
									 * trip2.setTripName("Ola");
									 * trip2.setStartDate(new
									 * Date(System.currentTimeMillis() + 2 * 24
									 * * 60 60 * 1000)); trip2.setEndDate(new
									 * Date(System.currentTimeMillis() + 4 * 24
									 * * 60 * 60 1000)); trip1.setVehicle(v);
									 * trip2.setVehicle(v);
									 * 
									 * v.addTrip(trip1); v.addTrip(trip2);
									 * 
									 * DataHandler.save(v);
									 */
	}

}
