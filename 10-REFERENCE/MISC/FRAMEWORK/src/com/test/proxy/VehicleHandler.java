package com.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class VehicleHandler implements InvocationHandler {

	private IVehicle v;
	
	public VehicleHandler() {
		
	}

	public VehicleHandler(IVehicle v) {
		this.v = v;
	}

	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		System.out.println("Vehicle Handler: Invoking " + m.getName());
		return m.invoke(v, args);
	}
	
	public static void main(String[] args) {
		IVehicle c = new Car("Botar");
		ClassLoader cl = IVehicle.class.getClassLoader();
		Class[] cc =  { IVehicle.class };
		VehicleHandler vh = new VehicleHandler();
		IVehicle v = (IVehicle) Proxy.newProxyInstance(cl, cc, vh );
		v.start();
		v.forward();
		v.stop();
	}

}