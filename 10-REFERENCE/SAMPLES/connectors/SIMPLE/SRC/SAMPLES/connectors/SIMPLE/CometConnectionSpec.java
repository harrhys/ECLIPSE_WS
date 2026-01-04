/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import javax.resource.cci.*;
import java.beans.*;

// CustomCodeBegin globalScope

// CustomCodeEnd

/**
 * This implementation class is used by an application component to pass
 * connection-specific info/properties to the getConnection method in
 * CometConnectionFactory class
 *This class was implemented as java bean
 */
public class CometConnectionSpec implements javax.resource.cci.ConnectionSpec {

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private String user=null;
    private String password=null;

    // CustomCodeBegin classScope

    /* example code:
    private String language=null;

    public void setLanguage(String language){
       String oldName = this.language;
       this.language=language;
	     changes.firePropertyChange("language",oldName,language);
    }

    public String getLanguage(){
       return this.language;
    }
    */
  	// CustomCodeEnd

    /**
     *Default Constructor
     */
    public CometConnectionSpec() {
	System.out.println("In CometConnectionSpec ctor");
    }

   /**
    *get user
    *@return String - user
    */
    public String getUser() {
        return this.user;
    }

   /**
    *get password
    *@return String - password
    */
    public String getPassword() {
        return this.password;
    }

   /**
    *set user
    *@param user user name
    */
    public void setUser(String user) {
        String oldName = this.user;
        this.user = user;
	      changes.firePropertyChange("user",oldName,user);
    }

   /**
    *set password
    *@param password user password
    */
    public void setPassword(String password) {
        String oldName = this.password;
        this.password = password;
	      changes.firePropertyChange("password",oldName,password);
    }

    /**
     * Associate PropertyChangeListener to the CometConnectionSpec.In order to 
     * notify about properties changes.
     * java bean implementation.
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     *Delete association of PropertyChangeListener to the CometConnectionSpec.
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
}
