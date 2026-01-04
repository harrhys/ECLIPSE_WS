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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import java.lang.reflect.Method;
import com.sun.j2ee.blueprints.waf.util.I18nUtil;

/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class ExtractPropertyTag extends BodyTagSupport {

    private String sourceId = null;
    private String sourceScope = null;
    private String targetScope = null;
    private String property = null;
    private Object targetObject = null;
    private String destinationId = null;

    private ArrayList parameters = new ArrayList();

    public ExtractPropertyTag() {
        super();
    }

    public void setId(String sourceId){
        this.sourceId = sourceId;
    }

    public void setScope(String sourceScope){
        this.sourceScope = sourceScope.toLowerCase();
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public void setDestinationScope(String targetScope) {
        this.targetScope = targetScope.toLowerCase();
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int doStartTag() throws JspTagException {
        targetObject = null;
        if (sourceScope.equals("request")) {
            targetObject = pageContext.getRequest().getAttribute(sourceId);
        } else if (sourceScope.equals("session")) {
            targetObject = pageContext.getSession().getAttribute(sourceId);
        } else if (sourceScope.equals("page")) {
            targetObject = pageContext.getAttribute(sourceId);
        }
        if (targetObject == null) {
            throw new JspTagException("ExtractPropertyTag: Target Item " + sourceId + " not found in " + sourceScope + " scope.");
        }
        return EVAL_BODY_TAG;
    }

    public int doEndTag() throws JspTagException {
        Object extractedObject = getProperty();
        if (targetScope.equals("request")) {
            pageContext.getRequest().setAttribute(destinationId,extractedObject);
        } else if (targetScope.equals("session")) {
            pageContext.getSession().setAttribute(destinationId, extractedObject);
        } else if (targetScope.equals("page")) {
            pageContext.setAttribute(destinationId, extractedObject);
        }
        parameters = null;
        sourceId = null;
        sourceScope = null;
        targetScope = null;
        property = null;
        targetObject = null;
        destinationId = null;
        return EVAL_PAGE;
    }


    /**
     * Using the current Object use reflection to obtain the
     * String data from the element method the same as a JavaBean
     * would use:
     * <Br><br>e.g. a getXXXX method which has no parameters
     * <br>    The target method is the property attribute
     */
    private Object getProperty() throws JspTagException {
        //String targetMethod = property.substring(0,1).toUpperCase() + property.substring(1,property.length());
        String targetMethod = property;
        Object returnValue = null;
        try {
            // set arguments if  needed
            Class[] args = null;
            Object[] params = null;
            if (parameters == null) {
                Class[] tempArgs = {};
                args = tempArgs;
                Object[] tempParams = {};
                params = tempParams;
            } else {
                // set the parameters here
                args = new Class[parameters.size()];
                params = new Object[parameters.size()];
                Iterator it = parameters.iterator();
                int index=0;
                while (it.hasNext()) {
                    ParameterItem item = (ParameterItem)it.next();
                    if (item.getType().equals("java.lang.String")) {
                        args[index] = String.class;
                        params[index] = item.getParameter();
                    } else if(item.getType().equals("int")) {
                        args[index] = int.class;
                        params[index] = new Integer(item.getParameter());
                    } else if(item.getType().equals("boolean")) {
                        args[index] = boolean.class;
                        params[index] = new Boolean(item.getParameter());
                    } else if(item.getType().equals("java.util.Locale")) {
                        args[index] = Locale.class;
                        Locale locale = I18nUtil.getLocaleFromString(item.getParameter());
                        params[index] = locale;
                    }
                    index++;
                }
            }

            Method m = targetObject.getClass().getMethod(property, args);
            if (m == null) {
                throw new JspTagException("ExtractPropertyTag: There is no method by the name of " + targetMethod);
            }
            returnValue = m.invoke(targetObject,params);
        } catch ( java.lang.NoSuchMethodException ex) {
            throw new JspTagException("ExtractPropertyTag: Method for property " + property + " not found.");
        } catch (java.lang.reflect.InvocationTargetException ex) {
            ex.printStackTrace();
            throw new JspTagException("ExtractPropertyTag: Error calling method " + targetMethod + ":" + ex);
        } catch (java.lang.IllegalAccessException ex) {
            throw new JspTagException("ExtractPropertyTag: Error calling method " + targetMethod + ":" + ex);
        }
        return returnValue;
    }

    public void addParameter(String parameter, String type) {
        parameters.add(new ParameterItem(parameter,type));
    }


    // This class represents a parameter for internal use in this class only

    class ParameterItem {

        private String parameter;
        private String type;

        ParameterItem(String parameter, String type) {
            this.parameter = parameter;
            this.type = type;
        }

        public String getParameter() {
            return parameter;
        }

        public String getType() {
            return type;
        }

    }
}

