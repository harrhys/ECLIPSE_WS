package com.farbig.practice.designpatterns.state;

public class IdleState implements State {

	@Override
	public void insertCoin(VendingMachine vm) {
		
		System.out.println("Coin inserted");
		vm.setCurrentState(vm.getHasOneDollarState());
	}

	@Override
	public void ejectCoin(VendingMachine vm) {
		
		System.out.println("No coins to eject");
	}

	@Override
	public void dispenseProduct(VendingMachine vm) {
		
		System.out.println("insert coin to dispence the product");
	}

}
