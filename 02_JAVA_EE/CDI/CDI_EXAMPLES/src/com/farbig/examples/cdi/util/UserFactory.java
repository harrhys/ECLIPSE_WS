/**
 * @author Copyright (c) 2010 by Oracle. All Rights Reserved
 *  
 */
package com.farbig.examples.cdi.util;

import java.util.HashMap;
import java.util.Map;

import com.farbig.examples.cdi.entity.User;

/**
 * User Factory 
 */
public class UserFactory {
  private static UserFactory instance = new UserFactory();

    private UserFactory() {}

    private Map<String, User> userMap = new HashMap<String, User>();

    public static UserFactory getInstance() {
        return instance;
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }
}
