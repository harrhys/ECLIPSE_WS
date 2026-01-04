/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 */

package samples.connectors.simple;

import javax.resource.spi.*;
import javax.resource.ResourceException;
import java.lang.String;
import javax.resource.spi.IllegalStateException;

// CustomCodeBegin globalScope

// CustomCodeEnd

/**
 *The ManagedConnectionMetaData interface provides information about the underlying EIS
 *instance associated with a ManagedConnection instance. An application server uses this
 * information to get runtime information about a connected EIS instance
 */
public class CometManagedConnectionMetaData implements ManagedConnectionMetaData {

  private CometManagedConnection mc;

  // CustomCodeBegin classScope

	// CustomCodeEnd

  /**
   *Constructor
   *@param mc  The managed connection that created this instance of CometManagedConnectionMetaData
   */
  public CometManagedConnectionMetaData(CometManagedConnection mc) {
	System.out.println("In CometManagedConnectionMetaData ctor");
       this.mc = mc;
  }

  /**
   *Returns Product name of the underlying EIS instance connected through the ManagedConnection.
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
   *Returns product version of the underlying EIS instance connected through the ManagedConnection..
   *@return String - Product version of the EIS instance
   */
  public String getEISProductVersion() throws ResourceException{
         String productVersion=null;

       // ToDo: Add service specific code here
		   // CustomCodeBegin getEISProductVersion

		   // CustomCodeEnd

       return  productVersion;
  }

  /**
   *Returns maximum limit on number of active concurrent connections that
   *an EIS instance can support across client processes. If an EIS
   *  instance does not know about (or does not have) any such limit, it returns a 0.
   *@return int - Maximum limit for number of active concurrent connections
   */
  public int getMaxConnections() throws ResourceException{
      int  maxConnections=0;

       // ToDo: Add service specific code here
		   // CustomCodeBegin getMaxConnections

		   // CustomCodeEnd

       return  maxConnections;
  }

  /**
   *Returns name of the user associated with the ManagedConnection instance. The name
   *corresponds to the resource principal under  its security context, a connection to the
   * EIS instance has been established.
   *@return int - name of the user
   */
  public String getUserName() throws ResourceException{
        if (mc.isDestroyed()) {
            throw new IllegalStateException
                (Messages.getMessage(Messages.DESTROYED_CONNECTION),
                 Messages.DESTROYED_CONNECTION);
        }
        return mc.getUserName();  
  }

}
