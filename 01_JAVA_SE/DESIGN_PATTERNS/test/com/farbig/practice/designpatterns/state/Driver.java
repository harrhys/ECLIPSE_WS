package com.farbig.practice.designpatterns.state;

import org.junit.Test;

public class Driver {

	@Test
	public void test() {
		
		VendingMachine vm = new VendingMachine(2);
		vm.insertCoin();
		vm.dispenseProduct();
		vm.ejectCoin();
		vm.insertCoin();
		vm.insertCoin();
		vm.ejectCoin();
		vm.dispenseProduct();
		vm.ejectCoin();
		vm.insertCoin();
		vm.dispenseProduct();
		vm.insertCoin();
		vm.ejectCoin();
		vm.dispenseProduct();

	}

}
