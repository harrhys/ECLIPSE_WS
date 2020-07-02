/**
 * @author Copyright (c) 2008 by Oracle. All Rights Reserved
 *  
 */
package com.farbig.examples.cdi.interceptor;

import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.farbig.examples.cdi.service.UserBean;

@ShowTime
@Interceptor
public class ShowTimeInterceptor {
  
  
  /**
   * <br/> is used for a newline in facelets
   * 
   */ 
  @AroundInvoke
  public Object identify(InvocationContext ctx) throws Exception { 
   
	  System.out.println("Inside identify method");
	  UserBean userBean = (UserBean)ctx.getTarget(); 
    String msg = "Intercepor Invoked ...<br/>From Method ... " + ctx.getMethod() + "<br/>";

    msg = msg + "Current Time ... "
        + (new Date(System.currentTimeMillis())).toString() + "<br/><br/>";
    
    userBean.setMsg(msg);    
    return ctx.proceed();
  }
}