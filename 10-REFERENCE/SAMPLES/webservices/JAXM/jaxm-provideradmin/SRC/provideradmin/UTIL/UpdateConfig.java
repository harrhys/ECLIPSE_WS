/*
 * $Id: UpdateConfig.java,v 1.1.2.1 2002/08/05 20:34:55 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:55 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package provideradmin.util;

import javax.servlet.http.*;
import java.util.Iterator;
import javax.servlet.ServletContext;

import com.sun.xml.messaging.jaxm.provider.Config;

/**
 * A utility to update config object in provideradmin webapp's
 * context.
 * Admin Servlet in provider is the one that writes the new provider.xml.
 *
 * @author Manveen Kaur (manveen.kaur@sun.com)
 */

public class UpdateConfig {
    
    Config config = null;
    HttpServletRequest request = null;
    
    public UpdateConfig(ServletContext context) {
        this.config = (Config) context.getAttribute("theConfig");
    }
    
    public void update(HttpServletRequest request) {
        
        this.request = request;
        
        // type of object this will work on.
        String type = request.getParameter("type");        
        String action = request.getParameter("action");
        String profile = request.getParameter("profile");
        String protocol = request.getParameter("protocol");

        // take action depending on type of object to be acted upon
        // and action to be performed.
        
        if (("Endpoint").equalsIgnoreCase(type)) {
            changeURIMapping(action, profile, protocol);
        } else if (("Persistence").equalsIgnoreCase(type)){
            changePersistence(action, profile, protocol);
        } else if (("ErrorHandling").equalsIgnoreCase(type)) {
            changeErrorHandling(action, profile, protocol);
        } else if (("PersErrorForProfile").equalsIgnoreCase(type)) {
            changePersErrorHand(action, profile, protocol);
        } else if (("TopLevel").equalsIgnoreCase(type)) {
            changeTopLevel(action, profile, protocol);
        }        
    }    
    
    public void changeURIMapping(String action, String profile, String protocol) {
        
        if (action.equalsIgnoreCase("add")) {
            
            String uri = request.getParameter("uri");
            String url = request.getParameter("url");
            
            config.addURIMapping(profile, protocol, uri, url);
            
        } else  if (action.equalsIgnoreCase("modify")) {
            
            String uri = request.getParameter("uri");
            String url = request.getParameter("url");
            
            config.modifyURIMapping(profile, protocol, uri, url);
            
        } else if (action.equalsIgnoreCase("remove")) {
            
            String[] uris =  request.getParameterValues("checkbox");
            
            if (uris != null) {
                for (int i=0; i< uris.length; i++)
                    config.removeURIMapping(profile, protocol, uris[i]);
            }
        }
    }
    
    // change both persistence and error handling for the profile,transport 
    public void changePersErrorHand(String action, String profile, String protocol)
    {
               
        String maxretries = request.getParameter("maxretries");
        String interval = request.getParameter("interval");
        
        String directory = request.getParameter("directory");
        String records = request.getParameter("records");
        
        config.modifyErrorHandling(profile, protocol, maxretries, interval);
        config.modifyPersistence(profile, protocol, directory, records);
        
    }
    
    public void changePersistence(String action, String profile, String protocol)
    {
        
        String directory = request.getParameter("directory");
        String records = request.getParameter("records");
        
        if (action.equals("remove")) {
            config.removePersistence(profile, protocol);
        } else if (action.equals("topremove")){
            config.removePersistence();
        } else if (action.equals("modify")){
            config.modifyPersistence(profile, protocol, directory, records);
        } else {
            // top level modify
            config.modifyPersistence(directory, records);
        }
        
    }
    
    public void changeErrorHandling(String action, String profile, String protocol) {
        String maxretries = request.getParameter("maxretries");
        String interval = request.getParameter("interval");
        
        if (action.equals("remove")) {
            config.removeErrorHandling(profile, protocol);
        } else if (action.equals("topremove")) {
            config.removeErrorHandling();
        } else if (action.equals("modify")) {
            config.modifyErrorHandling(profile, protocol, maxretries, interval);
        } else {
            //top modify
            config.modifyErrorHandling(maxretries, interval);
        }
    }
    
    
    public void changeTopLevel(String action, String profile, String protocol) {
        
        String maxretries = request.getParameter("maxretries");
        String interval = request.getParameter("interval");
        
        String directory = request.getParameter("directory");
        String records = request.getParameter("records");
        
        //top modify
        config.modifyErrorHandling(maxretries, interval);
        config.modifyPersistence(directory, records);
    }
    
}
