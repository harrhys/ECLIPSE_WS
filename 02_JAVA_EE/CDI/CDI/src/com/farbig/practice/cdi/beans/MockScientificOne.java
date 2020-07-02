package com.farbig.practice.cdi.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

import com.farbig.practice.cdi.qualifiers.CalculatorType;
import com.farbig.practice.cdi.qualifiers.CalculatorType.Type;

@Alternative
@CalculatorType(Type.SCIENTIFIC)
@Priority(Interceptor.Priority.APPLICATION+25)
public class MockScientificOne extends Calculator{
	
	private String calculatorName="MockScientificOne";
	
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
