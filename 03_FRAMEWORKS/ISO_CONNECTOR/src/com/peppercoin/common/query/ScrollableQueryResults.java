package com.peppercoin.common.query;

import java.util.Collection;

public interface ScrollableQueryResults extends QueryResultSet {
	void beforeFirst();

	void first();

	boolean isFirst();

	boolean isLast();

	int getRowNum();

	Object get(int var1);

	boolean setRowNumber(int var1);

	boolean last();

	boolean previous();

	boolean scroll(int var1);

	boolean wasClipped();

	void add(ScrollableQueryResults var1);

	Collection getRows();

	void setRows(Collection var1);
}