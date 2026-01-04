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

import java.util.Collection;

/**
 * A conditional tag.
 *
 * &lt;waf:conditional id=&quot;id&quot; scope=&quot;scope&quot;&gt;
 *   &lt;waf:true&gt;blah&lt;/waf:true&gt;
 *   &lt;waf:false&gt;blah&lt;/waf:false&gt;
 * &lt;/waf:conditional&gt;
 */
public class ConditionalTag extends BodyTagSupport {

    boolean value;

    String id;
    String scope;

    public void setId(String i) { id = i; }

    public void setScope(String s) { scope = s; }

    public boolean getValue() { return value; }

    public int doStartTag() throws JspTagException {
        Object o = null;

        if (scope.equals("request")) {
            o = pageContext.getRequest().getAttribute(id);
        }
        else if (scope.equals("session")) {
            o = pageContext.getSession().getAttribute(id);
        }
        else if (scope.equals("page")) {
            o = pageContext.getAttribute(id);
        }

        value = doTest(o);

        return EVAL_BODY_INCLUDE;
    }

    protected boolean doTest(Object o) throws JspTagException {
        return false;
    }

    public int doEndTag() throws JspTagException { return EVAL_PAGE; }
}

