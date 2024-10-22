package com.peppercoin.mpp.gateway.util;

import java.util.Map;
import java.util.TreeMap;

public class AVSResponseCode {
	private String respCode;
	private static Map map = new TreeMap();
	public static final AVSResponseCode ZIP_CODE_WRONG = new AVSResponseCode('P');
	public static final AVSResponseCode STREET_WRONG = new AVSResponseCode('S');
	public static final AVSResponseCode STREET_ZIP_WRONG = new AVSResponseCode('N');
	public static final AVSResponseCode OK = new AVSResponseCode('Y');
	public static final AVSResponseCode NOT_CHECKED = new AVSResponseCode('E');
	public static final AVSResponseCode NOT_GIVEN = new AVSResponseCode('Z');
	public static final AVSResponseCode NOT_SUPPORTED = new AVSResponseCode('X');
	public static final AVSResponseCode INTL_ZIP_CODE_WRONG = new AVSResponseCode('Q');
	public static final AVSResponseCode INTL_STREET_WRONG = new AVSResponseCode('T');
	public static final AVSResponseCode INTL_STREET_ZIP_WRONG = new AVSResponseCode('O');
	public static final AVSResponseCode INTL_OK = new AVSResponseCode('V');

	private AVSResponseCode(char code) {
		this.respCode = "" + code;
		map.put(this.respCode, this);
	}

	public String toString() {
		return this.respCode;
	}

	public static AVSResponseCode find(char code) {
		AVSResponseCode a = (AVSResponseCode) map.get("" + code);
		return a == null ? NOT_CHECKED : a;
	}
}