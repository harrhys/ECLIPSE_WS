package com.farbig.persistence;

import com.farbig.persistence.hibernate.JDBCTxnManagedHandler;

public class DataHandlerFactory {
	
	public static DataHandler getDataHandler()
	{
		DataHandler util = new JDBCTxnManagedHandler();
		
		return util;
	}

}
