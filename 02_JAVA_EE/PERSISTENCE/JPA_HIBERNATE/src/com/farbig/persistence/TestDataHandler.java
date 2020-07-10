package com.farbig.persistence;

import com.farbig.entity.collections.CompositeId;
import com.farbig.entity.collections.CompositeIdStudent;
import com.farbig.persistence.hibernate.JDBCTxnManagedHandler;
import com.farbig.persistence.hibernate.JDBCUserManagedHandler;
import com.farbig.persistence.jpa.JPAHandler;

public class TestDataHandler {

	public static void main(String[] args) {

		//testJDBCTTxnManagedHandler();
		
		//testJDBCUserManagedHandler();
		
		testJPAHandler();

	}

	public static void testJDBCTTxnManagedHandler() {

		JDBCTxnManagedHandler handler = new JDBCTxnManagedHandler();
		handler.openSession();
		handler.start();

		CompositeIdStudent tt = new CompositeIdStudent();
		
		try {
			CompositeIdStudent t = (CompositeIdStudent) handler.save(tt);
			handler.commit();
		} catch (Exception e) {
			e.printStackTrace();
			handler.rollback();
		} finally {
			handler.closeSession();
			handler.closeConnection();
		}

	}

	public static void testJDBCUserManagedHandler() {

		JDBCUserManagedHandler handler = new JDBCUserManagedHandler();
		handler.openSession();
		handler.start();

		CompositeIdStudent tt = new CompositeIdStudent();
	

		try {
			CompositeIdStudent t = (CompositeIdStudent) handler.save(tt);
			handler.commit();
		} catch (Exception e) {
			e.printStackTrace();
			handler.rollback();
		} finally {
			handler.closeSession();
			handler.closeConnection();
		}

	}
	
	public static void testJPAHandler() {

		JPAHandler handler = new JPAHandler();
		handler.openSession();
		handler.start();

		CompositeIdStudent student = new CompositeIdStudent();
		student.setRollNumber(101);
		student.setBatch(2000);
		student.setName("Balaji");
		student.setBranch("CSE");

		try {
			CompositeIdStudent t = (CompositeIdStudent) handler.save(student);
			handler.commit();
		} catch (Exception e) {
			e.printStackTrace();
			handler.rollback();
		} finally {
			handler.closeSession();
			handler.closeConnection();
		}

	}

}
