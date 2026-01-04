/*
 * $Id: TopLevelForm.java,v 1.1.2.1 2002/08/05 20:34:54 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:54 $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * $Header: /m/src/iplanet/ias/server/src/samples/webservices/jaxm/jaxm-provideradmin/src/provideradmin/struts/Attic/TopLevelForm.java,v 1.1.2.1 2002/08/05 20:34:54 georgel Exp $
 * $Revision: 1.1.2.1 $
 * $Date: 2002/08/05 20:34:54 $
 *
 */

package provideradmin.struts;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for the user profile page.
 *
 * @author Manveen Kaur (manveen.kaur@sun.com)
 * @version $Revision: 1.1.2.1 $ $Date: 2002/08/05 20:34:54 $
 */

public final class TopLevelForm extends ActionForm {
    
    
    // --------------------------------------------------- Instance Variables
    private String records = null;
    private String maxretries = null;
    private String directory = null;
    private String interval = null;    
    // ----------------------------------------------------------- Properties
       
    /**
     * Return the records per file.
     */
    public String getRecords() {
        
        return (this.records);
        
    }
    
    
    /**
     * Set the records per file.
     *
     * @param records The new records
     */
    public void setRecords(String records) {
        
        this.records = records;
        
    }
    
    /**
     * Return the records.
     */
    public String getMaxretries() {
        
        return (this.maxretries);
        
    }
    
    
    /**
     * Set the records.
     *
     * @param records The new records
     */
    public void setMaxretries(String maxretries) {
        
        this.maxretries = maxretries;
        
    }
    
    /**
     * Return the directory.
     */
    public String getDirectory() {
        
        return (this.directory);
        
    }
    
    
    /**
     * Set the directory.
     *
     * @param directory The new directory
     */
    public void setDirectory(String directory) {
        
        this.directory = directory;
        
    }
    
    
    /**
     * Return the retry interval.
     */
    public String getInterval() {
        
        return (this.interval);
        
    }
    
    
    /**
     * Set the interval.
     *
     * @param interval The new interval
     */
    public void setInterval(String interval) {
        
        this.interval = interval;
        
    }
    
    // --------------------------------------------------------- Public Methods
    
    
    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        
        this.maxretries = null;
        this.interval = null;
        this.records = null;   
        this.directory = null;
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

        //max retries check
        if ((maxretries == null) || (maxretries.length() < 1)) {
            errors.add("maxretries", new ActionError("error.maxretries.required"));
        } else {
            try {
                long val = Long.parseLong(maxretries);
                if ((val <= 0) || (val >=99999))
                    errors.add("maxretries", new ActionError("error.maxretries.range"));
            } catch (NumberFormatException e) {
                errors.add("maxretries", new ActionError("error.maxretries.format"));
            }
        }
        
        // interval check
        if ((interval == null) || (interval.length() < 1)) {
            errors.add("interval", new ActionError("error.interval.required"));
        } else {
            try {
                long val = Long.parseLong(interval);
                if ((val <= 10) || (val >=999999))
                    errors.add("interval", new ActionError("error.interval.range"));
            } catch (NumberFormatException e) {
                errors.add("interval", new ActionError("error.interval.format"));
            }
        }
        
        // records per file check
        if ((records == null) || (records.length() < 1)) {
            errors.add("records", new ActionError("error.records.required"));
        } else {
            try {
                long val = Long.parseLong(records);
                if ((val <= 0) || (val >=9999))
                    errors.add("records", new ActionError("error.records.range"));
            } catch (NumberFormatException e) {
                errors.add("records", new ActionError("error.records.format"));
            }
        }
        
        // directory check
        if ((directory == null) || (directory.length() < 1)) {
            errors.add("directory", new ActionError("error.directory.required"));
        }
        
        return errors;
        
    }
    
    
}
