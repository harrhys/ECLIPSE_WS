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

package com.sun.j2ee.blueprints.waf.controller.web;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import javax.naming.InitialContext;

import com.sun.j2ee.blueprints.waf.exceptions.GeneralFailureException;
import com.sun.j2ee.blueprints.util.tracer.Debug;
import com.sun.j2ee.blueprints.waf.controller.web.URLMappingsXmlDAO;
import com.sun.j2ee.blueprints.waf.controller.web.RequestProcessor;
import com.sun.j2ee.blueprints.waf.controller.web.util.WebKeys;
import com.sun.j2ee.blueprints.waf.controller.web.util.JSPUtil;
import com.sun.j2ee.blueprints.waf.util.JNDINames;
import com.sun.j2ee.blueprints.waf.controller.web.flow.FlowHandlerException;
import com.sun.j2ee.blueprints.waf.controller.web.flow.ScreenFlowManager;
import com.sun.j2ee.blueprints.waf.controller.web.WebClientController;

public class MainServlet extends HttpServlet {

    private HashMap urlMappings;

    public void init() {
        String requestMappingsURL = null;
        try {
            requestMappingsURL = getServletContext().getResource("/WEB-INF/mappings.xml").toString();
        } catch (java.net.MalformedURLException ex) {
            System.err.println("ScreenFlowManager: initializing ScreenFlowManager malformed URL exception: " + ex);
        }
       urlMappings = URLMappingsXmlDAO.loadRequestMappings(requestMappingsURL);
       getServletContext().setAttribute(WebKeys.URL_MAPPINGS, urlMappings);
       getScreenFlowManager();
       getRequestProcessor();
    }

     public  void doGet(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        doProcess(request, response);
    }

    public  void doPost(HttpServletRequest request, HttpServletResponse  response)
        throws IOException, ServletException {
        doProcess(request, response);

    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response)
                   throws IOException, ServletException {
        String nextScreen = null;
        try {
                 getRequestProcessor().processRequest(request);
                 nextScreen = getScreenFlowManager().getNextScreen(request);
        } catch (Throwable ex) {
            String className = ex.getClass().getName();
            nextScreen = getScreenFlowManager().getExceptionScreen(ex);
            // put the exception in the request
            request.setAttribute("javax.servlet.jsp.jspException", ex);
            if (nextScreen == null) {
                // send to general error screen
                ex.printStackTrace();
                throw new ServletException("MainServlet: unknown exception: " + className);
            }
      }

      getServletConfig().getServletContext().getRequestDispatcher(nextScreen).forward(request, response);

    }

    private RequestProcessor getRequestProcessor() {
         RequestProcessor rp = (RequestProcessor)getServletContext().getAttribute(WebKeys.REQUEST_PROCESSOR);
         if ( rp == null ) {
             rp = new RequestProcessor();
             rp.init(getServletContext());
             getServletContext().setAttribute(WebKeys.REQUEST_PROCESSOR, rp);
        }
       return rp;
    }

    private ScreenFlowManager getScreenFlowManager() {
            ScreenFlowManager screenManager = (ScreenFlowManager)getServletContext().getAttribute(WebKeys.SCREEN_FLOW_MANAGER);
            if (screenManager == null ) {
                screenManager = new ScreenFlowManager();
                screenManager.init(getServletContext());
                getServletContext().setAttribute(WebKeys.SCREEN_FLOW_MANAGER, screenManager);
             }
        return screenManager;
    }

    /**
     * The UrlMapping object contains information that will match
     * a url to a mapping object that contains information about
     * the current screen, the HTMLAction that is needed to
     * process a request, and the HTMLAction that is needed
     * to insure that the propper screen is displayed.
    */

    private URLMapping getURLMapping(String urlPattern) {
        if ((urlMappings != null) && urlMappings.containsKey(urlPattern)) {
            return (URLMapping)urlMappings.get(urlPattern);
        } else {
            return null;
        }
    }
}

