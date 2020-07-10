package com.farbig.practice.cdi.producers;

import javax.enterprise.inject.Produces;

import com.farbig.practice.cdi.beans.Calculator;
import com.farbig.practice.cdi.beans.ScientificCalculator;
import com.farbig.practice.cdi.beans.SimpleCalculator;
import com.farbig.practice.cdi.qualifiers.ProducerChosen;

public class CalculatorProducer {

	public static final String NORMAL = "NOMRAL";
	public static final String SCIENTIFIC = "SCIENTIFIC";
	private static String calculatorType = "";

	public static String getCalculatorType() {
		
		return calculatorType;
	}

	public static void setCalculatorType(String calculatorType) {
		
		CalculatorProducer.calculatorType = calculatorType;
	}

	@Produces
	@ProducerChosen
	public Calculator getReader() {

		switch (calculatorType) {

		case NORMAL:
			return new SimpleCalculator();
			
		case SCIENTIFIC:
			return new ScientificCalculator();
			
		default:
			return new Calculator();
		}
	}

}
