package com.farbig.util;

public class DataHandlerFactory {
	
	public static DataHandler getDataHandler()
	{
		DataHandler util = new CMTHibernateHandler();
		
		return util;
	}

}
