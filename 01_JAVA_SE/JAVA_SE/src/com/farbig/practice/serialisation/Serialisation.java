package com.farbig.practice.serialisation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialisation {

	public static void main(String args[]) {
		try {
			
			Student s1 = new Student(211, "Balaji");
			// Creating stream and writing the object
			FileOutputStream fout = new FileOutputStream("Serialisation.txt");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(s1);
			out.flush();
			// closing the stream
			out.close();
			System.out.println("success");

			// Creating stream to read the object
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("Serialisation.txt"));
			Student s = (Student) in.readObject();
			// printing the data of the serialized object
			System.out.println(s.id + " " + s.name);
			// closing the stream
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

class Student implements Serializable {
	
	int id;
	String name;

	public Student(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
