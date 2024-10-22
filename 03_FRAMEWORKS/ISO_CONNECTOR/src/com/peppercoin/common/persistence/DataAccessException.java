package com.peppercoin.common.persistence;

import com.peppercoin.common.exception.ExceptionKey;
import com.peppercoin.common.exception.PpcnException;
import com.peppercoin.common.exception.PpcnException.BundleID;

public class DataAccessException extends PpcnException {
	private static BundleID myBundle = new BundleID("com.peppercoin.common.persistence.PersistenceResources");

	public DataAccessException(ExceptionKey key, Object[] args) {
		super(myBundle, key, args);
	}

	public DataAccessException(ExceptionKey key, String arg1, String arg2, String arg3) {
		super(myBundle, key, arg1, arg2, arg3);
	}

	public DataAccessException(ExceptionKey key, String arg1, String arg2) {
		super(myBundle, key, arg1, arg2);
	}

	public DataAccessException(ExceptionKey key, String arg) {
		super(myBundle, key, arg);
	}

	public DataAccessException(ExceptionKey key) {
		super(myBundle, key);
	}
}