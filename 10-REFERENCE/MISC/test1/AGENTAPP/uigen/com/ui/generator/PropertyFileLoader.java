package com.ui.generator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PropertyFileLoader {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
		   
		   loadFile("deposit_money1.properties");


	}
	
	public static List loadFile(String filename)
	{
		
		List properties = new ArrayList();
		
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(filename);
		
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		   String strLine;
		  //Read File Line By Line
		  while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console
		  System.out.println (strLine);
		  properties.add(strLine);
		  
		  }
		  //Close the input stream
		  in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return null;
	}

}
