package com.farbig.services.ws.calculator.client.stubs;


public class CalculatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CalculatorImpl service = new CalculatorImpl();
		
		System.out.println(service.getCalculatorImplPort().add(5,6));

	}

}
