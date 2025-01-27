package com.peppercoin.common.query.impl;

import com.peppercoin.common.query.QueryResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class QueryResultsImpl implements QueryResultSet {
	List results = null;
	Iterator iter = null;
	private int type = 0;

	public QueryResultsImpl(List results) {
		this.results = results;
	}

	public Collection getRows() {
		return this.results;
	}

	public boolean hasMore() {
		if (this.iter == null) {
			this.iter = this.results.iterator();
		}

		return this.iter.hasNext();
	}

	public boolean next() {
		if (this.iter == null) {
			this.iter = this.results.iterator();
		}

		return this.iter.next() != null;
	}

	public Object get() {
		if (this.iter == null) {
			this.iter = this.results.iterator();
		}

		return this.iter.next();
	}

	public int size() {
		return this.results.size();
	}

	public boolean isScrollable() {
		return false;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}
}