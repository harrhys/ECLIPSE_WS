/*
 * $Id: NewMappingForm.java,v 1.1.2.1 2002/08/05 20:34:53 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:53 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * $Header: /m/src/iplanet/ias/server/src/samples/webservices/jaxm/jaxm-provideradmin/src/provideradmin/struts/Attic/NewMappingForm.java,v 1.1.2.1 2002/08/05 20:34:53 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:53 $
 *
 */

package provideradmin.struts;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import java.net.URL;
import java.net.MalformedURLException;

/**
 * Form bean for the new/editting uri to url mapping form.
 *
 * @author Manveen Kaur (manveen.kaur@sun.com)
 * @version $Revision: 1.1.2.1 $ $Date: 2002/08/05 20:34:53 $
 */

public final class NewMappingForm extends ActionForm {
    
    
    // --------------------------------------------------- Instance Variables
    private String uri = null;
    private String url = null;
    // ----------------------------------------------------------- Properties
    
    /**
     * Return the uri.
     */
    public String getUri() {
        
        return (this.uri);
        
    }
    
    
    /**
     * Set the uri.
     *
     * @param uri The new uri
     */
    public void setUri(String uri) {
        
        this.uri = uri;
        
    }
    
    /**
     * Return the url.
     */
    public String getUrl() {
        
        return (this.url);
        
    }
    
    
    /**
     * Set the url.
     *
     * @param url The new url
     */
    public void setUrl(String url) {
        
        this.url = url;
        
    }
    
    // --------------------------------------------------------- Public Methods
    
    
    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        
        this.uri = null;
        this.url = null;
    }
    
    
    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {
        
        ActionErrors errors = new ActionErrors();
        
        String profile = request.getParameter("profile");
        String protocol = request.getParameter("protocol");
        String action = request.getParameter("action");
        
        // do not process if cancel was pressed
        if ( request.getParameter("Cancel.x") != null)
            return null;
        
        //uri cannot be blank
        if ((uri == null) || (uri.length() < 1)) {
            errors.add("uri", new ActionError("error.uri.required"));
        }
        
        // url check
        if ((url == null) || (url.length() < 1)) {
            errors.add("url", new ActionError("error.url.required"));
        } else {
            // validation for http
            if ("http".equalsIgnoreCase(protocol)) {
                try {
                    URL u = new URL(url);
                } catch (MalformedURLException e) {
                    errors.add("url", new ActionError("error.url.format"));
                }
            } else {
                // https protocol
                String https = url.substring(0, url.indexOf("://"));
                // error if it doesnt begin with https. simple validation, for now.
                if (!"https".equalsIgnoreCase(https)) {
                    errors.add("url", new ActionError("error.url.format"));
                }
            }
        }
        
        
        if (action.equalsIgnoreCase("add")) {
            
            String path = "/"+profile+"_"+protocol+"_newMapping.jsp";
            mapping.setInput(path+"?uri="+ uri+"&url="+url);
            
        } else if (("modify").equalsIgnoreCase(action)) {
            
            String path = "/"+profile+"_"+protocol+"_editMapping.jsp";
            mapping.setInput(path+"?uri="+ uri+"&url="+url);
            
        }
        
        return errors;
        
    }
    
}
