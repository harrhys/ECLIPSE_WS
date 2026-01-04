package com.weblogic.sample.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class LH
{
	public final static String staticversion = "1.0";
	public static void trace(String version, String packageandclass, String method, String message)
	{
		System.out.println(version+"-"+packageandclass+"."+method+"() "+message);
	}
	public static void error(String version, String packageandclass, String method, String message)
	{
		System.out.println(version+"-"+packageandclass+"."+method+"() "+message);
	}
	public static void exception(String version, String packageandclass, String method, String message, Throwable e)
	{
		System.out.println(version+"-"+packageandclass+"."+method+"() "+message+" "+e);
	}
	public static void staticversion(String testversion)
	{
		System.out.println("Staticversion="+staticversion + "  EJB Version=" + testversion);
	}
	
	public static void testdb(String version, String packageandclass, String method, String message)
	{	
	try{
		InitialContext   initCtx   =   new   InitialContext();   
		DataSource   ds   =   (DataSource)   initCtx.lookup("jdbc/obodb_XA_ds");   
	    Connection  conn   =   ds.getConnection();   	
	    String   SQLSelect=" SELECT fubar a FROM FUBAR ";
	    Statement   stmt=conn.createStatement();   
	    ResultSet   rs=stmt.executeQuery(SQLSelect);

		   while(rs.next())
		   {                    
				System.out.println(version+"-"+packageandclass+"."+method+"() "+message+"="+rs.getString("a"));
		   }		    
		rs.close();
		stmt.close();
		conn.close();
		}
		catch (Throwable e)
		{
			System.out.println ("sql error: " + e);
		}	
	
	}
}
