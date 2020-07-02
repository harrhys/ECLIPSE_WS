package com.farbig.services.ws.calculator;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService( serviceName = "SimpleCalculatorService", name="SimpleCalculatorImpl",portName = "SimpleCalculator.port", targetNamespace = "http://farbig.com")
@SOAPBinding(style=Style.DOCUMENT, use=Use.LITERAL,parameterStyle=ParameterStyle.WRAPPED)
public class SimpleCalculator implements Calculator {

	@WebMethod(operationName="add",action="tstadd")
	public String add(int a, int b)  {
		
/*		CalculatorResponse myResult = new CalculatorResponse();
		
		myResult.message = "success";
		
		myResult.value = a;*/
		
		return "result is" + (a+b);
	}
	
	/*
	 * @WebMethod(operationName="subtraction",action="tstsubtraction") public int
	 * subtract(int a, int b) {
	 * 
	 * return a-b; }
	 * 
	 * @WebMethod(operationName="multiplication",action="tstmultiplication") public
	 * int multiply(int a, int b) {
	 * 
	 * return a*b; }
	 * 
	 * @WebMethod(operationName="division",action="tstdivision") public int
	 * divide(int a, int b) {
	 * 
	 * return a/b; }
	 */

}

