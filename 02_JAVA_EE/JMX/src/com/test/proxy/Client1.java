package com.test.proxy;

public class Client1 {
	
	public static void main(String[] args) {
		
		IVehicle v = new Car("Botar");
		v.start();
		v.forward();
		v.stop();
		
	}
}
