/*
 * $Id: EditMappingAction.java,v 1.1.2.1 2002/08/05 20:34:53 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:53 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * $Header: /m/src/iplanet/ias/server/src/samples/webservices/jaxm/jaxm-provideradmin/src/provideradmin/struts/Attic/EditMappingAction.java,v 1.1.2.1 2002/08/05 20:34:53 georgel Exp $
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
 * Implementation of <strong>Action</strong> that saves the top level
 * provider configuration that is common for all profiles.
 *
 * @author Manveen Kaur
 * @version $Revision: 1.1.2.1 $ $Date: 2002/08/05 20:34:53 $
 */

public final class EditMappingAction extends Action {
    
    static Log logger = LogFactory.getFactory().getInstance("Tools/ProviderAdmin");
    
    /**
     * The MessageResources we will be retrieving messages from.
     */
    private MessageResources resources = null;
    
    
    // --------------------------------------------------------- Public Methods
    
    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     *
     * @param mapping The ActionMapping used to select this instance
     * @param actionForm The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet exception occurs
     */
    public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
    throws IOException, ServletException {
        
       // Acquire the resources that we need
        HttpSession session = request.getSession();
        if (resources == null) {
            resources = getServlet().getResources();
        }

        // resources = getResources();
        
        // Validate the request parameters specified by the user
        ActionErrors errors = new ActionErrors();
        
        // Report any errors we have discovered back to the original form
        if (!errors.empty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }
        
        ServletContext context = servlet.getServletContext();
        
        // ===================================================
        // data is sent to backend after this point.
        
        // Set the parameters we will need to create the message
        SOAPMessage msg = null;
        MessageUtil util = new MessageUtil();
        
        String profile = request.getParameter("profile");
        String protocol = request.getParameter("protocol");
        
        String action = request.getParameter("action");
        String type = request.getParameter("type");
        
        // check if save ( profile ) button was pressed.
        if ( request.getParameter("Save.x") != null ) {
            // change the persistence and error handling settings for this profile
            if (type.equalsIgnoreCase("PersErrorForProfile")) {
                msg = util.topLevelChangeCommand(request);
                util.sendMessage(request, msg);
            }
            
            UpdateConfig update = new UpdateConfig(context);
            update.update(request);
            
            return (mapping.findForward("successmessage"));
        }
        
        // if OK from image button is pressed.
        if ( request.getParameter("OK.x") != null) {
            if (type.equalsIgnoreCase("Endpoint")) {
                if (action.equalsIgnoreCase("modify") || action.equalsIgnoreCase("add")) {
                    msg = util.createMappingCommand(request);
                } else if (action.equalsIgnoreCase("remove"))
                    msg = util.deleteMappingCommand(request);
                util.sendMessage(request, msg);
            }
            
            UpdateConfig update = new UpdateConfig(context);
            update.update(request);
            
            // success page depends on profile and protocol
            if ((profile!=null) && (protocol!=null))
                return new ActionForward("/"+profile+"_"+protocol+".jsp");
        }
        //if cancel is pressed, just go to success page and take no action.
        
        
        if (request.getParameter("Cancel.x") != null ) {
            return new ActionForward("/"+profile+"_"+protocol+".jsp");
        }
        
       String message = resources.getMessage("forward.success.page");
       servlet.log(message);
        
        return (mapping.findForward("success"));
    }
    
    
    
}
