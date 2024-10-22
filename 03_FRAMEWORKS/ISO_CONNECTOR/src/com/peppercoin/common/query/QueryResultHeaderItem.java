package com.peppercoin.common.query;

public class QueryResultHeaderItem {
	String field = null;
	String displayName = null;
	String description = null;

	public QueryResultHeaderItem(String field, String displayName) {
		this.field = field;
		this.displayName = displayName;
	}

	public String getFieldName() {
		return this.field;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public boolean isSortable() {
		return this.field != null;
	}
}