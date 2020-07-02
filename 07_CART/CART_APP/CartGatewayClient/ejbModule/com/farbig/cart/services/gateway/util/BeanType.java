package com.farbig.cart.services.gateway.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanType {
	
	public enum Type
	{
		BMT,CMT
	}
	
	public Type value();

}
