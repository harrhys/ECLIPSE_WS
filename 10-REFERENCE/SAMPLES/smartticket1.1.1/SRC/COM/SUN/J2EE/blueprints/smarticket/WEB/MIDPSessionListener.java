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

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Listens for session timeouts and performs necessary cleanup.
 */
public class MIDPSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
	System.err.println("[TRACE]: Started session");
	MIDPService midpService = null;
	try {
	    midpService = new MIDPService();
	} catch (MIDPException ex) {
	    // already reported, can ignore
	}
	se.getSession().setAttribute("midpService", midpService);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
	System.err.println("[TRACE]: Destroying session");
	/*
           The getAttribute("midpService") does not work
           for a destroyed session. The exception is
           shown in application server log (bug 4713809).
           Here is a workaround to get rid of exception.

           The getAttribute("midpService") also fails 
           in RI.  The exception is not shown in log.

	MIDPService midpService = 
	    (MIDPService) se.getSession().getAttribute("midpService");
	try {
	    midpService.removeMovieInfo();
	} catch (MIDPException ex) {
	    // already reported, can ignore
	}

	*/
    }
}
