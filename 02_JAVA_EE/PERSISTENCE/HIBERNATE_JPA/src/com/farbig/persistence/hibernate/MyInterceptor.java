package com.farbig.persistence.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class MyInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;

	   @Override
	   public void onDelete(Object entity,
	                     Serializable id,
	                     Object[] state,
	                     String[] propertyNames,
	                     Type[] types) {
	       // do nothing
	   }

	   // This method is called when Employee object gets updated.
	   @Override
	   public boolean onFlushDirty(Object entity,
	                     Serializable id,
	                     Object[] currentState,
	                     Object[] previousState,
	                     String[] propertyNames,
	                     Type[] types) {
	      
	          System.out.println("Myinterceptor Update Operation");
	          return true; 
	     
	   }
	   @Override
	   public boolean onLoad(Object entity,
	                    Serializable id,
	                    Object[] state,
	                    String[] propertyNames,
	                    Type[] types) {
	       // do nothing
		   System.out.println("Myinterceptor Load Operation");
	       return true;
	   }
	   // This method is called when Employee object gets created.
	  @Override
	   public boolean onSave(Object entity,
	                    Serializable id,
	                    Object[] state,
	                    String[] propertyNames,
	                    Type[] types) {
		   
	          System.out.println("Myinterceptor Create Operation");
	          return true; 
	      
	   }
	   //called before commit into database
	   @Override
	   public void preFlush(Iterator iterator) {
	      System.out.println("Myinterceptor preFlush");
	   }
	   //called after committed into database
	   @Override
	   public void postFlush(Iterator iterator) {
	      System.out.println("Myinterceptor postFlush");
	   }
	}