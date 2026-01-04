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

public class ParameterTag extends TagSupport {
   private Tag parentTag = null;
   private String paramName = null;
   private String paramValue = null;
   private String isDirectString = null;

   public ParameterTag() {
      super();
   }
   public void setName(String paramName) {
      this.paramName = paramName;
   }
   public void setValue(String paramValue) {
      this.paramValue = paramValue;
   }
   public void setDirect(String isDirectString) {
      this.isDirectString = isDirectString;
   }
   public int doStartTag() {
      boolean isDirect = false;

      if ((isDirectString != null) && 
         isDirectString.toLowerCase().equals("true"))
         isDirect = true;

      try {
         // retrieve the parameters list
         if (paramName != null) {
            ArrayList parameters = (ArrayList)((TagSupport)getParent()).getValue("parameters");
            if (parameters != null) {
               Parameter param = new Parameter(paramName, paramValue, isDirect);
               parameters.add(param);
            } else {
               Debug.println("ParameterTag: parameters do not exist.");
            }
         }
      } catch (Exception e) {
         Debug.println("ParameterTag: error in doStartTag: " + e);
      }
      return SKIP_BODY;
   }
   public void release() {
      parentTag = null;
      paramName = null;
      paramValue = null;
      isDirectString = null;	
      super.release();
   }
}
