package com.test;

import javax.jws.*;

@WebService(portName = "WLWSPort", serviceName = "WLWSService", targetNamespace = "http://com.test/", endpointInterface = "com.test.WLWS")
public class WLWSImpl implements WLWS {

	public void hello() {
		System.out.println("Hello");
		return;

	}
}
