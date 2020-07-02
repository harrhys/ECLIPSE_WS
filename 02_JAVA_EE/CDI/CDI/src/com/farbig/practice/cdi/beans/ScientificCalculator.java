package com.farbig.practice.cdi.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import com.farbig.practice.cdi.qualifiers.CalculatorType;
import com.farbig.practice.cdi.qualifiers.CalculatorType.Type;

@CalculatorType(Type.SCIENTIFIC)
@ApplicationScoped
public class ScientificCalculator extends Calculator{
	
	public ScientificCalculator()
	{
		System.out.println("Created ScientificCalculator");
	}
	
	private String calculatorName="ScientificCalculator";
	
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
