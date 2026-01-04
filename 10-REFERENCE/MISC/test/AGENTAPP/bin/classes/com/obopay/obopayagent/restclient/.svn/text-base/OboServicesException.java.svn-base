package com.obopay.obopayagent.restclient;

/**
 * Extends Exception class to include an error code
 * returned by Mobile Web Services
 * 
 * @author stu
 *
 */
public class OboServicesException extends Exception
{
	/**
	 * Error code returned by service
	 */
	private String mErrorCode;
	
	/**
	 * Construct with an errocode and an error message
	 * 
	 * @param errorCode	Error code returned by service
	 * @param errorMessage Message returned by service
	 */
	public OboServicesException(String errorCode, String errorMessage)
	{
		super(errorMessage);
		mErrorCode = errorCode;
	}
	
	/**
	 * Get the error code
	 * 
	 * @return The error code
	 */
	public String getErrorCode()
	{
		return mErrorCode;
	}
}
