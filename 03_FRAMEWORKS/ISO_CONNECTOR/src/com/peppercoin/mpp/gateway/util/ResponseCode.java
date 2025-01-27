package com.peppercoin.mpp.gateway.util;

import com.peppercoin.common.exception.PpcnError;

public class ResponseCode {
	private int respCode;
	private String respMessage;
	public static final ResponseCode OK = new ResponseCode(0, "Approved");
	public static final ResponseCode INTERNAL_ERROR = new ResponseCode(-1, "Internal Error");
	public static final ResponseCode DECLINED = new ResponseCode(100, "Declined");
	public static final ResponseCode INSUFFICIENT_FUNDS = new ResponseCode(101, "Insufficient Funds");
	public static final ResponseCode EXPIRED_CARD = new ResponseCode(102, "Expired Card");
	public static final ResponseCode REFERRAL = new ResponseCode(103, "Referral");
	public static final ResponseCode PICK_UP = new ResponseCode(108, "Pick Up");
	public static final ResponseCode INVALID_MERCHANT = new ResponseCode(1, "Login failed");
	public static final ResponseCode DUPLICATE_REQUESTID = new ResponseCode(4, "Duplicate REQUESTID");

	private ResponseCode(int code, String msg) {
		this.respCode = code;
		this.respMessage = msg;
	}

	public int getRespCode() {
		return this.respCode;
	}

	public String getRespMessage() {
		return this.respMessage;
	}

	public String toString() {
		return "" + this.respCode;
	}

	public static ResponseCode find(int code) {
		switch (code) {
			case -1 :
				return INTERNAL_ERROR;
			case 0 :
				return OK;
			case 1 :
				return INVALID_MERCHANT;
			case 4 :
				return DUPLICATE_REQUESTID;
			case 100 :
				return DECLINED;
			case 101 :
				return INSUFFICIENT_FUNDS;
			case 102 :
				return EXPIRED_CARD;
			case 103 :
				return REFERRAL;
			case 108 :
				return PICK_UP;
			default :
				throw new PpcnError("Can't find response code for code #" + code);
		}
	}
}