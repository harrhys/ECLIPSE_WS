package com.peppercoin.common.query.impl;

import java.util.ArrayList;

public class HQLUtil {
	public static String ALIAS = "poClass";

	public static String prepareHQL(String qClass, String alias, Object[] props, Object[] orderByProps) {
		StringBuffer hsql = new StringBuffer();
		hsql.append("from ");
		hsql.append(qClass + " as " + alias);
		hsql.append(" where ");
		boolean bValueFound = false;

		int i;
		for (i = 0; i < props.length; ++i) {
			hsql.append(" " + alias + "." + props[i] + " = ? ");
			if (i != props.length - 1) {
				hsql.append("and");
			}

			hsql.append(" ");
		}

		if (orderByProps != null) {
			hsql.append(" order by ");

			for (i = 0; i < orderByProps.length; ++i) {
				if (orderByProps[i] != null) {
					System.out.println(" QueryMangerImpl.prepareHSQL(): orderByProps=" + orderByProps[i].toString());
					hsql.append(alias + "." + orderByProps[i] + " asc");
					if (i != orderByProps.length - 1) {
						hsql.append(", ");
					}
				}
			}
		}

		System.out.println(" QueryMangerImpl.prepareHSQL(): hsql = {" + hsql.toString() + "}");
		return hsql.toString();
	}

	public static String[] generateAliases(Class[] classes) {
		ArrayList aliases = new ArrayList();

		for (int i = 1; i < classes.length + 1; ++i) {
			aliases.add(new String("poClass" + i));
		}

		return (String[]) aliases.toArray();
	}
}