package com.farbig.practice.string;

public class StringFormat {

	public static void main(String[] args) {
		
		String name="sonoo";  
		String sf1=String.format("name is %s",name);  
		String sf2=String.format("value is %f",32.33434);  
		String sf3=String.format("value is %32.12f",32.33434);//returns 12 char fractional part filling with 0  
		  
		System.out.println(sf1);  
		System.out.println(sf2);  
		System.out.println(sf3);  
		
		String str1 = String.format("%d", 101);          // Integer value  
        String str2 = String.format("%s", "Amar Singh"); // String value  
        String str3 = String.format("%f", 101.00);       // Float value  
        String str4 = String.format("%x", 101);          // Hexadecimal value  
        String str5 = String.format("%c", 'd');          // Char value  
        System.out.println(str1);  
        System.out.println(str2);  
        System.out.println(str3);  
        System.out.println(str4);  
        System.out.println(str5); 
        
        String str11 = String.format("%d", 101);  
        String str22 = String.format("|%10d|", 101);  // Specifying length of integer  
        String str33 = String.format("|%-10d|", 101); // Left-justifying within the specified width  
        String str44 = String.format("|% d|", 101);   
        String str55 = String.format("|%04d|", 101); // Filling with zeroes  
        System.out.println(str11);  
        System.out.println(str22);  
        System.out.println(str33);  
        System.out.println(str44);  
        System.out.println(str55);  
	}

}
