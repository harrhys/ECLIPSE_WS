package com.test.proxy;

import java.lang.reflect.Proxy;

public class Client3 {
	
	public static void main(String[] args) {
		IVehicle c = new Car("Botar");
		ClassLoader cl = IVehicle.class.getClassLoader();
		Class[] cc =  { IVehicle.class };
		VehicleHandler vh = new VehicleHandler(c);
		IVehicle v = (IVehicle) Proxy.newProxyInstance(cl, cc, vh );
		v.start();
		v.forward();
		v.stop();
	}
	
}