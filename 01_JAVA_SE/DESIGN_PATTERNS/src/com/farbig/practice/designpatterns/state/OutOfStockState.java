package com.farbig.practice.designpatterns.state;

public class OutOfStockState implements State {

	@Override
	public void insertCoin(VendingMachine vm) {
		
		System.out.println("Product out of stock..come back later");
		
	}

	@Override
	public void ejectCoin(VendingMachine vm) {
		
		System.out.println("Product out of stock..come back later");
		
	}

	@Override
	public void dispenseProduct(VendingMachine vm) {
		
		System.out.println("Product out of stock..come back later");
		
	}


}
