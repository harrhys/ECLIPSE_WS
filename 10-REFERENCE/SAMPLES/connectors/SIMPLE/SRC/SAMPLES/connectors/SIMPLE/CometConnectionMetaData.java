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
import javax.resource.ResourceException;
import java.lang.String;

// CustomCodeBegin globalScope

// CustomCodeEnd

/**
 *This class provides information about an EIS instance connected through a Connection instance
 */
public class CometConnectionMetaData implements ConnectionMetaData {

  private CometManagedConnection mc;

  // CustomCodeBegin classScope

	// CustomCodeEnd

  /**
   *Constructor
   *@param mc The phisical connection of the CometConnection that created this instance of CometConnectionMetaData
   */
  public CometConnectionMetaData(CometManagedConnection mc) {
	System.out.println("In CometConnectionMetaData ctor");
       this.mc = mc;
  }

  /**
   *Returns product name of the underlying EIS instance connected through the
   * Connection that produced this metadata.
   *@return String - Product name of the EIS instance.
   */
  public String getEISProductName() throws ResourceException{
       String productName=null;

       // ToDo: Add service specific code here
		   // CustomCodeBegin getEISProductName

		   // CustomCodeEnd

       return  productName;
  }

  /**
   *Returns product version of the underlying EIS instance.
   *@return String - Product version of an EIS instance.
   */
  public String getEISProductVersion() throws ResourceException{
       String productVersion=null;

       // ToDo: Add service specific code here
		   // CustomCodeBegin getEISProductVersion

		   // CustomCodeEnd

       return  productVersion;
  }

  /**
   *Returns the user name for an active connection as known to the underlying EIS instance.
   *@return String - representing the user name
   */
  public String getUserName() throws ResourceException{
     
       if (mc.isDestroyed()) {
            throw new ResourceException
                (Messages.getMessage(Messages.DESTROYED_CONNECTION),
                 Messages.DESTROYED_CONNECTION);
       }
       return mc.getUserName();

  }

}
