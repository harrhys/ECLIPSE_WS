package com.test;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService(targetNamespace="vishar.com",name="CalculatorImpl", serviceName="CalculatorImpl", endpointInterface="com.test.Calculator")
//@SOAPBinding(style=Style.RPC,parameterStyle=ParameterStyle.WRAPPED,use=Use.LITERAL)
public class CalculatorMainImpl implements Calculator {

//	@WebMethod(operationName="add",action="add")
	public String add(int a, int b)  {
		
/*		CalculatorResponse myResult = new CalculatorResponse();
		
		myResult.message = "success";
		
		myResult.value = a;*/
		
		return "result is" + (a+b);
	}
	
	@WebMethod(operationName="subtraction",action="tstsubtraction")
	public int subtract(int a, int b) {
		
		return a-b;
	}
	
	@WebMethod(operationName="multiplication",action="tstmultiplication")
	public int multiply(int a, int b)  {
		
		return a*b;
	}
	
	@WebMethod(operationName="division",action="tstdivision")
	public int divide(int a, int b) {
		
		return a/b;
	}

}

