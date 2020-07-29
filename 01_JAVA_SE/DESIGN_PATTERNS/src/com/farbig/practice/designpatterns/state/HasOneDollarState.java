package com.farbig.practice.designpatterns.state;

public class HasOneDollarState implements State {

	@Override
	public void insertCoin(VendingMachine vm) {

		System.out.println("Coin is already inserted");

	}

	@Override
	public void ejectCoin(VendingMachine vm) {

		vm.doReturnMoney();
		vm.setCurrentState(vm.getIdleState());

	}

	@Override
	public void dispenseProduct(VendingMachine vm) {
		
		vm.doReleaseProduct();
		if (vm.getProductCount() > 0) {
			vm.setCurrentState(vm.getIdleState());
		} else {
			vm.setCurrentState(vm.getOutOfStockState());
		}
	}

}
