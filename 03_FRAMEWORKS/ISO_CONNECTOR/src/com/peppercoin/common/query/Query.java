package com.peppercoin.common.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query {
	public static int USE_HIBERNATE_SQL = 0;
	public static int USE_NATIVE_SQL = 1;
	public static int ENABLE_SCROLLING = 2;
	List values = null;
	Object[] orderByProps = null;
	List pojoClasses = null;
	List aliases = null;
	String query = null;
	int configFlags;

	public Query() {
		this.configFlags = USE_HIBERNATE_SQL | ENABLE_SCROLLING;
	}

	public Query(Class pojoClass) {
		this.configFlags = USE_HIBERNATE_SQL | ENABLE_SCROLLING;
		if (this.pojoClasses == null) {
			this.pojoClasses = new ArrayList();
		}

		this.pojoClasses.add(pojoClass);
	}

	public Query(Class[] pojoClasses) {
		this.configFlags = USE_HIBERNATE_SQL | ENABLE_SCROLLING;
		this.setClasses(pojoClasses);
	}

	public void setAliases(String[] aliases) {
		this.aliases = Arrays.asList(aliases);
	}

	public String[] getAliases() {
		return (String[]) this.aliases.toArray();
	}

	public Object[] getOrderByParams() {
		return this.orderByProps;
	}

	public void setOrderByParams(Object[] params) {
		this.orderByProps = params;
	}

	public List getValues() {
		return this.values;
	}

	public void addValue(Object value) {
		if (this.values == null) {
			this.values = new ArrayList();
		}

		this.values.add(value);
	}

	public void setValues(Object[] values) {
		if (values != null) {
			this.values = Arrays.asList(values);
		}

	}

	public void setValues(List values) {
		this.values = values;
	}

	public String getQueryDef() {
		return this.query;
	}

	public void setQueryDef(String sql) {
		this.query = sql;
	}

	public void setConfigFlags(int flags) {
		if ((flags & USE_HIBERNATE_SQL) == USE_HIBERNATE_SQL) {
			this.configFlags &= ~USE_NATIVE_SQL;
		}

		if ((flags & USE_NATIVE_SQL) == USE_NATIVE_SQL) {
			this.configFlags &= ~USE_HIBERNATE_SQL;
		}

		this.configFlags |= flags;
	}

	public boolean useScrollableResult() {
		return (this.configFlags & ENABLE_SCROLLING) == ENABLE_SCROLLING;
	}

	public boolean useNativeSQL() {
		return (this.configFlags & USE_NATIVE_SQL) == USE_NATIVE_SQL;
	}

	public Class[] getClasses() {
		return (Class[]) this.pojoClasses.toArray();
	}

	public void setClasses(Class[] pojoClasses) {
		this.pojoClasses = Arrays.asList(pojoClasses);
	}
}