package com.farbig.practice.entity.testcases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.farbig.practice.entity.relations.Vehicle;
import com.farbig.practice.entity.relations.onetomany.BIJTO2MTrip;
import com.farbig.practice.entity.relations.onetomany.UNIJTO2MTrip;
import com.farbig.practice.entity.relations.onetomany.BIJTO2MVehicle;
import com.farbig.practice.entity.relations.onetomany.BIJCO2MTrip;
import com.farbig.practice.entity.relations.onetomany.UNIJCO2MTrip;
import com.farbig.practice.entity.relations.onetomany.BIJCO2MVehicle;
import com.farbig.practice.entity.test.util.EntityUtil;
import com.farbig.practice.persistence.PersistenceHandler;
import com.farbig.practice.persistence.PersistenceHandlerFactory;
import com.farbig.practice.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneToManyTestCases extends EntityUtil {

	@Test
	public void test1_BIO2MJC() {
		
		testCaseName="test1_BIO2MJC";

		BIJCO2MVehicle vehicle = new BIJCO2MVehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2MFiesta");
		vehicle.setNumber(getVehicleNumber());

		BIJCO2MTrip trip1 = new BIJCO2MTrip();
		setEntityInfo(trip1);
		trip1.setName("O2MUber");
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));

		BIJCO2MTrip trip2 = new BIJCO2MTrip();
		setEntityInfo(trip2);
		trip2.setName("O2MOla");
		trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));
		trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * MILLI_SECS_PER_DAY));
		
		trip1.setVehicle(vehicle);
		trip2.setVehicle(vehicle);

		vehicle.addTrip(trip1);
		vehicle.addTrip(trip2);

		List<Object> entities = new ArrayList<Object>();
		entities.add(trip1);
		entities.add(trip2);

		handler.openSession();
		handler.save(entities);
		handler.closeSession();

		Assert.assertNotNull(trip1);
		Assert.assertTrue(trip1.getId() > 0);
		Assert.assertNotNull(trip1.getVehicle());
		Assert.assertTrue(trip1.getVehicle().getId() > 0);
		Assert.assertNotNull(trip2);
		Assert.assertTrue(trip2.getId() > 0);
		Assert.assertNotNull(trip2.getVehicle());
		Assert.assertTrue(trip2.getVehicle().getId() > 0);
	}

	@Test
	public void test2_BIO2MJT() {
		
		testCaseName="test2_BIO2MJT";

		BIJTO2MVehicle vehicle = new BIJTO2MVehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2MJTFiesta");
		vehicle.setNumber(getVehicleNumber());

		BIJTO2MTrip trip1 = new BIJTO2MTrip();
		setEntityInfo(trip1);
		trip1.setName("O2MJTUber");
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));

		BIJTO2MTrip trip2 = new BIJTO2MTrip();
		setEntityInfo(trip2);
		trip2.setName("O2MJTOla");
		trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));
		trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * MILLI_SECS_PER_DAY));
		
		trip1.setVehicle(vehicle);
		trip2.setVehicle(vehicle);

		vehicle.addTrip(trip1);
		vehicle.addTrip(trip2);
		
		List<Object> entities = new ArrayList<Object>();
		entities.add(trip1);
		entities.add(trip2);

		handler.openSession();
		handler.save(entities);
		handler.closeSession();

		Assert.assertNotNull(trip1);
		Assert.assertTrue(trip1.getId() > 0);
		Assert.assertNotNull(trip1.getVehicle());
		Assert.assertTrue(trip1.getVehicle().getId() > 0);
		Assert.assertNotNull(trip2);
		Assert.assertTrue(trip2.getId() > 0);
		Assert.assertNotNull(trip2.getVehicle());
		Assert.assertTrue(trip2.getVehicle().getId() > 0);
	}
	
	@Test
	public void test3_UNIO2MJC() {
		
		testCaseName="test3_UNIO2MJC";

		Vehicle vehicle = new Vehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2MUNIFiesta");
		vehicle.setNumber(getVehicleNumber());

		UNIJCO2MTrip trip1 = new UNIJCO2MTrip();
		setEntityInfo(trip1);
		trip1.setName("O2MUNIUber");
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));

		UNIJCO2MTrip trip2 = new UNIJCO2MTrip();
		setEntityInfo(trip2);
		trip2.setName("O2MUNIOla");
		trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));
		trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * MILLI_SECS_PER_DAY));
		
		trip1.setVehicle(vehicle);
		trip2.setVehicle(vehicle);

		List<Object> entities = new ArrayList<Object>();
		entities.add(trip1);
		entities.add(trip2);

		handler.openSession();
		handler.save(entities);
		handler.closeSession();

		Assert.assertNotNull(trip1);
		Assert.assertTrue(trip1.getId() > 0);
		Assert.assertNotNull(trip1.getVehicle());
		Assert.assertTrue(trip1.getVehicle().getId() > 0);
		Assert.assertNotNull(trip2);
		Assert.assertTrue(trip2.getId() > 0);
		Assert.assertNotNull(trip2.getVehicle());
		Assert.assertTrue(trip2.getVehicle().getId() > 0);
	}

	@Test
	public void test4_UNIO2MJT() {
		
		testCaseName="test4_UNIO2MJT";

		Vehicle vehicle = new Vehicle();
		setEntityInfo(vehicle);
		vehicle.setName("O2MJTUNIFiesta");
		vehicle.setNumber(getVehicleNumber());

		UNIJTO2MTrip trip1 = new UNIJTO2MTrip();
		setEntityInfo(trip1);
		trip1.setName("O2MJTUNIUber");
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));

		UNIJTO2MTrip trip2 = new UNIJTO2MTrip();
		setEntityInfo(trip2);
		trip2.setName("O2MJUNITOla");
		trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));
		trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * MILLI_SECS_PER_DAY));
		
		trip1.setVehicle(vehicle);
		trip2.setVehicle(vehicle);

		List<Object> entities = new ArrayList<Object>();
		entities.add(trip1);
		entities.add(trip2);

		handler.openSession();
		handler.save(entities);
		handler.closeSession();

		Assert.assertNotNull(trip1);
		Assert.assertTrue(trip1.getId() > 0);
		Assert.assertNotNull(trip1.getVehicle());
		Assert.assertTrue(trip1.getVehicle().getId() > 0);
		Assert.assertNotNull(trip2);
		Assert.assertTrue(trip2.getId() > 0);
		Assert.assertNotNull(trip2.getVehicle());
		Assert.assertTrue(trip2.getVehicle().getId() > 0);
	}
	
	static PersistenceHandler handler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("\nStarting OneToManyTestCases");
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
