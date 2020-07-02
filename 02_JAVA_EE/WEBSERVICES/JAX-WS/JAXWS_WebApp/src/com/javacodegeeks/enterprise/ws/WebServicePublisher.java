package com.javacodegeeks.enterprise.ws;

import javax.xml.ws.Endpoint;
import com.javacodegeeks.enterprise.ws.WebServiceImpl;

public class WebServicePublisher{
 
	public static void main(String[] args) {
	   Endpoint.publish("http://localhost:8888/webservice/validator", new WebServiceImpl());
    }
 
}