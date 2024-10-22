package com.test.client.HelloClient;

import java.math.BigInteger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "HelloWSImpl", targetNamespace = "http://test.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface HelloWSImpl {

	/**
	 * 
	 * @param arg1
	 * @param arg0
	 * @return returns java.lang.String
	 */
	@WebMethod
	@WebResult(partName = "return")
	public String sayHello(
			@WebParam(name = "arg0", partName = "arg0") String arg0,
			@WebParam(name = "arg1", partName = "arg1") BigInteger arg1);

}
