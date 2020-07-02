package com.peppercoin.common.query;

import java.util.List;

public class QueryResultRow {
	List columns;
	String detailId;

	public QueryResultRow(List columns) {
		this.columns = columns;
	}

	public List getColumns() {
		return this.columns;
	}

	public void setDetailId(String id) {
		this.detailId = id;
	}

	public String getDetailId() {
		return this.detailId;
	}
}