package com.farbig.util;

public class DataHandlerFactory {

	public static DataHandler getDataHandler(String type) {

		DataHandler util = null;

		if (type.equals("JDBC"))

			util = new JDBCHandler();

		else if (type.equals("JTA"))

			util = new JTAHandler();
		
		else if (type.equals("JPA"))

			util = new JPAHandler();
		
		else if (type.equals("JPAEJB"))

			util = new JPAEJBHandler();
		
		else
			
			util = new HibernateHandler();

		return util;
	}

}
