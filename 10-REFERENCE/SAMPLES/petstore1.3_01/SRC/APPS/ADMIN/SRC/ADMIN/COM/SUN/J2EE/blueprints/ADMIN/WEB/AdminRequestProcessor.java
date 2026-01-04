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

package com.sun.j2ee.blueprints.admin.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.servlet.ServletOutputStream;

/**
 * This servlet serves requests from Admin web client
 */
public class AdminRequestProcessor extends HttpServlet {

    boolean fromDoGet = false;

    /**
     * This method builds the dynamic JNLP file for java web start
     * @param req The <Code>HttpServletRequest</Code> from which host details
     *            are obtained
     * @param sid The session id to enable the rich client attach itself to
     *            this session and thereby get authenticated
     * @return String the JNLP file for download by WebStart
     */
    private String buildJNLP(HttpServletRequest req, String sid) {

        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        String codebase = "<jnlp codebase=\"http://" + serverName +
                          ":" + serverPort + "/admin\">\n";
        String appInfo = "<information>\n" +
                         "<title>Pet Store Admin Rich Client</title>\n" +
                         "<vendor>J2EE BluePrints</vendor>\n" +
                         "<description>Example of Java Web Start Enabled" +
                         " Rich Client For J2EE application</description>\n" +
                         "<description kind=\"short\"></description>\n" +
                         "</information>\n";
        String rsrcInfo = "<resources>\n" +
                          "<j2se version=\"1.4\"/>\n" +
                          "<jar href=\"AdminApp.jar\"/>\n" +
                          "</resources>\n";
        String appDesc =
                   "<application-desc main-class=\"PetStoreAdminClient\">\n" +
                   "<argument>" + serverName + "</argument>\n" +
                   "<argument>" + serverPort + "</argument>\n" +
                   "<argument>" + sid + "</argument>\n";
        String endOfFile = "</application-desc>\n</jnlp>\n";
        return(xmlHeader+codebase+appInfo+rsrcInfo+appDesc+endOfFile);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws java.io.IOException, javax.servlet.ServletException {
        fromDoGet = true;
        doPost(req, resp);
        fromDoGet = false;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws java.io.IOException, javax.servlet.ServletException {

        HttpSession session = req.getSession();
        String sId = session.getId();
        if(fromDoGet) {
            getServletConfig().getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            String curScreen = req.getParameter("currentScreen").trim();
            if(curScreen.equals("logout")) {
                getServletConfig().getServletContext().getRequestDispatcher("/logout.jsp").forward(req, resp);
            }
            if(curScreen.equals("manageorders")) {
                resp.setContentType("application/x-java-jnlp-file");
                ServletOutputStream out = resp.getOutputStream();
                out.println(buildJNLP(req, sId));
                out.flush();
                out.close();
            }
        }
    }
}

