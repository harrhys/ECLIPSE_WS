/**
 * @author Copyright (c) 2008,2013, Oracle and/or its affiliates. All rights reserved.
 *  
 */
package com.farbig.examples.cdi.service;

import javax.enterprise.inject.Model;

/**
 * CDI Model Bean Class
 */
@Model
public class Login {
  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String id) {
    this.username = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
