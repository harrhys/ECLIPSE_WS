/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.ebank.web.taglib;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.*;
import java.util.*;
public class ScreenTag extends TagSupport {

   public ScreenTag() {
      super();
   }
   public int doStartTag() {
    
      setValue("parameters", new ArrayList());
      HashMap screens = (HashMap) pageContext.getAttribute("screens", pageContext.APPLICATION_SCOPE);
      if (screens == null) {
          Debug.println("ScreenTag: Unable to get screens object.");
          return SKIP_BODY;
      }
      else {
          if (!screens.containsKey(getId())) {
               screens.put(getId(), getValue("parameters"));
          }
          return EVAL_BODY_INCLUDE;
      
      }
    }
   public void release() {
      super.release();
   }
}
