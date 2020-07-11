package com.javacodegeeks.enterprise.ws;

import com.javacodegeeks.enterprise.ws.WebServiceImplService;

public class WSClient {
	public static void main(String[] args) {
	
	WebServiceImplService webService = new WebServiceImplService();
	WebServiceInterface serviceInterface = webService.getWebServiceImplPort();
	
	System.out.println(serviceInterface.printMessage());
 }
}
