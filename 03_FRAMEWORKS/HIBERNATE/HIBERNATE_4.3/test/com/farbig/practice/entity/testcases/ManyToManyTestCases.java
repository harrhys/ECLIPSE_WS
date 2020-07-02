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
import com.farbig.practice.entity.relations.manytomany.BIJTM2MTrip;
import com.farbig.practice.entity.relations.manytomany.UNIJTM2MTrip;
import com.farbig.practice.entity.relations.manytomany.BIJTM2MVehicle;
import com.farbig.practice.entity.test.util.EntityUtil;
import com.farbig.practice.persistence.PersistenceHandler;
import com.farbig.practice.persistence.PersistenceHandlerFactory;
import com.farbig.practice.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ManyToManyTestCases extends EntityUtil {

	@Test
	public void test1_BIM2MJT() {
		
		testCaseName="test1_BIM2MJT";

		BIJTM2MVehicle vehicle1 = new BIJTM2MVehicle();
		vehicle1.setName("M2MJTFiesta");
		vehicle1.setNumber(getVehicleNumber());
		setEntityInfo(vehicle1);
		
		BIJTM2MVehicle vehicle2 = new BIJTM2MVehicle();
		vehicle2.setName("M2MJTHondaCity");
		vehicle2.setNumber(getVehicleNumber());
		setEntityInfo(vehicle2);

		BIJTM2MTrip trip1 = new BIJTM2MTrip();
		trip1.setName("M2MJTUber");
		setEntityInfo(trip1);
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));

		BIJTM2MTrip trip2 = new BIJTM2MTrip();
		trip2.setName("M2MJTOla");
		setEntityInfo(trip2);
		trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));
		trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * MILLI_SECS_PER_DAY));
		
		trip1.addVehicle(vehicle1);
		trip1.addVehicle(vehicle2);
		
		trip2.addVehicle(vehicle1);
		trip2.addVehicle(vehicle2);
		
		vehicle1.addTrip(trip1);
		vehicle1.addTrip(trip2);
		
		vehicle2.addTrip(trip1);
		vehicle2.addTrip(trip2);
		
		List<Object> entities = new ArrayList<Object>();
		entities.add(trip1);
		entities.add(trip2);
		
		handler.openSession();
		handler.save(entities);
		handler.closeSession();

		Assert.assertNotNull(trip1);
		Assert.assertTrue(trip1.getId() > 0);
		Assert.assertNotNull(trip1.getVehicles());
		Assert.assertTrue(trip1.getVehicles().get(0).getId() > 0);
		Assert.assertTrue(trip1.getVehicles().get(1).getId() > 0);
		Assert.assertNotNull(trip2);
		Assert.assertTrue(trip2.getId() > 0);
		Assert.assertNotNull(trip2.getVehicles());
		Assert.assertTrue(trip2.getVehicles().get(0).getId() > 0);
		Assert.assertTrue(trip2.getVehicles().get(1).getId() > 0);
	}
	
	@Test
	public void test2_UNIM2MJT() {
		
		testCaseName="test2_UNIM2MJT";

		Vehicle vehicle1 = new Vehicle();
		vehicle1.setName("M2MJTUNIFiesta");
		vehicle1.setNumber(getVehicleNumber());
		setEntityInfo(vehicle1);
		
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setName("M2MJTUNIHondaCity");
		vehicle2.setNumber(getVehicleNumber());
		setEntityInfo(vehicle2);

		UNIJTM2MTrip trip1 = new UNIJTM2MTrip();
		trip1.setName("M2MJTUNIUber");
		setEntityInfo(trip1);
		trip1.setStartDate(new Date());
		trip1.setEndDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));

		UNIJTM2MTrip trip2 = new UNIJTM2MTrip();
		trip2.setName("M2MJTUNIOla");
		setEntityInfo(trip2);
		trip2.setStartDate(new Date(System.currentTimeMillis() + 2 * MILLI_SECS_PER_DAY));
		trip2.setEndDate(new Date(System.currentTimeMillis() + 4 * MILLI_SECS_PER_DAY));
		
		trip1.addVehicle(vehicle1);
		trip1.addVehicle(vehicle2);
		
		trip2.addVehicle(vehicle1);
		trip2.addVehicle(vehicle2);
		
		List<Object> entities = new ArrayList<Object>();
		entities.add(trip1);
		entities.add(trip2);
		
		handler.openSession();
		handler.save(entities);
		handler.closeSession();
		
		Assert.assertNotNull(trip1);
		Assert.assertTrue(trip1.getId() > 0);
		Assert.assertNotNull(trip1.getVehicles());
		Assert.assertTrue(trip1.getVehicles().get(0).getId() > 0);
		Assert.assertTrue(trip1.getVehicles().get(1).getId() > 0);
		Assert.assertNotNull(trip2);
		Assert.assertTrue(trip2.getId() > 0);
		Assert.assertNotNull(trip2.getVehicles());
		Assert.assertTrue(trip2.getVehicles().get(0).getId() > 0);
		Assert.assertTrue(trip2.getVehicles().get(1).getId() > 0);
	}
	
	static PersistenceHandler handler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("\nStarting ManyToManyTestCases");
		handler = PersistenceHandlerFactory.getPersistenceHandler(TxnMgmtType.HIBERNATE_JDBC);
		//handler.openConnection();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//handler.closeConnection();
		System.out.println("Completed ManyToManyTestCases\n");
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
