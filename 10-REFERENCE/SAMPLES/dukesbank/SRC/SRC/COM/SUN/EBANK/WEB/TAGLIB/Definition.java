/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.web.taglib;
import java.util.Hashtable;

public class Definition {
   private Hashtable params = new Hashtable();

   public void setParam(Parameter p) {
      params.put(p.getName(), p);
   }
   public Parameter getParam(String name) {
      return (Parameter) params.get(name);
   }
}
