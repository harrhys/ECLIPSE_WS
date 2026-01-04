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
import java.util.*;

public class DefinitionTag extends TagSupport {
   private         String screenName = null;
   private         String definitionName = null;
   private         String screenId;

   public DefinitionTag() {
      super();
   }
   public void setName(String name) {
      this.definitionName = name;
   }
   public void setScreen(String screenId) {
      this.screenId = screenId;
   }
   public int doStartTag() {
      HashMap screens = null;

      screens = (HashMap) pageContext.getAttribute("screens", pageContext.APPLICATION_SCOPE);
      if (screens == null)
      	pageContext.setAttribute("screens", new HashMap(), pageContext.APPLICATION_SCOPE);
      return EVAL_BODY_INCLUDE;
   }
   public int doEndTag()throws JspTagException {
      try {
         Definition definition = new Definition();
         HashMap screens = null;
         ArrayList params = null;
         TagSupport screen = null;

         screens = (HashMap) pageContext.getAttribute("screens", pageContext.APPLICATION_SCOPE);
         if (screens != null) { 
            params = (ArrayList) screens.get(screenId);
         }
         else
            Debug.println("DefinitionTag: screens null.");

         if (params == null)
            Debug.println("DefinitionTag: params are not defined.");

         Iterator	ir = null;

         if (params != null)
            ir = params.iterator();

         while ((ir != null) && ir.hasNext())
            definition.setParam((Parameter) ir.next());

         // put the definition in the page context
         pageContext.setAttribute(definitionName, definition);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return EVAL_PAGE;
   }
   public void release() {
      screenName = null;
      definitionName = null;
      screenId = null;
      super.release();
   }
}
