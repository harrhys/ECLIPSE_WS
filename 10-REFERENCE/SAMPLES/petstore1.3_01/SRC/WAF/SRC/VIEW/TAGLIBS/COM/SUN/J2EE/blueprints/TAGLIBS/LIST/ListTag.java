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

package com.sun.j2ee.blueprints.waf.view.taglibs.list;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.BodyContent;

import java.io.IOException;
import java.util.Iterator;
import java.util.Collection;

import com.sun.j2ee.blueprints.util.tracer.Debug;


/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class ListTag extends BodyTagSupport {

    private Iterator iterator;
    private int startIndex = -1;
    private int index;
    private int size;
    private String collectionId;
    private String scope;
    private Object currentItem = null;

    public ListTag() {
        super();
    }

    public void setCollectionId(String collectionId){
        this.collectionId = collectionId;
    }

    public void setScope(String scope) {
        this.scope = scope.toLowerCase();
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int doStartTag() throws JspTagException {
        Object targetObject = null;
        Collection collection = null;
        if (scope.equals("request")) {
            targetObject = pageContext.getRequest().getAttribute(collectionId);
        } else if (scope.equals("session")) {
            targetObject = pageContext.getSession().getAttribute(collectionId);
        } else if (scope.equals("page")) {
            targetObject = pageContext.getAttribute(collectionId);
        }
        if (targetObject == null) {
           throw new JspTagException("ListTag: Collection " + collectionId + " not found in " + scope + " scope.");
        } else if (targetObject instanceof java.util.Collection) {
            collection = (Collection)targetObject;
        } else {
           throw new JspTagException("ListTag: Iterator " + collectionId + " is not an instance of java.util.Collection.");
        }
        iterator = collection.iterator();
        if (!iterator.hasNext()) return(SKIP_BODY);
        currentItem = iterator.next();
        return(EVAL_BODY_TAG);
    }

  // process the body again until the specified length with the next item if it exists
  public int doAfterBody() {
    if (iterator.hasNext()) {
      currentItem = iterator.next();
      return(EVAL_BODY_TAG);
    } else return(SKIP_BODY);
  }

    /**
     *
     * Meant for use by ListItem tags
     *
     */

    public Object getCurrentItem() {
        return currentItem;
    }

    public int doEndTag() throws JspTagException {
        try {
            BodyContent body = getBodyContent();
            if (body != null) {
                JspWriter out = body.getEnclosingWriter();
                out.print(body.getString());
            }
        } catch(IOException ioe) {
            System.err.println("Error handling items tag: " + ioe);
        }
        return EVAL_PAGE;
    }
}

