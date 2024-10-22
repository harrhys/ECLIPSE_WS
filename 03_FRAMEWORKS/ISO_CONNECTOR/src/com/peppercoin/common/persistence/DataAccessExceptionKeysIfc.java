package com.peppercoin.common.persistence;

import com.peppercoin.common.exception.ExceptionKey;

public interface DataAccessExceptionKeysIfc {
	ExceptionKey ERROR_JNDI = new ExceptionKey("exception.dataaccess.jndi");
	ExceptionKey ERROR_HIBERNATE = new ExceptionKey("exception.dataaccess.hibernate");
	ExceptionKey ERROR_ACCESSOR = new ExceptionKey("exception.dataaccess.accessor");
}