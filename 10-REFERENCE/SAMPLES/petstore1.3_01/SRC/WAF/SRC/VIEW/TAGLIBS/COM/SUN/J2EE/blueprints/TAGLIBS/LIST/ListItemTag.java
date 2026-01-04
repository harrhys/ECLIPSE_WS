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

import java.lang.reflect.Method;
import java.io.IOException;

import java.util.Locale;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.sun.j2ee.blueprints.util.tracer.Debug;
import com.sun.j2ee.blueprints.waf.util.I18nUtil;

/**
 *
 * This class allows you to retrieve a parameter from the request and output it to the page
 * This should allow you to avoid expressions for extracting parameter info.
 *
 */

public class ListItemTag extends TagSupport {

    private Object item = null;
    private String property = null;
    private String format = null;
    private String localeString = null;
    private String numberFormatPattern = null;
    private String precisionString = null;

    public ListItemTag() {
        super();
    }

    /**
     * To be used when formatting currency
     *
     */
    public void setLocale(String localeString) {
        this.localeString = localeString;
    }
    /**
     * Support number format and currency format here.
     * When this is set ensure that the proper decile places are
     * supportted.
     *
     */
    public void setFormatText(String format) {
        this.format = format;
    }

    /**
     * Only used when formattting numbers
     *
     */
    public void setNumberFormatPattern(String numberFormatPattern) {
        this.numberFormatPattern = numberFormatPattern;
    }

    public void setPrecision(String precisionString) {
        this.precisionString = precisionString;
    }

    public void setProperty(String property){
        this.property = property;
    }

    public int doStartTag() throws JspTagException {
        // check if items tag is in list tag
        ListTag listTag = (ListTag) findAncestorWithClass(this, ListTag.class);

        if (listTag == null) {
          throw new JspTagException("ListItemTag: ListItem tag not inside items tag");
        }
        item = listTag.getCurrentItem();
        if (listTag == null) {
          throw new JspTagException("ListItemTag: There is no item to list.");
        }
        return SKIP_BODY;
    }

  public int doEndTag() throws JspTagException {
    // print out attribute
    try {
      JspWriter out = pageContext.getOut();
      String targetText = getText();
      if (format != null) targetText = formatText(targetText);
      out.print(targetText);
    } catch(IOException ioe) {
      System.err.println("ListItemTag: Error printing attribute: " + ioe);
      throw new JspTagException("ListItemTag: IO Error printing attribute.");
    }
    return(EVAL_PAGE);
  }

  /**
   * Using the current Object use reflection to obtain the
   * String data from the element method the same as a JavaBean
   * would use:
   * <br><br>e.g. a getXXXX method which has no parameters
   * <br>    The target method is the property attribute
   *
   * The default method that is called is the toString method on the object.
   */
  private String getText() throws JspTagException {

      String targetMethod = null;
      if (property == null) targetMethod = "toString";
      else targetMethod = "get" + property.substring(0,1).toUpperCase() + property.substring(1,property.length());

      Object returnValue = null;
      try {
          // no arguments are needed
          Class[] args = {};
          Object[] params = {};
          Method m = item.getClass().getMethod(targetMethod, args);
          if (m == null) {
              throw new JspTagException("ListItemTag: There is no method by the name of " + targetMethod);
          }
          returnValue = m.invoke(item,params);
        } catch ( java.lang.NoSuchMethodException ex) {
              throw new JspTagException("ListItemTag: Method for property " + property + " not found.");
        } catch (java.lang.reflect.InvocationTargetException ex) {
              throw new JspTagException("ListItemTag: Error calling method " + targetMethod + ":" + ex);
        } catch (java.lang.IllegalAccessException ex) {
              throw new JspTagException("ListItemTag: Error calling method " + targetMethod + ":" + ex);
        }
        // do casting of Integers and Dobules here
        if (returnValue instanceof java.lang.String) {
            return (String)returnValue;
        } else if (returnValue instanceof java.lang.Integer) {
            return ((Integer)returnValue).toString();
        } else if (returnValue instanceof java.lang.Double){
            return ((Double)returnValue).toString();
        } else if (returnValue instanceof java.lang.Float) {
            return ((Float)returnValue).toString();
        } else {
              throw new JspTagException("ListItemTag: Error extracting property: Can not handle the return type for property " + property);
        }
    }


    /**
     * Apply number formatting for the default locale the application is running in unless
     * specfied with the locale attribute.
     *
     * Apply the pattern if specified.
     *
     */
    private String formatText(String text) throws JspTagException {
        String formattedString = null;
        Locale locale = null;
        int precision = 2;
        if (precisionString != null) {
            try {
                precision = Integer.parseInt(precisionString);
            } catch (java.lang.NumberFormatException ex) {
                // if this fails stick with the default;
                precision = 2;
            }

        }

        locale = I18nUtil.getLocaleFromString(localeString);
        if (locale == null) locale = Locale.getDefault();
        // use doubles for the number formatting
        double dub = 0;
        try {
            dub = Double.parseDouble(text);
        } catch (java.lang.NumberFormatException nex) {
            throw new JspTagException("ListItemTag: Error converting : " + text + " to a double. Ensure it is a number.");
        }
        if (format.toLowerCase().equals("number")) {
            if (numberFormatPattern != null) formattedString = I18nUtil.formatNumber(dub,precision,numberFormatPattern, locale);
            else formattedString = I18nUtil.formatNumber(dub,precision,locale);
        } else if (format.toLowerCase().equals("currency")) {
            if (numberFormatPattern != null) formattedString = I18nUtil.formatCurrency(dub,precision,numberFormatPattern,locale);
            else formattedString = I18nUtil.formatCurrency(dub,precision,locale);
        } else {
            throw new JspTagException("ListItemTag: Error extracting formatting text: Do not know format:>" + format + "<");
        }
        return formattedString;
    }

}


