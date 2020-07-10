package com.javacodegeeks.enterprise.ws;

import javax.jws.HandlerChain;
import javax.jws.WebService;


@WebService(endpointInterface = "com.javacodegeeks.enterprise.ws.WebServiceInterface")
@HandlerChain(file="./handler/handlers.xml")
public class WebServiceImpl implements WebServiceInterface{

	@Override
	public String printMessage() {
		return "Hello from Java Code Geeks Server";
	}

}