/*
 *
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 * 
 */


package com.sun.cb.taglib;

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
