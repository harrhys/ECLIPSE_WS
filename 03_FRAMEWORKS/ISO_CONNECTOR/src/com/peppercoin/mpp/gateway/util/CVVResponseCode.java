package com.peppercoin.mpp.gateway.util;

import java.util.Map;
import java.util.TreeMap;

public class CVVResponseCode {
	private String respCode;
	private static Map map = new TreeMap();
	public static final CVVResponseCode YES = new CVVResponseCode('Y');
	public static final CVVResponseCode NO = new CVVResponseCode('N');
	public static final CVVResponseCode NOT_CHECKED = new CVVResponseCode('E');
	public static final CVVResponseCode NOT_SUPPORTED = new CVVResponseCode('X');
	public static final CVVResponseCode NOT_GIVEN = new CVVResponseCode('Z');

	private CVVResponseCode(char code) {
		this.respCode = "" + code;
		map.put(this.respCode, this);
	}

	public String toString() {
		return this.respCode;
	}

	public static CVVResponseCode find(char code) {
		CVVResponseCode a = (CVVResponseCode) map.get("" + code);
		return a == null ? NOT_CHECKED : a;
	}
}