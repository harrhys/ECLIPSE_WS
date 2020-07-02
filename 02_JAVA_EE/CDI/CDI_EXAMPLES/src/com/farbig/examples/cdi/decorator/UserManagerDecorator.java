/**
 * @author Copyright (c) 2008,2013, Oracle and/or its affiliates. All rights reserved.
 *  
 */
package com.farbig.examples.cdi.decorator;

import java.io.Serializable;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import com.farbig.examples.cdi.entity.User;
import com.farbig.examples.cdi.service.Login;
import com.farbig.examples.cdi.service.UserManager;
import com.farbig.examples.cdi.util.UserFactory;

@Decorator
public class UserManagerDecorator implements UserManager, Serializable {
  private static final long serialVersionUID = 1L;

  @Inject
  @Delegate
  private UserManager delegate;

  @Inject
  private Login current;

  public User findUser() {
    System.out.println("Decorator invoked ...");
    User user = null;
    if (UserFactory.getInstance().getUserMap().get(current.getUsername()) == null) {
      user = delegate.findUser();
    } else {

    // Assume no write operations will be performed on entity objects
      user = UserFactory.getInstance().getUserMap().get(current.getUsername());
    }
    return user;
  }
}
