package com.farbig.mapping.onetomany;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;

import com.farbig.util.DataHandler;
import com.farbig.util.DataHandlerFactory;

public class OneToManyTest {

	public static void main(String[] args) {

		

		DataHandler dataHandler = DataHandlerFactory.getDataHandler("JDBC");

		testJoinTable(dataHandler);
		//testJoinColum(util);
		//testMerge(util);

		dataHandler.closeConnection();

	}

	public static void testCriteria() {

		DataHandler dataHandler = DataHandlerFactory.getDataHandler("JDBC");
		/*VehicleOne v = (VehicleOne) (dataHandler.getSession()
				.createCriteria(VehicleOne.class).list().iterator().next());
		System.out.println(v.getVehicleName());*/
	}

	public static void testMerge(DataHandler dataHandler) {

		VehicleOne v1 = new VehicleOne();
		VehicleOne v2 = new VehicleOne();

		dataHandler.load(v1, 1);

		System.out.println("INITIAL SESSION SIZE:" + v1.getTrips().size());
		for (Iterator iterator = v1.getTrips().iterator(); iterator.hasNext();) {
			TripOne trip = (TripOne) iterator.next();
			System.out.println("INITIAL SESSION - " + trip.getTripId() + " -- "
					+ trip.getTripName());
		}
		dataHandler.closeSession();
		dataHandler.closeConnection();

		dataHandler.load(v2, 1);

		TripOne TT = v1.getTrips().get(v1.getTrips().size() - 1);
		v1.removeTrip(TT);
		/*
		 * TT = new TripOne(); TT.setTripName("New Trip"); VV_1.addTrip(TT);
		 */
		v1 = (VehicleOne) dataHandler.update(v1);
		dataHandler.commit();
		dataHandler.closeSession();

		System.out.println("UPDATED SESSION SIZE:" + v1.getTrips().size());
		for (Iterator iterator = v1.getTrips().iterator(); iterator.hasNext();) {
			TripOne trip = (TripOne) iterator.next();
			System.out.println("UPDATED SESSION - " + trip.getTripId() + " -- "
					+ trip.getTripName());
		}

		dataHandler.openSession();
		TT = v2.getTrips().get(v2.getTrips().size() - 1);
		TT.setTripName(TT.getTripId() + "");

		v1 = (VehicleOne) dataHandler.mergeAndCommit(v2);

		System.out.println("MERGED SIZE:" + v1.getTrips().size());
		for (Iterator iterator = v1.getTrips().iterator(); iterator.hasNext();) {
			TripOne trip = (TripOne) iterator.next();
			System.out.println("MERGED - " + trip.getTripId() + " -- "
					+ trip.getTripName());
		}
		dataHandler.closeSession();

		dataHandler.openSession();
		dataHandler.load(v2, 1);

		System.out.println("FINAL SIZE:" + v2.getTrips().size());
		for (Iterator iterator = v2.getTrips().iterator(); iterator.hasNext();) {
			TripOne trip = (TripOne) iterator.next();
			System.out.println("FINAL - " + trip.getTripId() + " -- "
					+ trip.getTripName());
		}
	}

	public static void testJoinTable(DataHandler dataHandler) {

		for (int i = 1; i <= 1; i++) {

			VehicleOne v = new VehicleOne();

			v.setVehicleName("Vehicle-" + i);

			TripOne trip1 = new TripOne();

			trip1.setTripName("Trip-1");
			trip1.setStartDate(new Date());
			trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * 24 * 60
					* 60 * 1000));

			TripOne trip2 = new TripOne();

			trip2.setTripName("Trip-2");
			trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * 24
					* 60 * 60 * 1000));
			trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * 24 * 60
					* 60 * 1000));
			trip1.setVehicle(v);
			trip2.setVehicle(v);

			v.addTrip(trip1);
			v.addTrip(trip2);

			dataHandler.save(v);
			dataHandler.commit();
			dataHandler.start();

		}
		dataHandler.commit();
	}

	public static void testJoinColum(DataHandler dataHandler) {

		VehicleTwo v = new VehicleTwo();

		v.setVehicleName("Fiesta");

		TripTwo trip1 = new TripTwo();

		trip1.setTripName("Uber");
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60
				* 1000));

		TripTwo trip2 = new TripTwo();

		trip2.setTripName("Ola");
		trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * 24 * 60
				* 60 * 1000));
		trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * 24 * 60 * 60
				* 1000));
		trip1.setVehicle(v);
		trip2.setVehicle(v);

		v.addTrip(trip1);
		v.addTrip(trip2);

		dataHandler.save(v);
	}

}
