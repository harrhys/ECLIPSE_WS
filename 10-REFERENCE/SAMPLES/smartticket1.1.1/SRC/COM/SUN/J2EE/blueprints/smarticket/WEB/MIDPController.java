/*
 * Copyright 1999-2002 Sun Microsystems, Inc. ALL RIGHTS RESERVED
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

package com.sun.j2ee.blueprints.smarticket.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * This front controller handles MIDP client requests.  
 */
public class MIDPController extends HttpServlet {

    // Overridden servlet methods

    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
        throws IOException, ServletException {

        // This is the actual method that gets called when MIDP
        // clients use the application.

	HttpSession session = null;
	boolean invalidate = false;

        try {
            String command = req.getReader().readLine();
            session = req.getSession(true);
            resp.setContentType("application/binary");

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bout);
            String sessionURL = resp.encodeURL(req.getRequestURL().toString());
	    MIDPService midpService 
		= (MIDPService) session.getAttribute("midpService");
            System.err.println("[TRACE]: sessionID = " + session.getId());
            System.err.println("[TRACE]: sessionURL = " + sessionURL);
            System.err.println("[TRACE]: command = " + command);

	    // The service flags that a session should be invalidated
	    // when the client is finished with it.
            invalidate = midpService.processRequest(out, command, sessionURL);
            bout.flush();
            resp.setContentLength(bout.size());
            System.err.println("[TRACE]: contentLength = " + bout.size());
            resp.getOutputStream().write(bout.toByteArray());
        } catch (MIDPException e) {
	    log("doProcessCommand", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.err.println("[TRACE]: error = " + e.getErrorCode());
            resp.setHeader("Reason-Phrase", Integer.toString(e.getErrorCode()));
        } finally {
	    if (invalidate == true) {
		session.invalidate();
	    }
	}
    }
}
