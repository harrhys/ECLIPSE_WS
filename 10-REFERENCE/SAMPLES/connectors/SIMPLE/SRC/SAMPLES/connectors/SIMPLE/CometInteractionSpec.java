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

import java.io.Serializable;
import java.beans.*;

// CustomCodeBegin globalScope

// CustomCodeEnd

/**
 * This implementation class holds properties for driving an Interaction
 * with an EIS instance. This class is a Java Bean, hence it supports
 * bound properties.
 */
public class CometInteractionSpec implements InteractionSpec, java.io.Serializable  {

  private PropertyChangeSupport changes = new PropertyChangeSupport(this);

  // CustomCodeBegin classScope

  /* example code:
  private String functionName;

  public String getFunctionName() {
        return this.functionName;
  }

  public void setFunctionName(String functionName) {
        String oldName = this.functionName;
        this.functionName = functionName;
	      changes.firePropertyChange("functionName",oldName,functionName);
  }
  */
	// CustomCodeEnd


  /**
   *Default Constructor
   */
  public CometInteractionSpec() {
	System.out.println(" 7. In CometInteractionSpec ctor");
  }

  /**
   * Associate PropertyChangeListener to the CometInterationSpec.In order to 
   * notify about properties changes.
   * java bean implementation.
   */
  public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
  }

   /**
    *Delete association of PropertyChangeListener to the CometInterationSpec.
    */
   public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
   }
}
