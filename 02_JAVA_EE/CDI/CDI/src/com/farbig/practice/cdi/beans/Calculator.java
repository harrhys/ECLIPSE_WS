package com.farbig.practice.cdi.beans;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class Calculator {
	
	private String calculatorName="Calculator";
	
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
	
	public String toString()
	{
		return "This is "+calculatorName;
	}
}
