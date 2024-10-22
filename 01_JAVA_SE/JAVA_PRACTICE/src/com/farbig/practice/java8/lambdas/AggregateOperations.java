package com.farbig.practice.java8.lambdas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AggregateOperations {

	public static void main(String[] args) throws Exception {

		List<Person> persons = new ArrayList<Person>();
		
		writePersonFile(persons);
		
		readPersonFile(persons);

		persons.stream().forEach(p -> System.out.println(p));

		System.out.println();

		//persons.stream().filter(p -> p.age > 11 && p.gender == Gender.MALE).map(p -> p.details)
		//		.forEach(name -> System.out.println(name));

		System.out.println("Total Group Age - " + persons.stream().mapToInt(p -> p.age).sum());

		// Person::getAge is the Method Reference in place of the lambda Expression
		System.out.println("Total Male Group Age - "
				+ persons.stream().filter(p -> p.gender == Gender.MALE).mapToInt(Person::getAge).sum());  

		System.out.println("Total Female Group Age - " + persons.stream().filter(p -> p.gender == Gender.FEMALE)
				.map(p -> p.age).reduce(0, (sum, age) -> sum + age));

		System.out.println("Total Male Count - " + persons.stream().filter(p -> p.gender == Gender.MALE).count());

		System.out.println("Total Male Avg - "
				+ persons.stream().filter(p -> p.gender == Gender.MALE).mapToInt(p -> p.age).average().getAsDouble());

		// Collect - Collectors - groupingBy - mapping - toList - reducing
		System.out.println("\nGender : Name - age Map - ");

		persons.stream()
				.collect(Collectors.groupingBy(p -> p.gender,
						Collectors.mapping(p -> (p.name + "-" + p.age), Collectors.toList())))
				.forEach((k, v) -> System.out.println(k + "-" + v));

		System.out.println("\nAge : Name-Gender Map  ");
		persons.stream()
				.collect(Collectors.groupingBy(p -> p.age,
						Collectors.mapping(p -> (p.name + "-" + p.gender), Collectors.toList())))
				.forEach((k, v) -> System.out.println(k + "-" + v));

		System.out.println("\nName : Age-Gender Map  ");
		persons.stream()
				.collect(Collectors.groupingBy(p -> p.name,
						Collectors.mapping(p -> (p.age + "-" + p.gender), Collectors.toList())))
				.forEach((k, v) -> System.out.println(k + "-" + v));

		System.out.println("\ngender : AgeSum Map  ");
		persons.stream()
				.collect(Collectors.groupingBy(p -> p.gender,
						Collectors.mapping(p -> p.age, Collectors.reducing(0, (sum, age) -> sum + age))))
				.forEach((k, v) -> System.out.println(k + "-" + v));

		System.out.println("\ngender : AgeAvg Map  ");
		persons.stream().collect(Collectors.groupingBy(p -> p.gender, Collectors.averagingInt((p -> p.age))))
				.forEach((k, v) -> System.out.println(k + "-" + v));

		System.out.println();
	}

	public static  void writePersonFile(List<Person> persons) throws FileNotFoundException, IOException {
		
		ObjectOutputStream outputFile = new ObjectOutputStream(new FileOutputStream(new File("Persons")));

		for (int i = 1; i <= 50; i++) {
			
			Random r = new Random();
			
			int age = r.nextInt(50);
			
			int gender = r.nextInt(2);

			while (age < 20) {
				age = r.nextInt(50);
			}

			Gender[] genders = Gender.values();

			Person p = new Person(age, "P" + i, genders[gender]);

			outputFile.writeObject(p);

			persons.add(p);
		}
		
		outputFile.close();
		
	}

	public static void readPersonFile(List<Person> persons) throws FileNotFoundException, IOException, ClassNotFoundException {
		

		ObjectInputStream inputFile = new ObjectInputStream(new FileInputStream(new File("Persons")));

		for (int i = 1; i <= 50; i++) {

			Person p = (Person) inputFile.readObject();

			persons.add(p);

		}
		
		inputFile.close();
	}

}

class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	public Person(int age, String name, Gender gender) {
		super();
		this.age = age;
		this.name = name;
		this.gender = gender;
	}

	int age;

	String name;

	Gender gender;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return name + ":" + gender + ":" + age;
	}
}

enum Gender {
	MALE, FEMALE;
}
