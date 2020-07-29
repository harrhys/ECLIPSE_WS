package com.farbig.practice.designpatterns.state;

public interface State {

	public void insertCoin(VendingMachine vm);

	public void ejectCoin(VendingMachine vm);

	public void dispenseProduct(VendingMachine vm);

}
