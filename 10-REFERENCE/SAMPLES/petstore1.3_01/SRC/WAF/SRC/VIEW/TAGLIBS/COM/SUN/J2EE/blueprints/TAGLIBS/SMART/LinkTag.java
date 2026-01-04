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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.*;

/**
 * A link (i.e. &lt;a href=...&gt; and &lt;/a&gt;).
 *
 * Also see LinkContentTag and QueryParameterTag.
 */
public class LinkTag extends BodyTagSupport {

    Map parameters = new TreeMap();
    String linkContent = "";
    String href = "";

    public void setHref(String h) { href = h; }

    public void setLinkContent(String lc) { linkContent = lc; }

    public void putParameter(String name, String value) {
        parameters.put(name, value);
    }

    public int doStartTag() throws JspTagException {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspTagException {
        try {
            StringBuffer html = new StringBuffer();
            html.append("<a href=\"");

            StringBuffer url = new StringBuffer();
            url.append(href);
            Iterator it = parameters.keySet().iterator();
            if (it.hasNext()) {
                url.append("?");
                String name = (String) it.next();
                url.append(name);
                url.append("=");
                url.append(parameters.get(name));
                while (it.hasNext()) {
                    url.append("&");
                    name = (String) it.next();
                    url.append(name);
                    url.append("=");
                    url.append(parameters.get(name));
                }
            }

            html.append(((HttpServletResponse)
                         pageContext.getResponse())
                        .encodeURL(url.toString()));
            html.append("\">");
            html.append(linkContent);
            html.append("</a>");
            pageContext.getOut().print(html.toString());
            return EVAL_PAGE;
        }
        catch (IOException e) {
            throw new JspTagException("LinkTag: " + e.getMessage());
        }
    }
}

