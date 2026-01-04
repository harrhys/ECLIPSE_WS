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


package com.sun.j2ee.blueprints.waf.view.template;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;
import javax.transaction.SystemException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.HeuristicMixedException;

import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Locale;

public class TemplateServlet extends HttpServlet {

    private HashMap screens;
    private ServletConfig config;
    private ServletContext context;
    private String defaultLanguage;
    private static final String LOCALE_KEY = "com.sun.j2ee.blueprints.waf.LOCALE";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.config = config;
        this.context = config.getServletContext();
        screens = new HashMap();
        defaultLanguage = config.getInitParameter("default_language");
        String languages = config.getInitParameter("languages");
        StringTokenizer strTok = new StringTokenizer(languages,",");
        while (strTok.hasMoreTokens()) {
            initScreens(config.getServletContext(), strTok.nextToken());
        }
    }

    private void initScreens(ServletContext context, String language) {
        URL screenDefinitionURL = null;
        try {
            screenDefinitionURL = context.getResource("/WEB-INF/screendefinitions_" + language + ".xml");
        } catch (java.net.MalformedURLException ex) {
            System.err.println("TemplateServlet: malformed URL exception: " + ex);
        }
        if (screenDefinitionURL != null) {
            HashMap screenDefinitions = ScreenDefinitionDAO.loadScreenDefinitions(screenDefinitionURL);
            if (screenDefinitions != null) {
                screens.put(language, screenDefinitions);
            } else {
                System.err.println("Template Servlet Error Loading Screen Definitions: Confirm that file at URL /WEB-INF/screendefinitions_" + language + ".xml contains the screen definitions");
            }
        } else {
            System.err.println("Template Servlet Error Loading Screen Definitions: URL /WEB-INF/screendefinitions_" + language + ".xml not found");
        }
    }

    public void doPost (HttpServletRequest request,
                        HttpServletResponse response)
        throws IOException, ServletException {
        process(request, response);
    }

    public void doGet (HttpServletRequest request,
                       HttpServletResponse  response)
        throws IOException, ServletException {
        process(request, response);
    }

    public void process (HttpServletRequest request,
                          HttpServletResponse  response)
        throws IOException, ServletException {

        String screenName = null;
        String localeString = null;
        String selectedUrl = request.getRequestURI();
        String languageParam = request.getParameter("language");
        // The languge when specified as a parameter overrides the language set in the session
        if (languageParam != null) {
            localeString = languageParam;
        } else if (request.getSession().getAttribute(LOCALE_KEY) != null) {
            localeString = ((Locale)request.getSession().getAttribute(LOCALE_KEY)).toString();
        }
        if (screens.get(localeString) == null) {
           localeString = defaultLanguage;
        }
        // get the screen name
        int lastPathSeparator = selectedUrl.lastIndexOf("/") + 1;
        int lastDot = selectedUrl.lastIndexOf(".");
        if (lastPathSeparator != -1 && lastDot != -1 && lastDot > lastPathSeparator) {
            screenName = selectedUrl.substring(lastPathSeparator, lastDot);
        }
        // get the template for the corresponding language
        String templateName = null;
        if (screens != null) templateName = (String)((HashMap)screens.get(localeString)).get("template");
        // get the screen information for the coresponding language
        Screen screen = null;
        if (screenName != null){
            HashMap localeScreens = (HashMap)screens.get(localeString);
            if (localeScreens != null) screen = (Screen)localeScreens.get(screenName);
            // default to the default language screen if it was not defined in the locale specific definitions
            if (screen == null) {
                System.err.println("Screen not Found loading default from " + defaultLanguage);
                localeScreens = (HashMap)screens.get(defaultLanguage);
                screen = (Screen)localeScreens.get(screenName);
            }
            if (screen != null) {
                request.setAttribute("currentScreen", screen);
                if (templateName != null) {
                    insertTemplate(request, response, templateName);
                }
            } else {
                response.setContentType("text/html;charset=8859_1");
                PrintWriter out = response.getWriter();
                out.println("<font size=+4>Error:</font><br>Definition for screen " + screenName + " not found");
            }
        }
    }

    private void insertTemplate(HttpServletRequest request,
                                HttpServletResponse response,
                                String templateName)
        throws IOException, ServletException {

        // This method tries to wrap the request dispatcher call with-in
        // a transaction for making the local EJB access efficient. However,
        // it is not a fatal error, if such a transaction can not be started
        // for some reason, so it handles them gracefully.

        boolean tx_started = false;
        UserTransaction ut = null;

        try {
            // Lookup the UserTransaction object
            InitialContext ic = new InitialContext();
            ut = (UserTransaction) ic.lookup("java:comp/UserTransaction");
            ut.begin();         // start the transaction.
            tx_started = true;
        } catch (NamingException ne) {
            // it should not have happened, but it is a recoverable error.
            // Just dont start the transaction.
            ne.printStackTrace();
        } catch (NotSupportedException nse) {
            // Again this is a recoverable error.
            nse.printStackTrace();
        } catch (SystemException se) {
            // Again this is a recoverable error.
            se.printStackTrace();
        }

        try {
            context.getRequestDispatcher(templateName).forward(request, response);
        } finally {
            // commit the transaction if it was started earlier successfully.
            try {
                if (tx_started && ut != null) {
                    ut.commit();
                }
            } catch (IllegalStateException re) {
                re.printStackTrace();
            } catch (RollbackException re) {
                re.printStackTrace();
            } catch (HeuristicMixedException hme) {
                hme.printStackTrace();
            } catch (HeuristicRollbackException hre) {
                hre.printStackTrace();
            } catch (SystemException se) {
                se.printStackTrace();
            }
        }
    }
}

