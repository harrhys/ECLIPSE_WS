package com.farbig.web.services.client;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ProductWebService service = new ProductWebService();
		
		System.out.println(service.getCalculatorImplPort().addProduct(5,6));

	}

}
