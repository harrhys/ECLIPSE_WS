package com.peppercoin.common.query;

import java.io.Serializable;
import java.util.Collection;

public interface QueryResultSet extends Serializable {
	int SQL_TYPE = 0;
	int HQL_TYPE = 1;

	Collection getRows();

	boolean hasMore();

	boolean next();

	Object get();

	int size();

	boolean isScrollable();

	int getType();

	void setType(int var1);
}