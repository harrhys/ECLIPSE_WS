package com.farbig.practice.entity.testcases;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.farbig.practice.entity.relations.Vehicle;
import com.farbig.practice.entity.relations.VehicleId;
import com.farbig.practice.entity.relations.onetoone.BIJTO2OTrip;
import com.farbig.practice.entity.relations.onetoone.UNIJTO2OTrip;
import com.farbig.practice.entity.relations.onetoone.BIJTO2OVehicle;
import com.farbig.practice.entity.relations.onetoone.BIJCO2OTrip;
import com.farbig.practice.entity.relations.onetoone.UNIJCO2OTrip;
import com.farbig.practice.entity.relations.onetoone.BIJCO2OVehicle;
import com.farbig.practice.entity.test.util.EntityUtil;
import com.farbig.practice.persistence.PersistenceHandler;
import com.farbig.practice.persistence.PersistenceHandlerFactory;
import com.farbig.practice.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneToOneTestCases extends EntityUtil {

	@Test
	public void test1_BIO2OJC() {
		
		testCaseName="test1_BIO2OJC";
		
		BIJCO2OVehicle vehicle = new BIJCO2OVehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2OFiesta");
		vehicle.setNumber(getVehicleNumber());

		BIJCO2OTrip trip = new BIJCO2OTrip();
		setEntityInfo(trip);
		trip.setName("O2OUber");
		trip.setStartDate(new Date());
		trip.setEndDate(new Date(System.currentTimeMillis() + MILLI_SECS_PER_DAY));
		trip.setVehicle(vehicle);
		
		vehicle.setTrip(trip);
		
		handler.openSession();
		handler.save(trip);
		handler.closeSession();
		
		Assert.assertNotNull(trip);
		Assert.assertTrue(trip.getId()>0);
		Assert.assertNotNull(trip.getVehicle());
		Assert.assertTrue(trip.getVehicle().getId()>0);
		
		VehicleId id = new VehicleId(vehicle.getId(),vehicle.getNumber());
	
		handler.openSession();
		vehicle = (BIJCO2OVehicle) handler.get(BIJCO2OVehicle.class, id);
		handler.closeSession();
		
		Assert.assertNotNull(vehicle);
		Assert.assertTrue(vehicle.getId()>0);
		Assert.assertNotNull(vehicle.getTrip());
		Assert.assertTrue(vehicle.getTrip().getId()>0);
	}
	
	@Test
	public void test2_BIO2OJT() {
		
		testCaseName="test2_BIO2OJT";
		
		BIJTO2OVehicle vehicle = new BIJTO2OVehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2OJTFiesta");
		vehicle.setNumber(getVehicleNumber());

		BIJTO2OTrip trip = new BIJTO2OTrip();
		setEntityInfo(trip);
		trip.setName("O2OJTUber");
		trip.setStartDate(new Date());
		trip.setEndDate(new Date(System.currentTimeMillis() + MILLI_SECS_PER_DAY));
		trip.setVehicle(vehicle);
		
		vehicle.setTrip(trip);
		
		handler.openSession();
		handler.save(trip);
		handler.closeSession();
		
		Assert.assertNotNull(trip);
		Assert.assertTrue(trip.getId()>0);
		Assert.assertNotNull(trip.getVehicle());
		Assert.assertTrue(trip.getVehicle().getId()>0);
		
		VehicleId id = new VehicleId(vehicle.getId(),vehicle.getNumber());
		
		handler.openSession();
		vehicle = (BIJTO2OVehicle) handler.get(BIJTO2OVehicle.class, id);
		handler.closeSession();
		
		Assert.assertNotNull(vehicle);
		Assert.assertTrue(vehicle.getId()>0);
		Assert.assertNotNull(vehicle.getTrip());
		Assert.assertTrue(vehicle.getTrip().getId()>0);
	}
	
	@Test
	public void test3_UNIO2OJC() {
		
		testCaseName="test3_UNIO2OJC";
		
		Vehicle vehicle = new Vehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2OUNIFiesta");
		vehicle.setNumber(getVehicleNumber());

		UNIJCO2OTrip trip = new UNIJCO2OTrip();
		setEntityInfo(trip);
		trip.setName("O2OUNIUber");
		trip.setStartDate(new Date());
		trip.setEndDate(new Date(System.currentTimeMillis() + MILLI_SECS_PER_DAY));
		trip.setVehicle(vehicle);
		
		handler.openSession();
		handler.save(trip);
		handler.closeSession();
		
		Assert.assertNotNull(trip);
		Assert.assertTrue(trip.getId()>0);
		Assert.assertNotNull(trip.getVehicle());
		Assert.assertTrue(trip.getVehicle().getId()>0);
		
		handler.openSession();
		trip = (UNIJCO2OTrip) handler.get(UNIJCO2OTrip.class, trip.getId());
		handler.closeSession();
		
		Assert.assertNotNull(trip);
		Assert.assertTrue(trip.getId()>0);
		Assert.assertNotNull(trip.getVehicle());
		Assert.assertTrue(trip.getVehicle().getId()>0);
	}
	
	@Test //Throwing WalkingException..need to check later.
	public void test4_UNIO2OJT() {
		
		testCaseName="test4_UNIO2OJT";
		
		Vehicle vehicle = new Vehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2OJTUNIFiesta");
		vehicle.setNumber(getVehicleNumber());

		UNIJTO2OTrip trip = new UNIJTO2OTrip();
		setEntityInfo(trip);
		trip.setName("O2OJTUNIUber");
		trip.setStartDate(new Date());
		trip.setEndDate(new Date(System.currentTimeMillis() + MILLI_SECS_PER_DAY));
		trip.setVehicle(vehicle);
		
		handler.openSession();
		handler.save(trip);
		handler.closeSession();
		
		
		Assert.assertNotNull(trip);
		Assert.assertTrue(trip.getId()>0);
		Assert.assertNotNull(trip.getVehicle());
		Assert.assertTrue(trip.getVehicle().getId()>0);
		System.out.println("TRIPID..............."+trip.getId());
		System.out.println("vehicleid.............."+trip.getVehicle().getId());
		
		handler.openSession();
		trip = (UNIJTO2OTrip) handler.get(UNIJTO2OTrip.class, trip.getId());
		handler.closeSession();
		
		Assert.assertNotNull(trip);
		Assert.assertTrue(trip.getId()>0);
		
		Assert.assertNotNull(trip.getVehicle());
		Assert.assertTrue(trip.getVehicle().getId()>0);
		System.out.println("TRIPID..............."+trip.getId());
		System.out.println("vehicleid.............."+trip.getVehicle().getId());
	}
	
	static PersistenceHandler handler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("\nStarting OneToOneTestCases");
		handler = PersistenceHandlerFactory.getPersistenceHandler(TxnMgmtType.HIBERNATE_JDBC);
		//handler.openConnection();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//handler.closeConnection();
		System.out.println("Completed OneToOneTestCases\n");
	}

	@Before
	public void setUp() throws Exception {

		System.out.println("\nStarting the Testcase");
	}

	@After
	public void tearDown() throws Exception {

		System.out.println("Completed the Testcase\n");
	}
}
