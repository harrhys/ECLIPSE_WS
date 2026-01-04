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
import javax.servlet.jsp.tagext.TagSupport;

public class InsertTag extends TagSupport {
   private boolean directInclude = false;
   private String parameterName = null;
   private String definitionName = null;
   private Definition definition = null;
   private Parameter parameter = null;

   public InsertTag() {
      super();
   }
   public void setParameter(String parameter) {
      this.parameterName = parameter;
   }
   public void setDefinition(String name) {
      this.definitionName = name;
   }
   public int doStartTag() {
      // get the definition from the page context
         
      definition = (Definition)pageContext.getAttribute(definitionName);
      // get the parameter
      if (parameterName != null && definition != null)
         parameter = (Parameter) definition.getParam(parameterName);

      if (parameter != null)
         directInclude = parameter.isDirect();
      return SKIP_BODY;
   }
   public int doEndTag()throws JspTagException {
     try {
         // if parameter is direct, print to out
         if (directInclude && parameter  != null)
            pageContext.getOut().print(parameter.getValue());
         // if parameter is indirect, include results of dispatching to page 
         else {
            if ((parameter != null) && (parameter.getValue() !=  null))
               pageContext.include(parameter.getValue());
         }
      } catch (Exception ex) {
             	 throw new JspTagException(ex.getMessage());
      }
      return EVAL_PAGE;
   }
   public void release() {
      directInclude = false;
      parameterName = null;
      definitionName = null;
      definition = null;
      parameter = null;
      super.release();
   }
}
