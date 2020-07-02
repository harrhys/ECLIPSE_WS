/**
 * @author Copyright (c) 2008 by Oracle. All Rights Reserved
 *  
 */
package com.farbig.examples.cdi.service;

import com.farbig.examples.cdi.entity.User;

/**
 * EJB Business Interface
 */
public interface UserManager {

  public User findUser();
  
}
