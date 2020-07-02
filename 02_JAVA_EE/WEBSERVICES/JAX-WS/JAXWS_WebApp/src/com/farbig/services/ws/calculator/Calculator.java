package com.farbig.services.ws.calculator;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

//@WebService(name="CalculatorInterface", targetNamespace="farbig.com")
@WebService
public interface Calculator {
	
/*	@WebMethod(operationName="add",action="add")
	@WebResult(header=true,name="CalculatorResponse",partName="response")*/
	public String add(int a, int b) ;//throws Exception;
	//public abstract int add(int a, int b) throws Exception;
	
	/*@WebMethod(operationName="subtraction",action="tstsubtraction")
	public abstract int subtract(int a, int b) throws IOException;
*/
}
