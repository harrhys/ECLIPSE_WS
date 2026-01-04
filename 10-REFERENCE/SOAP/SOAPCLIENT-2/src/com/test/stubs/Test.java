package com.test.stubs;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CalculatorImpl service = new CalculatorImpl();
		
		System.out.println(service.getCalculatorImplPort().add(5,6));

	}

}
