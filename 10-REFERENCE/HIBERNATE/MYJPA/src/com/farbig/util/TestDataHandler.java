package com.farbig.util;



public class TestDataHandler {

	public static void main(String[] args) {
		
		/*java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.slf4j").setLevel(Level.OFF);
		*/
		
		JPAHandler util = new JPAHandler();
		
		/*TestCompositeID id = new TestCompositeID();
		
		id.setA(1);
		id.setB(2);
		
		Test tt = new Test();
		
		tt.setA(1);
		tt.setB(3);
		tt.setC("JPA Test");
		
		util.start();
		Test t = (Test) util.save(tt );
		
		util.commit();
		
		System.out.println(tt.getC());*/
		
		util.closeConnection();

	}

}
