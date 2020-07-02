package com.farbig.practice.cdi.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.farbig.practice.cdi.qualifiers.CalculatorType;
import com.farbig.practice.cdi.qualifiers.CalculatorType.Type;

@CalculatorType(Type.SIMPLE)

public class SimpleCalculator extends Calculator{
	
	private String calculatorName="SimpleCalculator";
	
	public int add(int a, int b) {
		System.out.println(calculatorName+":Executing add method");
		return a + b;
	}
	
	public String getCalculatorName() {
		return calculatorName;
	}

	public void setCalculatorName(String calculatorName) {
		this.calculatorName = calculatorName;
	}

	@PostConstruct
	public void postConstruct() {
		
		System.out.println(calculatorName+" is created and injected");
	}

	@PreDestroy
	public void preDestroy() {
		
		System.out.println(calculatorName+" is destroyed");
	}

}
