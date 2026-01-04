/*
 * $Id: NewMappingAction.java,v 1.1.2.2 2002/08/14 03:16:57 spanda Exp $
 * $Revision: 1.1.2.2 $
 * $Date: 2002/08/14 03:16:57 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * $Header: /m/src/iplanet/ias/server/src/samples/webservices/jaxm/jaxm-provideradmin/src/provideradmin/struts/Attic/NewMappingAction.java,v 1.1.2.2 2002/08/14 03:16:57 spanda Exp $
 * $Revision: 1.1.2.2 $
 * $Date: 2002/08/14 03:16:57 $
 *
 */

package provideradmin.struts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.MessageResources;

import java.util.Enumeration;

import java.net.*;
import java.util.Properties;
import javax.xml.messaging.*;
import javax.xml.soap.*;

import javax.activation.*;
import javax.naming.*;
import org.apache.commons.logging.*;

import provideradmin.util.*;

/**
 * Implementation of <strong>Action</strong> that saves the
 * changes to Endpoint mappings, that is, adds or modifies a URI
 * to URL mapping.
 *
 * @author Manveen Kaur
 * @version $Revision: 1.1.2.2 $ $Date: 2002/08/14 03:16:57 $
 */

public final class NewMappingAction extends Action {
    
    static Log
    logger = LogFactory.getFactory().getInstance("Tools/ProviderAdmin");
    
    public ActionForward perform(ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

        //The MessageResources we will be retrieving messages from.
        MessageResources resources = getResources();
        
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();
        
        // Report any errors we have discovered back to the original form
        if (!errors.empty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }
        
        // ===================================================
        // data is sent to backend after this point.
        
        // Set the parameters we will need to create the message
        
        SOAPMessage msg = null;
        MessageUtil util = new MessageUtil();
        
        // if OK from image button is pressed.
        if ( request.getParameter("OK.x") != null) {
            msg = util.createMappingCommand(request);
            util.sendMessage(request, msg);
            
            // ========================================================
            // update local copy of config in this webapp's context too.
            ServletContext context = servlet.getServletContext();
            UpdateConfig update = new UpdateConfig(context);
            update.update(request);
        }
        //if cancel is pressed, just go to success page and take no action.
        
        String message = resources.getMessage("forward.success.page");
        servlet.log(message);
       
        String profile = request.getParameter("profile");
        String protocol = request.getParameter("protocol");
        
        if ((profile!=null) && (protocol!=null))
            return new ActionForward("/"+profile+"_"+protocol+".jsp");
        
        return (mapping.findForward("success"));
    }
    
    
    
}
