package com.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class JAVA8TEST {

	public static void main(String[] args) {

		LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
		LocalTime time = LocalTime.of(11, 12, 34);
		LocalDateTime dateTime = LocalDateTime.of(date, time);

		System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
		System.out.println(dateTime
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mm");
		System.out.println(f.format(dateTime));
		//f.format(date);
		f.format(time);
		System.out.println(f.format(time));
		System.out.println(dateTime.format(f));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMM,yy");
		
		LocalDate newDate = LocalDate.parse("1111,11",formatter);
		System.out.println(newDate);
		
		StringBuilder sb1 = new StringBuilder("test");
		StringBuilder sb2 = new StringBuilder("test");
		//sb2=sb1;
		System.out.println(sb1==sb2);
		System.out.println(sb1.equals(sb2));
		System.out.println(sb1.toString().equals(sb2.toString()));
		Integer i1= 1,i2=1;
		System.out.println((i1==i2)+""+i1.equals(i2));
		JAVA8TEST j1 = new JAVA8TEST();
		JAVA8TEST j2 = new JAVA8TEST();
		System.out.println(j1==j2);
		System.out.println(j1.equals(j2));
		System.out.println(j2.hashCode());
		System.out.println(System.identityHashCode(j1));
		System.out.println(System.identityHashCode(j2));
	

	}
	
	public boolean equals(Object o)
	{
		return this.toString().equals(o.toString());
	}
	
	public String toString()
	{
		return "java";
	}
	
	public int hashCode()
	{
		return 1;
	}

}
