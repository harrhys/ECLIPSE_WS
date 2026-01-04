/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */
 
 
package com.sun.ebank.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.sun.ebank.web.BeanManager;

public final class ContextListener
   implements ServletContextListener {

   private ServletContext context = null;

   public void contextDestroyed(ServletContextEvent event) {
      context.removeAttribute("beanManager");
      context.log("contextDestroyed()");
      this.context = null;
   }

   public void contextInitialized(ServletContextEvent event) {
      this.context = event.getServletContext();
      context.setAttribute("beanManager", new BeanManager());
      context.log("contextInitialized()");
   }
}
