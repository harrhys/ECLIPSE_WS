package com.farbig.rs.services.transferobjects;

public class Student {

	int rollNo;

	String name;

	int studentClass;

	public Student() {

	}

	public Student(int rollNo, String name, int studentClass) {
		this.rollNo = rollNo;
		this.name = name;
		this.studentClass = studentClass;
	}

	public int getRollNo() {
		return rollNo;
	}

	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(int studentClass) {
		this.studentClass = studentClass;
	}

}
