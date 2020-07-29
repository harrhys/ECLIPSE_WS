package com.farbig.practice.designpatterns.state;

public class VendingMachine {

	private State idleState;
	private State hasOneDollarState;
	private State outOfStockState;
	private State currentState;
	private int productCount;

	public VendingMachine(int count) {

		idleState = new IdleState();
		hasOneDollarState = new HasOneDollarState();
		outOfStockState = new OutOfStockState();
		productCount = count;

		if (productCount > 0) {
			currentState = idleState;
		} else if (productCount == 0) {
			currentState = outOfStockState;
		}

	}

	public State getIdleState() {
		return idleState;
	}

	public void setIdleState(State idleState) {
		this.idleState = idleState;
	}

	public State getHasOneDollarState() {
		return hasOneDollarState;
	}

	public void setHasOneDollarState(State hasOneDollarState) {
		this.hasOneDollarState = hasOneDollarState;
	}

	public State getOutOfStockState() {
		return outOfStockState;
	}

	public void setOutOfStockState(State outOfStockState) {
		this.outOfStockState = outOfStockState;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	//Event triggered by Customer
	public void insertCoin() {
		currentState.insertCoin(this);
	}

	//Event triggered by Customer
	public void ejectCoin() {
		currentState.ejectCoin(this);
	}

	//Event triggered by Customer
	public void dispenseProduct() {

		currentState.dispenseProduct(this);

	}
	
	//Action performed by Vending Machine
	public void doReleaseProduct()
	{
		System.out.println("Product Dispensed");
		productCount--;
	}
	
	//Action performed by Vending Machine
	public void doReturnMoney()
	{
		System.out.println("Coin Returned");
	}

}
