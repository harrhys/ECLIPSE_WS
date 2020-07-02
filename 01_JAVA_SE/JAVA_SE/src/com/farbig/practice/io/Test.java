package com.farbig.practice.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {

		try {
			FileOutputStream fout = new FileOutputStream("testout.txt");
			//fout.write(65);
			// fout.close();
			System.out.println("success...");
			String s = "Welcome to javaTpoint.";
			byte b[] = s.getBytes();// converting string into byte array
			fout.write(b);
			fout.close();
			FileInputStream fin = new FileInputStream("testout.txt");
			 int i=0;    
	            while((i=fin.read())!=-1){    
	             System.out.print((char)i);    
	            }    

			fin.close();
			System.out.println("\nsuccess...");
			
			
			 fout=new FileOutputStream("testout.txt");    
		     BufferedOutputStream bout=new BufferedOutputStream(fout);    
		      s="Welcome to javaTpoint.";    
		      b=s.getBytes();    
		     bout.write(b);    
		     bout.flush();    
		     bout.close();    
		     fout.close();    
		     System.out.println("success");    
		     
		     fin=new FileInputStream("testout.txt");    
		     BufferedInputStream bin=new BufferedInputStream(fin);    
		     while((i=bin.read())!=-1){    
		      System.out.print((char)i);    
		     }    
		     bin.close();    
		     fin.close();    
		     
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
