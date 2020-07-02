/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.farbig.examples.jsf.bookmarking;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class GreetBean {
  private String greeting;
  private String msg = "Guys";

  public String getGreeting() {
    return greeting;
  }

  public void setGreeting(String greeting) {
    this.greeting = greeting;
    this.msg = greeting + ", " + msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

}
