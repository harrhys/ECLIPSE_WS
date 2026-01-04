package com.farbig.mapping.onetoone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.farbig.util.DataHandler;
import com.farbig.util.DataHandlerFactory;

public class OneToOneTest {

	public static void main(String[] args)  {

		// new mapping table is created
		joinTable();
		

		// new mapping Column is created
		//joinColumn();
		
		
		
		System.exit(0);

	}

	public static void joinTable() {
		DataHandler dataHandler = DataHandlerFactory.getDataHandler();

		VehicleThree v = new VehicleThree();

		v.setVehicleName("Fiesta");

		TripThree trip = new TripThree();

		trip.setTripName("Uber");
		trip.setStartDate(new Date());
		trip.setEndDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60
				* 1000));

		trip.setVehicle(v);

		v.setTrip(trip);

		dataHandler.save(v);
		
		dataHandler.commit();
		
		dataHandler.closeSession();
		
		dataHandler.openSession();
		
		VehicleThree vv = (VehicleThree) dataHandler.get(VehicleThree.class, v.getVehicleId());
		
		vv.getTrip();
		
		dataHandler.closeConnection();
	}

	public static void joinColumn() {
		
		DataHandler dataHandler = DataHandlerFactory.getDataHandler();
		VehicleFour v = new VehicleFour();

		v.setVehicleName("Fiesta");

		TripFour trip = new TripFour();

		trip.setTripName("Uber");
		trip.setStartDate(new Date());
		trip.setEndDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60
				* 1000));

		trip.setVehicle(v);
		
		TripFour trip1 = new TripFour();

		trip1.setTripName("Uber");
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60
				* 1000));

		trip1.setVehicle(v);

		List domainObjects = new ArrayList();
		domainObjects.add(v);
		domainObjects.add(trip);
		domainObjects.add(trip1);

		dataHandler.save(domainObjects);
		
		dataHandler.closeSession();
		
		dataHandler.openSession();
		
		VehicleFour vv = (VehicleFour) dataHandler.get(VehicleFour.class, v.getVehicleId());
		
		System.out.println(vv.getTrips().getTripName());
		
		dataHandler.closeConnection();

	}

}
