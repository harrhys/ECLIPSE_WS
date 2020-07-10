package com.farbig.practice.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ComparatorTest {

	public static void main(String a[]) {

		List<Student> students = new ArrayList<Student>();

		students.add(new Student(10, "tat", 12));
		students.add(new Student(5, "bat", 15));
		students.add(new Student(3, "rat", 9));
		students.add(new Student(8, "cat", 7));
		students.add(new Student(6, "mat", 8));
		students.add(new Student(4, "cat", 18));
		students.add(new Student(8, "xat", 7));
		


		Collections.sort(students);

		for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {

			Student student = (Student) iterator.next();
			System.out.println(student);
		}

		System.out.println();

		Collections.sort(students, new StudentNameComparator());

		for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {

			Student student = (Student) iterator.next();
			System.out.println(student);
		}

		System.out.println();

		Collections.sort(students, new StudentAgeComparator());

		for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {

			Student student = (Student) iterator.next();
			System.out.println(student);
		}
		
		System.out.println();

		Collections.sort(students, new StudentComparator());

		for (Iterator<Student> iterator = students.iterator(); iterator.hasNext();) {

			Student student = (Student) iterator.next();
			System.out.println(student);
		}
	}
}

class StudentNameComparator implements Comparator<Student> {

	public int compare(Student t1, Student t2) {

		return t1.getName().compareTo(t2.getName());
	}
}

class StudentAgeComparator implements Comparator<Student> {

	public int compare(Student t1, Student t2) {

		if (t1.getAge() > t2.getAge())
			return 1;
		else if (t1.getAge() < t2.getAge())
			return -1;
		else
			return 0;
	}
}

class StudentComparator implements Comparator<Student> {

	public int compare(Student t1, Student t2) {

		if (t1.getAge() > t2.getAge())
			return 1;
		else if (t1.getAge() < t2.getAge())
			return -1;
		else {
			if (t1.getId() > t2.getId())
				return 1;
			else if (t1.getId() < t2.getId())
				return -1;
			else {
				return t1.getName().compareTo(t2.getName());
			}
		}

	}
}

class Student implements Comparable<Student> {

	public Student(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	Integer id;

	String name;

	Integer age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public int compareTo(Student o) {
		return this.getId().compareTo(o.getId());
	}
	
	public String toString() {
		return "id:" + id + "-age:" + age + "-name:" + name;
	}

}
