package com.peppercoin.common.query;

import com.peppercoin.common.exception.ExceptionKey;
import com.peppercoin.common.exception.PpcnException;
import com.peppercoin.common.exception.PpcnException.BundleID;

public class QueryException extends PpcnException {
	private static BundleID myBundle = new BundleID("com.peppercoin.common.query.QueryResources");

	public QueryException(ExceptionKey key, Object[] args) {
		super(myBundle, key, args);
	}

	public QueryException(ExceptionKey key, String arg1, String arg2, String arg3) {
		super(myBundle, key, arg1, arg2, arg3);
	}

	public QueryException(ExceptionKey key, String arg1, String arg2) {
		super(myBundle, key, arg1, arg2);
	}

	public QueryException(ExceptionKey key, String arg) {
		super(myBundle, key, arg);
	}

	public QueryException(ExceptionKey key) {
		super(myBundle, key);
	}
}