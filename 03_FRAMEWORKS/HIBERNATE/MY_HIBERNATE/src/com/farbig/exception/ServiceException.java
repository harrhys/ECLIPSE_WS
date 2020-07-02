package com.farbig.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException()
	{
		super();
	}
	
	public ServiceException(String msg)
	{
		super();
		this.errorMsg = msg;
	}
	
	public ServiceException(Integer code, String msg)
	{
		super();
		this.errorCode = code;
		this.errorMsg = msg;
	}

	private String errorMsg;

	private Integer errorCode;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
