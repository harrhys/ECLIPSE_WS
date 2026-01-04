package com.test.proxy;

public class Client2 {
	
	public static void main(String[] args) {
		
		IVehicle c = new Car("Botar");
		IVehicle v = new VehicleProxy(c);
		v.start();
		v.forward();
		v.stop();
		
	}
}