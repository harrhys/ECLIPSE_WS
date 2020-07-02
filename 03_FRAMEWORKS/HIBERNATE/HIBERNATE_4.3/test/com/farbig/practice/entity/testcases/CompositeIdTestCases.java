package com.farbig.practice.entity.testcases;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.farbig.practice.entity.composite.id.EmbeddableCompositeEntity;
import com.farbig.practice.entity.composite.id.EmbeddableCompositeId;
import com.farbig.practice.entity.composite.id.SimpleCompositeEntity;
import com.farbig.practice.entity.composite.id.SimpleCompositeId;
import com.farbig.practice.entity.test.util.EntityUtil;
import com.farbig.practice.persistence.PersistenceHandler;
import com.farbig.practice.persistence.PersistenceHandlerFactory;
import com.farbig.practice.persistence.TxnMgmtType;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CompositeIdTestCases extends EntityUtil {

	

	@Test
	public void test1_SimpleCompositeEntityDeletion() {

		SimpleCompositeEntity entity;
		SimpleCompositeId id = new SimpleCompositeId();
		id.setA(1);
		id.setB(2);

		handler.openSession();
		entity = (SimpleCompositeEntity) handler.get(SimpleCompositeEntity.class, id);

		if (entity != null) {

			handler.openSession();
			handler.delete(entity);
			handler.closeSession();

			handler.openSession();
			entity = (SimpleCompositeEntity) handler.get(SimpleCompositeEntity.class, id);
			handler.closeSession();

			Assert.assertNull(entity);
		}
	}

	@Test
	public void test2_SimpleCompositeEntityCreation() {

		SimpleCompositeEntity entity = new SimpleCompositeEntity();
		entity.setA(1);
		entity.setB(2);
		entity.setC("SimpleCompositeEntity");
		setEntityInfo(entity);

		handler.openSession();
		handler.save(entity);
		handler.closeSession();

		Assert.assertNotNull(entity);
	}

	@Test
	public void test3_SimpleCompositeEntityUpdation() {

		SimpleCompositeEntity entity;
		SimpleCompositeId id = new SimpleCompositeId();
		id.setA(1);
		id.setB(2);
		handler.openSession();
		entity = (SimpleCompositeEntity) handler.get(SimpleCompositeEntity.class, id);

		Assert.assertNotNull(entity);

		String c = "UpdatedSimpleCompositeEntity" + Math.random();
		entity.setC(c);
		setUpdatedEntityInfo(entity);
		handler.update(entity);
		handler.closeSession();

		Assert.assertNotNull(entity);

		handler.openSession();
		entity = (SimpleCompositeEntity) handler.get(SimpleCompositeEntity.class, id);
		handler.closeSession();

		Assert.assertNotNull(entity);
		Assert.assertEquals(c, entity.getC());
	}

	@Test
	public void test4_EmbeddableCompositeEntityDeletion() {

		EmbeddableCompositeEntity entity;
		EmbeddableCompositeId id = new EmbeddableCompositeId();
		id.setA(3);
		id.setB(4);

		handler.openSession();
		entity = (EmbeddableCompositeEntity) handler.get(EmbeddableCompositeEntity.class, id);

		if (entity != null) {

			handler.openSession();
			handler.delete(entity);
			handler.closeSession();

			handler.openSession();
			entity = (EmbeddableCompositeEntity) handler.get(EmbeddableCompositeEntity.class, id);
			handler.closeSession();

			Assert.assertNull(entity);
		}
	}

	@Test
	public void test5_EmbeddableCompositeEntityCreation() {

		EmbeddableCompositeEntity entity = new EmbeddableCompositeEntity();
		EmbeddableCompositeId id = new EmbeddableCompositeId();
		id.setA(3);
		id.setB(4);
		entity.setId(id);
		entity.setC("EmbeddableCompositeEntity");
		setEntityInfo(entity);

		handler.openSession();
		handler.save(entity);
		handler.closeSession();

		Assert.assertNotNull(entity);
	}

	@Test
	public void test6_EmbeddableCompositeEntityUpdation() {

		EmbeddableCompositeEntity entity;
		EmbeddableCompositeId id = new EmbeddableCompositeId();
		id.setA(3);
		id.setB(4);
		handler.openSession();
		entity = (EmbeddableCompositeEntity) handler.get(EmbeddableCompositeEntity.class, id);

		Assert.assertNotNull(entity);

		String c = "UpdatedEmbeddableCompositeEntity" + Math.random();
		entity.setC(c);
		setUpdatedEntityInfo(entity);
		handler.update(entity);
		handler.closeSession();

		Assert.assertNotNull(entity);

		handler.openSession();
		entity = (EmbeddableCompositeEntity) handler.get(EmbeddableCompositeEntity.class, id);
		handler.closeSession();

		Assert.assertNotNull(entity);
		Assert.assertEquals(c, entity.getC());
	}
	
	static PersistenceHandler handler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		System.out.println("Starting Composite Test cases");
		handler = PersistenceHandlerFactory.getPersistenceHandler(TxnMgmtType.HIBERNATE_JDBC);
		//handler.openConnection();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// handler.closeConnection();
		System.out.println("Completed Composite Test cases");
	}

	@Before
	public void setUp() throws Exception {

		System.out.println("Starting the Testcase");
	}

	@After
	public void tearDown() throws Exception {

		System.out.println("Completed the Testcase");
	}
}