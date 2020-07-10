package com.farbig.practice.string;

public class PassByValueTest {

	public static void main(String... doYourBest) {
		
		StringBuilder profession = new StringBuilder("Dragon ");
		String weapon = "Sword ";
		Person p = new Person();
		p.name="harrhy";
		p.age=40;
		Boolean check = true;
		int num = 10;
		changeWarriorClass(profession, weapon,p,check,num);

		System.out.println(profession);
		System.out.println(weapon);
		System.out.println(p.name);
		System.out.println(p.age);
		System.out.println(check);
		System.out.println(num);
	}

	static void changeWarriorClass(StringBuilder warriorProfession, String weapon, Person p, boolean test, int i) {
		warriorProfession.append("Knight");
		weapon = "Dragon " + weapon;
		p.name="hari";
		p.age=10;
		test = false;
		i=15;

		weapon = null;
		warriorProfession = null;
		
	}

}

class Person {
	String name;
	int age;
}
