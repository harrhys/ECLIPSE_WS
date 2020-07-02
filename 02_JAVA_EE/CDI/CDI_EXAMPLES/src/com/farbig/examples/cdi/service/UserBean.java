/**
 * @author Copyright (c) 2008,2013, Oracle and/or its affiliates. All rights reserved.
 *  
 */
package com.farbig.examples.cdi.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.farbig.examples.cdi.entity.User;
import com.farbig.examples.cdi.interceptor.ShowTime;
import com.farbig.examples.cdi.qualifier.Stored;

/**
 * CDI Managed Bean Class
 */
@Named
@RequestScoped
public class UserBean{

  private String msg;

  @Inject
  private Login current;

  @Inject
  @Stored
  private User stored;

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public Login getCurrent() {
    return current;
  }

  public User getStored() {
    return stored;
  }

  @ShowTime
  public void process() {
	  
	 System.out.println("Inside Process Method");
    if (stored == null) {
      msg = msg + "Process Result ... Invalid User ID!";
    } else if (!current.getPassword().equals(stored.getPassword())) {
      msg = msg + "Process Result ... Incorrect password!";
    } else {
      msg = msg + "Process Result ... User Type of " + stored.getName() + " is "
          + stored.getUserType();
    }
  }
}
