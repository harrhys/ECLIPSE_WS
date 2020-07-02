package com.test.proxy;

public class Car implements IVehicle {
	private String name;

	public Car(String name) {
		this.name = name;
	}

	public void start() {
		System.out.println(name + "Car started");
	}


	public void stop() {
		System.out.println(name + "Car stopped");

	}

	public void forward() {
		System.out.println(name + "Car forwarded");

	}

	public void reverse() {
		System.out.println(name + "Car reversed");

	}

	public String getName() {
		return name;
	}
}
