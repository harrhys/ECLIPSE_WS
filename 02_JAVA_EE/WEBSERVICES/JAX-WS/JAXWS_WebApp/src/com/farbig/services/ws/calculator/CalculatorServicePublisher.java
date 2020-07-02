package com.farbig.services.ws.calculator;

import javax.xml.ws.Endpoint;

public class CalculatorServicePublisher {

	public static void main(String[] args) {

		Endpoint.publish("http://localhost:80/services/calculator", new SimpleCalculator());
		Endpoint.publish("http://localhost:81/services/calculator", new FullCalculator());

	}

}
