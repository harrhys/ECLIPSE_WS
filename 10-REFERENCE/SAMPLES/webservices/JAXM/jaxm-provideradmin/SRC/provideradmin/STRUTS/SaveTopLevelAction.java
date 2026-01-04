/*
 * $Id: SaveTopLevelAction.java,v 1.1.2.1 2002/08/05 20:34:53 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:53 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * $Header: /m/src/iplanet/ias/server/src/samples/webservices/jaxm/jaxm-provideradmin/src/provideradmin/struts/Attic/SaveTopLevelAction.java,v 1.1.2.1 2002/08/05 20:34:53 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:53 $
 *
 */

package provideradmin.struts;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.MessageResources;

import java.net.*;
import java.util.Properties;
import javax.xml.messaging.*;
import javax.xml.soap.*;

import javax.activation.*;
import javax.naming.*;
import org.apache.commons.logging.*;

import provideradmin.util.MessageUtil;
import provideradmin.util.UpdateConfig;

/**
 * Implementation of <strong>Action</strong> that saves the top level
 * provider configuration that is common for all profiles.
 *
 * @author Manveen Kaur
 * @version $Revision: 1.1.2.1 $ $Date: 2002/08/05 20:34:53 $
 */

public final class SaveTopLevelAction extends Action {
    
    static Log
    logger = LogFactory.getFactory().getInstance("Tools/ProviderAdmin");
    
    public ActionForward perform(ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
        
        // front end validation and checking.
        // ===================================================
        MessageResources messages = getResources();
        
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();
        
        // Report any errors we have discovered back to the original form
        if (!errors.empty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }
        
        // ===================================================
        // data is sent to backend after this point.
        
        SOAPMessage msg = null;
        MessageUtil util = new MessageUtil();
        
        //change provider.xml in provider webapp
        msg = util.topLevelChangeCommand(request);
        util.sendMessage(request, msg);
        
        // ========================================================
        // update local copy of config in this webapp's context too.
        ServletContext context = servlet.getServletContext();
        UpdateConfig update = new UpdateConfig(context);
        update.update(request);
        
        if (servlet.getDebug() >= 1)
            servlet.log(" Forwarding to success page");
        return (mapping.findForward("successmessage"));
    }
}
