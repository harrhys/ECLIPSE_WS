package com.farbig.util;

public class DataHandlerFactory {

	public static DataHandler getDataHandler(String type) {

		DataHandler util = null;

		if (type.equals("JPA"))

			util = new JPAHandler();

		else if (type.equals("JPAEJB"))

			util = new JPAEJBHandler();

		return util;
	}

}
