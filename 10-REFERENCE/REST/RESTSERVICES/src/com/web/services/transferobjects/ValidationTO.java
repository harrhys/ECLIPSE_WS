package com.web.services.transferobjects;


public class ValidationTO {
	
	private String errorCode;
	
	private String errorMessage;
	
	private String[] params;

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
