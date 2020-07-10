package com.farbig.rs.services;


import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.farbig.rs.services.transferobjects.Student;


@Path("/")
public class UniversityService {
	
	static Map<Integer,Student> studentInfo  = new HashMap<Integer,Student>();
	
	@PUT
	@Path("/student/create/{rollno}/{name}/{class}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student createStudent(@PathParam("rollno") int rollno,@PathParam("name") String name,@PathParam("class") int studentClass) throws Exception
	{
		if(studentInfo.containsKey(rollno))
			throw new Exception("Student can't be create as existing student is found with RollNo : " + rollno);
		Student s = new Student(rollno, name, studentClass);
		studentInfo.put(rollno, s);
		return s;
	}
	
	@GET
	@Path("/student/get/{rollno}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student getStudentInfo(@PathParam("rollno") int rollno) throws Exception
	{
		if(!studentInfo.containsKey(rollno))
			throw new Exception("Student Not found with RollNo : " + rollno);
		return studentInfo.get(rollno);
	}
	
	
	@GET
	@Path("/students")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Integer,Student> getStudentsInfo() throws Exception
	{
		if(studentInfo.size()==0)
			throw new Exception("No students are enrolled yet!!");
		return studentInfo;
	}
	
	
	
	@POST
	@Path("/student/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Student updateStudent(Student s) throws Exception
	{
		if(!studentInfo.containsKey(s.getRollNo()))
			throw new Exception("Student Not found with RollNo : " + s.getRollNo());
		
		studentInfo.put(s.getRollNo(), s);
		
		return s;
	}
	
	@DELETE
	@Path("/student/delete/{rollno}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteStudentsInfo(@PathParam("rollno") int rollno1) throws Exception
	{
		if(!studentInfo.containsKey(rollno1))
			throw new Exception("Student Not found with RollNo : " + rollno1);
		studentInfo.remove(rollno1);
		return "<b>STUDENT : " + rollno1 + " IS DELETED </b>";
	}
	
	
	
	


}
