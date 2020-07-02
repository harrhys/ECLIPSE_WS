/**
 * @author Copyright (c) 2008 by Oracle. All Rights Reserved
 *  
 */
package com.farbig.examples.cdi.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface ShowTime {
}
