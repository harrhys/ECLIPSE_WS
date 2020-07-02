package com.farbig.rs.client;

import java.util.HashMap;
import java.util.Map;

import com.farbig.rs.services.transferobjects.Student;
import com.farbig.rs.util.RestUtil;

public class UniversityClient {

	public static void main(String[] args) {

		RestUtil util = new RestUtil();

		String response = "";

		try {

			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/create");
			util.pathParam("1");
			util.pathParam("Balaji");
			util.pathParam("7");
			System.out.println(util.putApplicationJson());
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/create");
			util.pathParam("7");
			util.pathParam("Harrhy");
			util.pathParam("7");
			System.out.println(util.putApplicationJson());
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/create");
			util.pathParam("3");
			util.pathParam("Dummy");
			util.pathParam("7");
			System.out.println(util.putApplicationJson());
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/get/1");
			response = util.getApplicationJson();
			System.out.println(response);
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/get/7");
			response = util.getApplicationJson();
			System.out.println(response);
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/get/3");
			response = util.getApplicationJson();
			System.out.println(response);
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/update");
			Student s = util.JsonObject(response, Student.class);
			s.setStudentClass(9);
			String request = util.JsonString(s);
			System.out.println(util.postNGetApplicationJson(request));
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/get/3");
			System.out.println(util.getApplicationJson());
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("student/delete/3");
			System.out.println(util.deleteTextHTML());
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

		try {
			util.baseURL("http://localhost:7001/RESTSERVICES");
			util.path("students");
			System.out.println(util.getApplicationJson());
			util.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			util.disconnect();
		}

	}

	public void testMap() {

		Map<Integer, Student> studentInfo = new HashMap<Integer, Student>();

		studentInfo.put(1, new Student(1, "sd", 1));
		studentInfo.put(2, new Student(2, "sd", 1));
		studentInfo.put(3, new Student(3, "sd", 1));

		RestUtil util = new RestUtil();

		System.out.println(util.JsonString(studentInfo));

	}
}
