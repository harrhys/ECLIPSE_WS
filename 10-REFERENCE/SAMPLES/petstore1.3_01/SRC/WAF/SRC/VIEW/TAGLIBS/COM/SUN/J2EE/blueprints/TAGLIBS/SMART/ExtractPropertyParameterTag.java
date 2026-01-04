/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN
 * OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR
 * FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF
 * LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of
 * any nuclear facility.
 */

package com.sun.j2ee.blueprints.waf.view.taglibs.smart;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.lang.reflect.Method;

/**  *  * This class is to be used with the ExtractProperty tag to specfiy parameters on
 * the method calls to the respective objects *  */

public class ExtractPropertyParameterTag extends BodyTagSupport {
        private String parameter = null;
        private String arg = null;
        private String id = null;
        private String scope = null;
        private String type = "java.lang.String";
        private String defaultValue = "";

        public ExtractPropertyParameterTag() {
            super();
        }

        public void setParameter(String parameter){
            this.parameter = parameter;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
            if (type.toLowerCase().equals("string")) this.type = "java.lang.String";
        }

        public void setDefault(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }
        public int doStartTag() throws JspTagException {
            // add the parameter to the parameter list of the ExtractPropertyTag
            ExtractPropertyTag parent = (ExtractPropertyTag) findAncestorWithClass(this, ExtractPropertyTag.class);
            if (parameter == null && arg == null && id == null) {
                throw new JspTagException("ExtractPropertyParamterTag:  Either an arg, parameter, or (id and scope) must be specified.");
            }
            if (parameter != null && arg != null) {
                throw new JspTagException("ExtractPropertyParamterTag:  An arg and parameter cannot both be specified.");
            }
            if (parent == null)   {
                throw new JspTagException("ExtractPropertyParamterTag: ExtractPropertyParamterTag tag not inside items tag");
            }        String theArg = null;
            if (parameter != null) {
                theArg =  pageContext.getRequest().getParameter(parameter);
                if (theArg == null) theArg = defaultValue;
            } else if (arg != null) {
                theArg = arg;
            } else if (id != null) {
                // get the parameter from the scope
                if (scope == null) {
                    throw new JspTagException("ExtractPropertyParamterTag: A scope must be set with the attribute id");
                }
                // XXXX make capable of doing more than strings later
                if (scope.toLowerCase().equals("request")) {
                    theArg = (String)pageContext.getRequest().getAttribute(id);
                } else if (scope.toLowerCase().equals("session")) {
                    theArg = (String)pageContext.getSession().getAttribute(id);
                } else if (scope.toLowerCase().equals("page")) {
                    theArg = (String)pageContext.getAttribute(id);
                }
            }
            parent.addParameter(theArg, type);
            return EVAL_BODY_TAG;
        }
}
