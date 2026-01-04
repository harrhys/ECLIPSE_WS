package com.obopay.obopayagent.restclient;

public interface OboServicesListener
{
	/**
	 * Notifies that result from web service call was successfully received and parsed. 
	 * @param serviceType			identifies the web service call. May be one of the <code>OboServicesConstants.METHOD_*</code> values.
	 * @param result				the result of the successful web service call. The type depends on the web service API.
	 * @param svcObj				the {@link OboServices} object that sent the notification.
	 */
	public void onOboServicesResult(int serviceType, Object result, OboServices svcObj);
	
	/**
	 * Notifies that there was an error in the web service call.
	 * @param serviceType			identifies the web service call. May be one of <code>OboServicesConstants.METHOD_*</code> values.
	 * @param e						An <code>Exception</code> containing information on the error condition.
	 * 								If it's an <code>HttpException</code>, then it's an error from the web service.
	 * 								If it's an <code>IOException</code>, then it's a network error.
	 * @param svcObj				the {@link OboServices} object that sent the notification.
	 */
	public void onOboServicesError(int serviceType, Exception e, OboServices svcObj);
}
